package org.example.serv.service;

import org.example.serv.models.ListDataAttributes;
import org.example.serv.persistence.entity.ToStatus;
import org.example.serv.repository.ToStatusRepository;
import org.example.serv.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ToStatusService {

    private final ToStatusRepository toStatusRepository;
    Logger logger = LoggerFactory.getLogger(ToStatusService.class);

    public ToStatusService(ToStatusRepository toStatusRepository){
        this.toStatusRepository = toStatusRepository;
    }

    public  ListDataAttributes<List<ToStatus>> readToStatuses(UUID runId) {
        ListDataAttributes listDataAttributes = new ListDataAttributes<List<ToStatus>>();
        List<ToStatus> allByActual = toStatusRepository.findAllByActual(true);
        listDataAttributes.setListData(allByActual);
        listDataAttributes.setCount(allByActual.stream().count());
        return listDataAttributes;
    }

    public ToStatus readToStatusById(UUID statusId) {
        Optional<ToStatus> byId = toStatusRepository.findById(statusId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ServiceNotFoundRuntimeException(String.format("ToStatus with id %s not found", statusId));
    }
}
