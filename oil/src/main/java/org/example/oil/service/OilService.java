package org.example.oil.service;

import org.example.oil.models.ListDataAttributes;
import org.example.oil.models.OilReq;
import org.example.oil.persistence.entity.Oil;
import org.example.oil.persistence.mappers.OilMapper;
import org.example.oil.repository.OilRepository;
import org.example.oil.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.oil.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OilService {

    private final OilRepository repository;
    private final OilTypeService oilTypeService;
    private final OilViscosityService oilViscosityService;
    Logger logger = LoggerFactory.getLogger(OilService.class);
    private final OilMapper oilMapper;

    public OilService(OilRepository repository, OilMapper oilMapper, OilTypeService oilTypeService, OilViscosityService oilViscosityService){
        this.repository = repository;
        this.oilMapper = oilMapper;
        this.oilTypeService = oilTypeService;
        this.oilViscosityService = oilViscosityService;
    }

    public  ListDataAttributes<List<Oil>> readOils(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<Oil>>();
        List<Oil> allByActual = repository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    public UUID add(OilReq req, UUID runId) {
        Oil entity = oilMapper.dtoToEntity(req);
        checkExistName(runId, entity.getName());
        entity.setOilType(oilTypeService.readOilTypeById(req.getOilTypeId()));
        entity.setOilViscosity(oilViscosityService.readOilViscosityById(req.getOilViscosityId()));
        entity.setActual(true);
        Oil save;
        save = repository.save(entity);
        UUID uuid = save.getId();
        logger.info(String.format("add Done: runId: '%s', id: '%s'", runId, uuid));
        return uuid;
    }

    public Oil read(UUID id, UUID runId) {
        Optional<Oil> byId = repository.findById(id);
        if (byId.isPresent())
        {
            Oil oil = byId.get();
            //check
            checkActual("readOilById", runId, oil.getId(), oil.getActual());
            return oil;
        }
        else {
            String message = String.format("readOilById: runId: '%s', Oil with id: '%s' not found.", runId, id);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }

    private void checkActual(String methodName, UUID runId, UUID id, boolean actual) {
        if(!actual){
            String message = String.format("%s: runId: '%s', Oil with id: '%s' not found.", methodName, runId, id);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }

    private void checkExistName(UUID runId, String name) {
        Optional<Oil> byNameAndActual = repository.findByNameAndActual(name, true);
        if(byNameAndActual.isPresent()){
            String message = String.format("checkExistName: runId: '%s', Oil with name: '%s' already exist.", runId, name);
            logger.error(message);
            throw new ServiceEntityExistRuntimeException(message);
        }

    }
}
