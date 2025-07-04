package com.example.scheduling.business;

import com.example.scheduling.entity.User;
import com.example.scheduling.entity.UserDto;
import com.example.scheduling.repository.UserDataService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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

}
