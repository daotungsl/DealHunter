package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.entity.Media;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @CrossOrigin
    @PostMapping("/api/file/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setData(upload(file))
                .setMessage("Upload file success !").build(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/api/file/uploads")
    public ResponseEntity<Object> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        List<Media> medias = new ArrayList<>();
        for (MultipartFile file: files
             ) {
            Media media = upload(file);
            medias.add(media);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setData(medias)
                .setMessage("Upload file success !").build(), HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/unauthentic/file/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    public Media upload(MultipartFile file){
        if (file.getContentType().equalsIgnoreCase("image/jpeg")
                || file.getContentType().equalsIgnoreCase("image/png")
                || file.getContentType().equalsIgnoreCase("image/gif")
                || file.getContentType().equalsIgnoreCase("video/mp4")){
            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/unauthentic/file/download/")
                    .path(fileName)
                    .toUriString();
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String fileUrl = "";
            try {
                fileUrl = resource.getURL().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Media(fileName, fileDownloadUri, fileUrl,
                    file.getContentType(), file.getSize(), Calendar.getInstance().getTimeInMillis());
        }else {
            return null;
        }
    }
}
