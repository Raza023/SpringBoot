package com.example.security.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {

    private Long id;
    private String name;
    private List<PostData> posts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostData {

        private Long id;
        private String title;
        private Long userId;
    }

    public static UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        List<PostData> posts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getPosts())) {
            for (Post post : user.getPosts()) {
                PostData postData = new PostData();
                postData.setId(post.getId());
                postData.setTitle(post.getTitle());
                postData.setUserId(post.getUser().getId());
                posts.add(postData);
            }
        }
        userDto.setPosts(posts);
        return userDto;
    }
}
