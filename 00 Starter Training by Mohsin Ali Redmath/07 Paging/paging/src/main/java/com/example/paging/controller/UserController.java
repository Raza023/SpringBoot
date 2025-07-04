package com.example.paging.controller;

import com.example.paging.business.UserBusiness;
import com.example.paging.entity.ApiResponse;
import com.example.paging.entity.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/paging")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    //http://localhost:9080/path/api/v1/paging/search?page=0&size=100&name=anyname
    @GetMapping("/search") // Get user records by name
    public ResponseEntity<ApiResponse<List<UserDto>>> findByNameLike(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "name", defaultValue = "") String title) {
        List<UserDto> userDtoList = userBusiness.findByNameLike(page, size, title);

        //No need to do this. because we can return 200 with [] empty list.
        //if (newsList.isEmpty()) {
        //	return ResponseEntity.notFound().build();
        //}

        // strict-origin-when-cross-origin
        //      This is usually the default setting
        //      If the click is on the same site: send the full URL
        //      If the click goes to another site: only send the domain, not the full URL
        //      This is a balanced option between privacy and functionality
        HttpHeaders headers = new HttpHeaders();
        headers.set("Referrer-Policy", "strict-origin-when-cross-origin");

        // Your response logic here
        return ResponseEntity.ok()
                .headers(headers)
                .body(ApiResponse.of(userDtoList));  //we don't have to do any mapping for it.

//    	return ResponseEntity.ok(Map.of("content", userDtoList));
    }


    //http://localhost:9080/path/api/v1/paging/{id}
    @GetMapping("/{id}")
    public String getUserWithID(@PathVariable Long id) {
        UserDto userDto = userBusiness.getUserWithID(id);
        if (ObjectUtils.isEmpty(userDto)) {
            return "User not found.";
        }
        // No LazyInitializationException here now, because we fetched data only in @Transactional
        int postCount = userDto.getPosts().size();
        return "User has " + postCount + " posts.";
    }

}
