package com.store.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadImage(MultipartFile file, String path) throws IOException;

    byte[] getResource(String path, String fileName) throws IOException;

}
