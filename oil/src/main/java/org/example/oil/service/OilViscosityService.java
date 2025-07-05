package org.example.oil.service;

import org.example.oil.models.ListDataAttributes;
import org.example.oil.persistence.entity.OilType;
import org.example.oil.persistence.entity.OilViscosity;
import org.example.oil.repository.OilViscosityRepository;
import org.example.oil.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OilViscosityService {

    private final OilViscosityRepository oilViscosityRepository;
    Logger logger = LoggerFactory.getLogger(OilViscosityService.class);

    public OilViscosityService(OilViscosityRepository oilViscosityRepository){
        this.oilViscosityRepository = oilViscosityRepository;
    }

    public  ListDataAttributes<List<OilViscosity>> readOilViscosities(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<OilViscosity>>();
        List<OilViscosity> allByActual = oilViscosityRepository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    public OilViscosity readOilViscosityById(UUID oilViscosityId) {
        Optional<OilViscosity> byId = oilViscosityRepository.findById(oilViscosityId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ServiceNotFoundRuntimeException(String.format("OilViscosity with id %s not found", oilViscosityId));
    }
}
