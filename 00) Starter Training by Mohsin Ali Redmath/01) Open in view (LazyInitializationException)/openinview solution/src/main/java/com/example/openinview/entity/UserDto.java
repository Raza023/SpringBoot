package com.example.openinview.entity;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {

    private String name;
    private List<Post> posts;

    public UserDto getUserDto(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPosts(user.getPosts());
        return userDto;
    }
}
