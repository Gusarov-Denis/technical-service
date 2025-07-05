package org.example.oil.service;

import org.example.oil.models.ListDataAttributes;
import org.example.oil.persistence.entity.OilType;
import org.example.oil.repository.OilTypeRepository;
import org.example.oil.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OilTypeService {

    private final OilTypeRepository oilTypeRepository;
    Logger logger = LoggerFactory.getLogger(OilTypeService.class);

    public OilTypeService(OilTypeRepository oilTypeRepository){
        this.oilTypeRepository = oilTypeRepository;
    }

    public  ListDataAttributes<List<OilType>> readOilTypes(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<OilType>>();
        List<OilType> allByActual = oilTypeRepository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    public OilType readOilTypeById(UUID oilTypeId) {
        Optional<OilType> byId = oilTypeRepository.findById(oilTypeId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ServiceNotFoundRuntimeException(String.format("OilType with id %s not found", oilTypeId));
    }
}
