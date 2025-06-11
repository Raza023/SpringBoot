package com.example.openinview.business;

import com.example.openinview.entity.User;
import com.example.openinview.entity.UserDto;
import com.example.openinview.repository.UserDataService;
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
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPosts(user.getPosts());
        return userDto;
    }


}
