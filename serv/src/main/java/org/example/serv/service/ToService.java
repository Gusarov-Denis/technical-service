package org.example.serv.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.example.serv.models.ListDataAttributes;
import org.example.serv.models.ToReq;
import org.example.serv.persistence.entity.To;
import org.example.serv.persistence.mappers.ToMapper;
import org.example.serv.repository.ToRepository;
import org.example.serv.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.serv.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ToService {

    private final ToRepository repository;
    private final ToStatusService toStatusService;
    Logger logger = LoggerFactory.getLogger(ToService.class);
    private final ToMapper toMapper;

    private final RestTemplate restTemplate;
    private final Boolean serviceCheck;
    private final String clientService;
    private final String oilService;
    private final String carService;

    public ToService(ToRepository repository, ToMapper toMapper, ToStatusService toStatusService,
                     RestTemplate restTemplate, @Value("${org.example.service.check}") Boolean serviceCheck,
                     @Value("${org.example.client.service}") String clientService,
                     @Value("${org.example.oil.service}") String oilService,
                     @Value("${org.example.car.service}") String carService){
        this.repository = repository;
        this.toMapper = toMapper;
        this.toStatusService = toStatusService;
        this.restTemplate = restTemplate;
        this.serviceCheck = serviceCheck;
        this.clientService = clientService;
        this.oilService = oilService;
        this.carService = carService;
    }

    public  ListDataAttributes<List<To>> readTos(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<To>>();
        List<To> allByActual = repository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    @Retry(name = "default")
    public UUID add(ToReq req, UUID runId) {
        To entity = toMapper.dtoToEntity(req);

        checkExistName(runId, entity.getName());

        if(serviceCheck) {
            clientServiceGetEmail(runId, entity.getLogin());
            oilServiceGet(runId, entity.getOilId());
            carServiceGet(runId, entity.getCarId());
        }

        entity.setToStatus(toStatusService.readToStatusById(req.getToStatusId()));
        entity.setActual(true);
        To save;
        save = repository.save(entity);
        UUID uuid = save.getId();
        logger.info(String.format("add Done: runId: '%s', id: '%s'", runId, uuid));
        return uuid;
    }


    private String clientServiceGetEmail(UUID runId, String login) {
        String  res;
        final var url = clientService + "/api/v1/users/emailbylogin/" + login;
        logger.info(String.format("add clientServiceURL: runId: '%s', url: '%s'", runId, url));
        ResponseEntity<String> resEmailEntity;
        try {
            resEmailEntity = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ServiceNotFoundRuntimeException(String.format("car get runId: '%s' err status: 404",
                        runId));
            }
            throw new RuntimeException(e);
        }
        if (!(resEmailEntity.getStatusCode() == HttpStatus.OK && resEmailEntity.getBody() != null)) {
            throw new ServiceNotFoundRuntimeException(String.format("emailbylogin runId: '%s' err status: '%s', resp: '%s'",
                    runId, resEmailEntity.getStatusCode(), resEmailEntity.getBody()));
        }
        else {
            res = resEmailEntity.getBody();
        }
        return res;
    }

    private String oilServiceGet(UUID runId, UUID id) {
        String  res =null;
        final var url = oilService + "/api/v1/oils/" + id.toString();
        logger.info(String.format("add oilServiceURL: runId: '%s', url: '%s'", runId, url));
        ResponseEntity<String> resOil;
        try {
            resOil = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ServiceNotFoundRuntimeException(String.format("car get runId: '%s' err status: 404",
                        runId));
            }
            throw new RuntimeException(e);
        }
        if (!(resOil.getStatusCode() == HttpStatus.OK && resOil.getBody() != null)) {
            throw new ServiceNotFoundRuntimeException(String.format("oil get runId: '%s' err status: '%s', resp: '%s'",
                    runId, resOil.getStatusCode(), resOil.getBody()));
        }
        else {
            res = resOil.getBody();
        }
        return res;
    }

    private String carServiceGet(UUID runId, UUID id) {
        String  res;
        final var url = carService + "/api/v1/cars/" + id.toString();
        logger.info(String.format("add carServiceURL: runId: '%s', url: '%s'", runId, url));
        ResponseEntity<String> resCar;
        try {
            resCar = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ServiceNotFoundRuntimeException(String.format("car get runId: '%s' err status: 404",
                        runId));
            }
            throw new RuntimeException(e);
        }
        if (!(resCar.getStatusCode() == HttpStatus.OK && resCar.getBody() != null)) {
            throw new ServiceNotFoundRuntimeException(String.format("car get runId: '%s' err status: '%s', resp: '%s'",
                    runId, resCar.getStatusCode(), resCar.getBody()));
        }
        else {
            res = resCar.getBody();
        }
        return res;
    }

    private void checkExistName(UUID runId, String name) {
        Optional<To> byNameAndActual = repository.findByNameAndActual(name, true);
        if(byNameAndActual.isPresent()){
            String message = String.format("checkExistName: runId: '%s', TO with name: '%s' already exist.", runId, name);
            logger.error(message);
            throw new ServiceEntityExistRuntimeException(message);
        }

    }

    public List<UUID> getIds() {
        UUID notReqStatusId = UUID.fromString("c409b9aa-4643-4115-a308-c829c492291f");
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        now = now.plusMinutes(-5);
        UUID doneStatusId = UUID.fromString("da7ee44a-cb0b-4c63-bf4f-f51632fc3606");

        return repository.getIds(notReqStatusId, 10001, now, doneStatusId);
    }

    public UUID updateToStatus(UUID id, UUID runId) {

        To readToById = readToById(id, runId);
        UUID statusId = UUID.fromString("de32b8d3-5852-4776-a459-7899ecb2f0c5");
        readToById.setToStatus(toStatusService.readToStatusById(statusId));
        To save = repository.save(readToById);
        return save.getId();
    }

    public To readToById(UUID id, UUID runId) {
        Optional<To> byId = repository.findById(id);
        if (byId.isPresent())
        {
            To vesselCall = byId.get();
            //check
            checkActual("readToById", runId, id, vesselCall.getActual());
            return vesselCall;

        } else {
            String message = String.format("readToById: runId: '%s', TO with id: '%s' not found ", runId, id);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }

    private void checkActual(String methodName, UUID runId, UUID id, boolean actual) {
        if(!actual){
            String message = String.format("%s: runId: '%s', TO with id: '%s' not found.", methodName, runId, id);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }


}
