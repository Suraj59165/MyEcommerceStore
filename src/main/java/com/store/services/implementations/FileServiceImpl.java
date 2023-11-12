package com.store.services.implementations;

import com.store.exceptions.BadApiRequest;
import com.store.repositories.UserRepo;
import com.store.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private UserRepo userRepository;

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String fileName = file.getOriginalFilename();
        String random = UUID.randomUUID().toString();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String fileNameWithExtension = random + extension;
        String fullPAthWithFileName = path + fileNameWithExtension;
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equals("jpeg")) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPAthWithFileName));


        } else {
            throw new BadApiRequest("file with " + extension + "are not allowed");
        }
        return fileNameWithExtension;
    }

    @Override
    public byte[] getResource(String path, String fileName) throws IOException {
        String fullPath = path + File.separator + fileName;
        byte[] bytes = Files.readAllBytes(new File(fullPath).toPath());
        return bytes;
    }

}
