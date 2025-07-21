package com.example.security.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {

    private Long id;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<PostData> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(List<PostData> posts) {
//        this.posts = posts;
//    }

    private String name;
    private List<PostData> posts;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostData {

        private Long id;
        private String title;
        private Long userId;

//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public Long getUserId() {
//            return userId;
//        }
//
//        public void setUserId(Long userId) {
//            this.userId = userId;
//        }
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
