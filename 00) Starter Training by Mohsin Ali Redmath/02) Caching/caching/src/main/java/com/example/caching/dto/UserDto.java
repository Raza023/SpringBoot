package com.example.caching.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.caching.entity.Post;
import com.example.caching.entity.User;
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
    private String name;
    private String email;
    private List<PostDto> posts;

    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDto {

        private Long id;
        private String title;
        private Long userId;
    }

    public static UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        List<PostDto> posts = new ArrayList<>();
        if(!CollectionUtils.isEmpty(user.getPosts())){
            for (Post post : user.getPosts()) {
                PostDto postDto = new PostDto();
                postDto.setId(post.getId());
                postDto.setTitle(post.getTitle());
                postDto.setUserId(post.getUser().getId());
                posts.add(postDto);
            }
        }
        userDto.setPosts(posts);
        return userDto;
    }

    public static List<UserDto> getUserDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(getUserDto(user));
        });
        return userDtos;
    }

    public static PostDto getPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setUserId(post.getUser().getId());
        return postDto;
    }

    public static List<PostDto> getPostDtos(List<Post> posts) {
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : posts) {
            postDtoList.add(getPostDto(post));
        }
        return postDtoList;
    }

}