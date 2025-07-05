package org.example.car.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.example.car.models.CarReq;
import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.Car;
import org.example.car.persistence.mappers.CarMapper;
import org.example.car.repository.CarRepository;
import org.example.car.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.car.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.SoftReference;
import java.util.*;

@Service
public class CarService {

    private final CarRepository repository;
    private final MotorTypeService motorTypeService;
    private final MarkModelService markModelService;
    Logger logger = LoggerFactory.getLogger(CarService.class);
    private final CarMapper carMapper;
    private final RestTemplate restTemplate;
    private final Boolean serviceCheck;
    private final String clientService;
    private final Map<String, SoftReference<Car>> mapCar = new HashMap<>();


    public CarService(CarRepository repository, CarMapper carMapper, MotorTypeService motorTypeService,
                      MarkModelService markModelService, RestTemplate restTemplate, @Value("${org.example.service.check}") Boolean serviceCheck,
                      @Value("${org.example.client.service}") String clientService){
        this.repository = repository;
        this.carMapper = carMapper;
        this.motorTypeService = motorTypeService;
        this.markModelService = markModelService;
        this.restTemplate = restTemplate;
        this.serviceCheck = serviceCheck;
        this.clientService = clientService;
    }

    public  ListDataAttributes<List<Car>> readCars(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<Car>>();
        List<Car> allByActual = repository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    @Retry(name = "default")
    public UUID add(CarReq req, UUID runId) {
        Car entity = carMapper.dtoToEntity(req);
        checkExistRegNumber(runId, entity.getRegNumber());
        if(serviceCheck) {
            clientServiceGetEmail(runId, entity.getLogin());
        }

        entity.setMotorType(motorTypeService.readMotorTypeById(req.getMotorTypeId()));
        entity.setMarkModel(markModelService.readMarkModelById(req.getMarkModelId()));
        entity.setActual(true);
        Car save;
        save = repository.save(entity);
        mapCar.put(save.getRegNumber().toLowerCase(), new SoftReference<>(save));
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
                throw new ServiceNotFoundRuntimeException(String.format("client get runId: '%s' err status: 404", runId));
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

    private void checkExistRegNumber(UUID runId, String regNumber) {
        Optional<Car> byNameAndActual = repository.findByRegNumberAndActual(regNumber, true);
        if(byNameAndActual.isPresent()){
            String message = String.format("checkExistName: runId: '%s', Car with regNumber: '%s' already exist.", runId, regNumber);
            logger.error(message);
            throw new ServiceEntityExistRuntimeException(message);
        }

    }

    public Car read(UUID id, UUID runId) {
        Optional<Car> byId = repository.findById(id);
        if (byId.isPresent())
        {
            Car car = byId.get();
            //check
            checkActual("readCarById", runId, car.getId(), car.getActual());
            return car;
        }
        else {
            String message = String.format("readCarById: runId: '%s', Car with id: '%s' not found.", runId, id);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }

    private void checkActual(String methodName, UUID runId, UUID id, boolean actual) {
        if(!actual){
            String message = String.format("%s: runId: '%s', Car with id: '%s' not found.", methodName, runId, id);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }

    public Car readByRegNumber(String regNumber, UUID runId) {
        SoftReference<Car> carInCache = mapCar.get(regNumber.toLowerCase());
        if (carInCache != null && carInCache.get() != null) {
            return carInCache.get();
        } else {
            Optional<Car> byId = repository.findByRegNumberAndActual(regNumber, true);
            if (byId.isPresent()) {
                Car car = byId.get();
                mapCar.put(regNumber.toLowerCase(), new SoftReference<>(car));
                return car;
            } else {
                String message = String.format("readByRegNumber: runId: '%s', Car with regNumber: '%s' not found.", runId, regNumber);
                logger.error(message);
                throw new ServiceNotFoundRuntimeException(message);
            }
        }
    }
}
