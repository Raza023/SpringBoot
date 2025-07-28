package com.example.security.business;

import com.example.security.model.Post;
import com.example.security.model.User;
import com.example.security.model.UserDto;
import com.example.security.repository.UserDataService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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

    /**
     * Get all users without posts.
     *
     * @return list of UserDto
     */
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<User> users = userDataService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            // Optionally clear posts if you want to exclude them
            user.setPosts(null);
            UserDto dto = UserDto.getUserDto(user);
            userDtos.add(dto);
        }
        return userDtos;
    }

    /**
     * Get all users with their posts.
     *
     * @return list of UserDto
     */
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersWithPosts() {
        List<User> users = userDataService.findAllWithPosts();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.getUserDto(user));
        }
        return userDtos;
    }

    /**
     * Add multiple random dummy users.
     *
     * @param count number of users to add
     * @return number of users added
     */
    @Transactional
    public int addDummyUsers(int count) {
        List<User> users = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setName("DummyUser" + random.nextInt(100000));

            // Add random posts to user (1-5)
            int postCount = 1 + random.nextInt(5);
            List<Post> posts = new ArrayList<>();
            for (int j = 0; j < postCount; j++) {
                Post post = new Post();
                post.setTitle("Post " + (j + 1) + " for " + user.getName());
                post.setUser(user);
                posts.add(post);
            }
            user.setPosts(posts);
            users.add(user);
        }
        userDataService.saveAllAndFlush(users);
        return users.size();
    }

}
