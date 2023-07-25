package com.blog.application.controllers;

import com.blog.application.payloads.FileResponse;
import com.blog.application.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("image")MultipartFile image){
        String fileName = null;
        try {
            fileName = this.fileService.uploadImage(path, image);
        } catch (Exception e) {
            return new ResponseEntity<FileResponse>(
                    new FileResponse(null, "Error Occurred : Unable to Upload file!!!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<FileResponse>(
                new FileResponse(fileName, "File Uploaded successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable(value = "imageName") String imageName,
                              HttpServletResponse httpServletResponse) throws Exception {
        InputStream inputStream = fileService.getResource(path, imageName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }
}
