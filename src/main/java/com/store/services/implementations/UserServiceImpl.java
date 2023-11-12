package com.store.services.implementations;

import com.store.dtos.UserDto;
import com.store.entitites.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.helper.Helper;
import com.store.payloads.PageableResponse;
import com.store.repositories.UserRepo;
import com.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String path;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(UUID.randomUUID().toString());
        return this.modelMapper.map(userRepository.save(this.modelMapper.map(userDto, User.class)), UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return this.modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public void deleteUser(String userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id " + userId));
        String deletePath = path + user.getImage();
        Files.delete(Paths.get(deletePath));
        userRepository.delete(user);


    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Page<User> page = userRepository.findAll(PageRequest.of(pageNumber, pageSize,
                (sortDirection.equals("desc") ? (Sort.by(sortBy)).descending() : (Sort.by(sortBy)).ascending())));
        return Helper.getPageableResponse(page, UserDto.class);

    }

    @Override
    public UserDto getUserById(String userId) {
        return this.modelMapper.map(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user not found with given id " + userId)), UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return this.modelMapper.map(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with email" + email)), UserDto.class);

    }

    @Override
    public List<UserDto> SearchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        return users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

    }

}
