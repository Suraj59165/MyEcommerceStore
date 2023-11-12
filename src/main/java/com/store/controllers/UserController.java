package com.store.controllers;

import com.store.dtos.UserDto;
import com.store.entitites.User;
import com.store.payloads.ImageResponse;
import com.store.payloads.PageableResponse;
import com.store.repositories.UserRepo;
import com.store.services.FileService;
import com.store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepo userRepo;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable(value = "user-id") String userId,
                                              @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "user-id") String id) throws IOException {
        userService.deleteUser(id);
        return new ResponseEntity<>("user is deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pagesize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pagesize, sortBy, sortDirection),
                HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserDto> getSingleUserById(@PathVariable(value = "user-id") String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{user-email}")
    public ResponseEntity<UserDto> getSingleUserByEmail(@PathVariable(value = "user-email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable(value = "keywords") String keywords) {
        return new ResponseEntity<>(userService.SearchUser(keywords), HttpStatus.OK);
    }

    @PostMapping("/image/{user-id}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam(value = "userImage") MultipartFile file,
                                                         @PathVariable(value = "user-id") String id) throws IOException {


        String imagename = fileService.uploadImage(file, imageUploadPath);
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User user2 = user.get();
            user2.setImage(imagename);
            userRepo.save(user2);
        }
        ImageResponse imageResponse = ImageResponse.builder().imageName(imagename).success(true)
                .status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }

    @GetMapping("image/{userId}")
    public ResponseEntity<?> serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto userdto = userService.getUserById(userId);
        byte[] imageData = fileService.getResource(imageUploadPath, userdto.getImage());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(imageData);


    }

}
