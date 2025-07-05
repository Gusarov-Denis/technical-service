package org.example.serv;

import org.example.serv.service.ToService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ScheduledTasks {
    private final ToService toService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public ScheduledTasks(ToService toService) {
        this.toService = toService;
    }

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        UUID runId = UUID.randomUUID();
        String message = String.format("ScheduledTasks runId: '%s', time: '%s'", runId, dateFormat.format(new Date()));
        logger.info(message);
        List<UUID> all = toService.getIds();
        logger.info(String.format("ScheduledTasks: runId: '%s', Count: '%s'", runId, all.size()));
        for (UUID id : all) {
            try {
                toService.updateToStatus(id, runId);
            } catch (Exception e) {
                String messageErr = String.format("ScheduledTasks: runId: '%s', TO id: '%s', Error: '%s'", runId, id, e);
                logger.error(messageErr);
            }
        }
        logger.info(String.format("ScheduledTasks runId: '%s', time: '%s' Done", runId, dateFormat.format(new Date())));
    }

}
