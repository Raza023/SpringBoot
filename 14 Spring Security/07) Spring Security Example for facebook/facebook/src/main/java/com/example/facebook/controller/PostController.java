package com.example.facebook.controller;

import com.example.facebook.entity.Post;
import com.example.facebook.entity.PostStatusEnum;
import com.example.facebook.entity.User;
import com.example.facebook.repository.PostDataService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostDataService postRepository;

    @PostMapping("/create")
    public String createPost(@RequestBody Post post, Principal principal) {
        post.setPostStatus(PostStatusEnum.PENDING);
        User user = new User();
        user.setUserName(principal.getName());
        post.setUser(user);
        postRepository.save(post);
        return principal.getName() + " Your post published successfully , Required ADMIN/MODERATOR Action !";
    }

    @GetMapping("/approvePost/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approvePost(@PathVariable int postId) {
        Post post = postRepository.findById(postId).get();
        post.setPostStatus(PostStatusEnum.APPROVED);
        postRepository.save(post);
        return "Post Approved !!";
    }

    @GetMapping("/approveAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approveAll() {
        postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatusEnum.PENDING))
                .forEach(post -> {
                    post.setPostStatus(PostStatusEnum.APPROVED);
                    postRepository.save(post);
                });
        return "Approved all posts !";
    }

    @GetMapping("/removePost/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String removePost(@PathVariable int postId) {
        Post post = postRepository.findById(postId).get();
        post.setPostStatus(PostStatusEnum.REJECTED);
        postRepository.save(post);
        return "Post Rejected !!";
    }


    @GetMapping("/rejectAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String rejectAll() {
        postRepository.findAll().stream().filter(post -> post.getPostStatus().equals(PostStatusEnum.PENDING))
                .forEach(post -> {
                    post.setPostStatus(PostStatusEnum.REJECTED);
                    postRepository.save(post);
                });
        return "Rejected all posts !";
    }

    @GetMapping("/viewAll")
    public List<Post> viewAll() {
        return postRepository.findAll().stream()
                .filter(post -> post.getPostStatus().equals(PostStatusEnum.APPROVED))
                .collect(Collectors.toList());
    }
}
