package com.example.async.controller;

import com.example.async.business.UserBusiness;
import com.example.async.entity.UserDto;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/async")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    //http://localhost:9080/path/api/v1/async/with-async?id=1&name=Bob
    @GetMapping("/with-async")
    public String getUserDataWithAsync(@RequestParam Long id, @RequestParam String name)
            throws ExecutionException, InterruptedException {
        long startTimeInMillis = System.currentTimeMillis();
        double startTime = startTimeInMillis / 1000.0;
        log.info("StartTime to fetch user data is {}", startTime);
        CopyOnWriteArrayList<CompletableFuture<?>> completableFutures = new CopyOnWriteArrayList<>();
        CompletableFuture<UserDto> userDtoID = userBusiness.getAsyncUserWithID(id);
        if (ObjectUtils.isEmpty(userDtoID)) {
            return "User with ID = " + id + " not found.";
        }
        completableFutures.add(userDtoID);
        CompletableFuture<UserDto> userDtoName = userBusiness.getAsyncUserWithName(name);
        if (ObjectUtils.isEmpty(userDtoName)) {
            return "User with Name = " + name + " not found.";
        }
        completableFutures.add(userDtoName);
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        long elapsedMillis = System.currentTimeMillis() - startTimeInMillis;
        double endTime = elapsedMillis / 1000.0;
        log.info("EndTime to fetch user data is {}", endTime);
        return "It took " + endTime + " time to fetch all user data (id = " + userDtoID.get().getId()
                + " name = " + userDtoName.get().getName() + ") asynchronously.";
    }

    //http://localhost:9080/path/api/v1/async/with-sync?id=1&name=Bob
    @GetMapping("/with-sync")
    public String getUserDataWithSync(@RequestParam Long id, @RequestParam String name) {
        long startTimeInMillis = System.currentTimeMillis();
        double startTime = startTimeInMillis / 1000.0;
        log.info("StartTime to fetch user data is {}", startTime);
        UserDto userDtoID = userBusiness.getSyncUserWithID(id);
        if (ObjectUtils.isEmpty(userDtoID)) {
            return "User with ID = " + id + " not found.";
        }
        UserDto userDtoName = userBusiness.getSyncUserWithName(name);
        if (ObjectUtils.isEmpty(userDtoName)) {
            return "User with Name = " + name + " not found.";
        }
        long elapsedMillis = System.currentTimeMillis() - startTimeInMillis;
        double endTime = elapsedMillis / 1000.0;
        log.info("EndTime to fetch user data is {}", endTime);
        return "It took " + endTime + " time to fetch all user data (id = " + userDtoID.getId()
                + " name = " + userDtoName.getName() + ") synchronously.";
    }

}
