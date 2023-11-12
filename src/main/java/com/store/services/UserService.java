package com.store.services;

import com.store.dtos.UserDto;
import com.store.payloads.PageableResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId) throws IOException;

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDirection);

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> SearchUser(String keyword);

}
