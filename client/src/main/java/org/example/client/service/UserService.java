package org.example.client.service;

import org.example.client.hashutil.PasswordHash;
import org.example.client.models.UserReq;
import org.example.client.persistence.entity.User;
import org.example.client.persistence.mappers.UserMapper;
import org.example.client.repository.UserRepository;
import org.example.client.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.client.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Map<String, SoftReference<byte[]>> map = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${hash.alg}")
    private String hash_alg;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UUID add(UserReq req, UUID runId){
        User entity = userMapper.dtoToEntity(req);

        String login = entity.getLogin();
        String password = entity.getPassword();

        SoftReference<byte[]> bytes = map.get(login);
        if(bytes != null && bytes.get() != null){
            userExist(runId, login);
        } else {
            Optional<User> byLogin = userRepository.findByLogin(login);
            if (byLogin.isPresent()) {

                getAndSaveHashedPassword(login, password);
                userExist(runId, login);
            }
        }

        User save = userRepository.save(entity);
        UUID uuid = null;
        if(save != null) {
            uuid = save.getId();
            getAndSaveHashedPassword(login, password);
        }
        logger.info(String.format("User add Done: runId: '%s', aggregateId: '%s'", runId, uuid));
        return uuid;
    }

    private void getAndSaveHashedPassword(String login, String password) {
        byte[] digest = PasswordHash.createPasswordHash(password,hash_alg);
        map.put(login, new SoftReference<>(digest));
    }


    private void userExist(UUID runId, String login) {
        String message = String.format("User add: runId: '%s', User with login: '%s' exist.", runId, login);
        logger.warn(message);
        throw new ServiceEntityExistRuntimeException(message);
    }

    public String readEmailByLogin(String login, UUID runId) {
        Optional<User> byLogin = userRepository.findByLogin(login);
        if (byLogin.isPresent()) {
            User user = byLogin.get();
            return user.getEmail();
        }
        else {
            String message = String.format("readEmailByLogin: runId: '%s', User with login: '%s' not found.", runId, login);
            logger.error(message);
            throw new ServiceNotFoundRuntimeException(message);
        }
    }
}
