package com.example.caching.business;

import com.example.caching.dto.UserDto;
import com.example.caching.entity.User;
import com.example.caching.repository.UserDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserBusiness {

    private final UserDataService userDataService;
    private final UserDto userDto;

    // Get all users (no cache)
    @Transactional
    public List<UserDto> getUsers() {
        return UserDto.getUserDtos(userDataService.findAll());
    }

    // Cache by ID
    @Transactional
    @Cacheable(value = "userIdCache", key = "#id")
    public UserDto getUserById(Long id) {
        return userDataService.findById(id)
                .map(UserDto::getUserDto)
                .orElse(null);
    }

    // Cache by email
    @Transactional
    @Cacheable(value = "userEmailCache", key = "#email")
    public UserDto getUserByEmail(String email) {
        return userDataService.findByEmail(email)
                .map(UserDto::getUserDto)
                .orElse(null);
    }

    //@CachePut is evaluated after the method execution.
    //#result refers to the method's return value — in this case, a UserDto object.
    // Save new user (put in both caches)
    @Caching(put = {
            @CachePut(value = "userIdCache", key = "#result.id"),
            @CachePut(value = "userEmailCache", key = "#result.email")
    })
    @Transactional
    public UserDto saveUser(User user) {
        return UserDto.getUserDto(userDataService.saveAndFlush(user));
    }

    //@CachePut is evaluated after the method execution.
    //#result refers to the method's return value — in this case, a UserDto object.
    // Update user (put in both caches)
    @Caching(put = {
            @CachePut(value = "userIdCache", key = "#result.id"),
            @CachePut(value = "userEmailCache", key = "#result.email")
    })
    @Transactional
    public UserDto updateUser(Long id, User updatedUser) {
        return userDataService.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setEmail(updatedUser.getEmail());
                    return userDataService.saveAndFlush(existingUser);
                }).map(UserDto::getUserDto).orElse(null);
    }

    //@CacheEvict is evaluated before the method execution, by default.
    //That's why we use a local variable (user) fetched before deletion.
    //user object is something we fetched inside the method to access these keys.
    // Delete user (evict from both caches)
    @Caching(evict = {
            @CacheEvict(value = "userIdCache", key = "#user.id"),
            @CacheEvict(value = "userEmailCache", key = "#user.email")
    })
    @Transactional
    public int deleteUser(Long id) {
        User user = userDataService.findById(id).orElse(null);
        if (Objects.isNull(user)) {
            return 0;
        }
        userDataService.deleteById(id);
        userDataService.flush();
        return 1;
    }
}
