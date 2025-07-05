package org.example.car.service;

import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.MarkModel;
import org.example.car.repository.MarkModelRepository;
import org.example.car.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MarkModelService {

    private final MarkModelRepository markModelRepository;
    Logger logger = LoggerFactory.getLogger(MarkModelService.class);

    public MarkModelService(MarkModelRepository markModelRepository){
        this.markModelRepository = markModelRepository;
    }

    public  ListDataAttributes<List<MarkModel>> readMarkModels(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<MarkModel>>();
        List<MarkModel> allByActual = markModelRepository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    public MarkModel readMarkModelById(UUID markModelId) {
        Optional<MarkModel> byId = markModelRepository.findById(markModelId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ServiceNotFoundRuntimeException(String.format("MarkModel with id %s not found", markModelId));
    }
}
