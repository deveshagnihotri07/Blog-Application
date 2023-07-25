package com.blog.application.services.implementation;

import com.blog.application.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws Exception {

        //FileName
        String fileName = file.getOriginalFilename();

        String randomString = UUID.randomUUID().toString();
        fileName = randomString.concat(fileName.substring(fileName.lastIndexOf(".")));

        //Full Path
        String filePath = path+ File.separator+fileName;

        //Create folder for images if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws Exception {
        String filePath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }
}
