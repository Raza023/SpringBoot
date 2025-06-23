package com.example.async.business;

import com.example.async.entity.User;
import com.example.async.entity.UserDto;
import com.example.async.repository.UserDataService;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBusiness {

    private final UserDataService userDataService;

    /**
     * Retrieve user with id.
     *
     * @param id id
     * @return User
     */
    @Async
    @Transactional
    public CompletableFuture<UserDto> getAsyncUserWithID(Long id) {
        long startTimeInMillis = System.currentTimeMillis();
        double startTime = startTimeInMillis / 1000.0;
        User user;
        try {
            log.info("StartTime to fetch user by ID is {}", startTime);
            user = userDataService.findById(id).orElse(null);
            if (Objects.isNull(user)) {
                return CompletableFuture.completedFuture(null);
            }
            // Delay for 3 seconds (3000 milliseconds)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Thread sleep was interrupted.");
            return CompletableFuture.completedFuture(null);
        }
        long elapsedMillis = System.currentTimeMillis() - startTimeInMillis;
        double endTime = elapsedMillis / 1000.0;
        log.info("EndTime to fetch user by ID is {}", endTime);
        return CompletableFuture.completedFuture(UserDto.getUserDto(user));
    }

    /**
     * Retrieve user with name.
     *
     * @param name name
     * @return User
     */
    @Async
    @Transactional
    public CompletableFuture<UserDto> getAsyncUserWithName(String name) {
        long startTimeInMillis = System.currentTimeMillis();
        double startTime = startTimeInMillis / 1000.0;
        User user;
        try {
            log.info("StartTime to fetch user by name is {}", startTime);
            user = userDataService.findByName(name).orElse(null);
            if (Objects.isNull(user)) {
                return CompletableFuture.completedFuture(null);
            }
            // Delay for 5 seconds (5000 milliseconds)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Thread sleep was interrupted.");
            return CompletableFuture.completedFuture(null);
        }
        long elapsedMillis = System.currentTimeMillis() - startTimeInMillis;
        double endTime = elapsedMillis / 1000.0;
        log.info("EndTime to fetch user by name is {}", endTime);
        return CompletableFuture.completedFuture(UserDto.getUserDto(user));
    }

    /**
     * Retrieve user with id.
     *
     * @param id id
     * @return User
     */
    @Transactional
    public UserDto getSyncUserWithID(Long id) {
        long startTimeInMillis = System.currentTimeMillis();
        double startTime = startTimeInMillis / 1000.0;
        User user = new User();
        try {
            log.info("StartTime to fetch user by ID is {}", startTime);
            user = userDataService.findById(id).orElse(null);
            if (Objects.isNull(user)) {
                return null;
            }
            // Delay for 3 seconds (3000 milliseconds)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Thread sleep was interrupted.");
        }
        long elapsedMillis = System.currentTimeMillis() - startTimeInMillis;
        double endTime = elapsedMillis / 1000.0;
        log.info("EndTime to fetch user by ID is {}", endTime);
        return UserDto.getUserDto(user);
    }

    /**
     * Retrieve user with name.
     *
     * @param name name
     * @return User
     */
    @Transactional
    public UserDto getSyncUserWithName(String name) {
        long startTimeInMillis = System.currentTimeMillis();
        double startTime = startTimeInMillis / 1000.0;
        User user = new User();
        try {
            log.info("StartTime to fetch user by name is {}", startTime);
            user = userDataService.findByName(name).orElse(null);
            if (Objects.isNull(user)) {
                return null;
            }
            // Delay for 5 seconds (5000 milliseconds)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Thread sleep was interrupted.");
        }
        long elapsedMillis = System.currentTimeMillis() - startTimeInMillis;
        double endTime = elapsedMillis / 1000.0;
        log.info("EndTime to fetch user by name is {}", endTime);
        return UserDto.getUserDto(user);
    }

}
