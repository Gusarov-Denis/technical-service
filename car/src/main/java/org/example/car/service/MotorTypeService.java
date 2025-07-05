package org.example.car.service;

import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.MotorType;
import org.example.car.repository.MotorTypeRepository;
import org.example.car.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MotorTypeService {

    private final MotorTypeRepository motorTypeRepository;
    Logger logger = LoggerFactory.getLogger(MotorTypeService.class);

    public MotorTypeService(MotorTypeRepository motorTypeRepository){
        this.motorTypeRepository = motorTypeRepository;
    }

    public  ListDataAttributes<List<MotorType>> readMotorTypes(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<MotorType>>();
        List<MotorType> allByActual = motorTypeRepository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    public MotorType readMotorTypeById(UUID motorTypeId) {
        Optional<MotorType> byId = motorTypeRepository.findById(motorTypeId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ServiceNotFoundRuntimeException(String.format("MotorType with id %s not found", motorTypeId));
    }
}
