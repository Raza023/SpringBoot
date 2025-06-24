package com.example.paging.business;

import com.example.paging.entity.User;
import com.example.paging.entity.UserDto;
import com.example.paging.repository.UserDataService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public UserDto getUserWithID(Long id) {
        User user = userDataService.findById(id).orElse(null);
        if (Objects.isNull(user)) {
            return null;
        }
        return UserDto.getUserDto(user);
    }

    /**
     * Retrieve user by name.
     *
     * @param page page
     * @param size size
     * @param name name
     * @return List<User>
     */
    @Transactional
    public List<UserDto> findByNameLike(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = new ArrayList<>();
        if (name.isEmpty() || name.isBlank()) {
            users = userDataService.findAll(pageable).getContent();
            return getUserDtos(users);
        }
        users = userDataService.findByNameLikeIgnoreCaseOrderByIdAsc(pageable, "%" + name + "%").getContent();
        return getUserDtos(users); // If no matches are found, this will return an empty list
    }

    private static List<UserDto> getUserDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(UserDto.getUserDto(user)));
        return userDtos;
    }
}
