package com.blog.application.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    String uploadImage(String path, MultipartFile file) throws Exception;

    InputStream getResource(String path, String fileName) throws Exception;
}
