package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupMediaDto.Media;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.fileAndMail.FileStorageService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

@RestController
public class FileController {
    HashMap<String, String> hashMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    AccountRepository accountRepository;

    @CrossOrigin
    //@PostMapping("/api/file/upload")
    @RequestMapping(value = "/api/file/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 0){
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                if (file != null){
                    if (isValidFileType(file)){
                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.OK.value())
                                .setData(upload(file))
                                .setMessage("Upload file success !").build(), HttpStatus.OK);
                    }else {
                        hashMap.clear();
                        hashMap.put("File-Type", "File type not support to upload !");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData(new Media(file.getOriginalFilename(), "Upload Fail : file type " + file.getContentType() + " not support !", file.getContentType(), file.getSize()))
                                .setMessage("Upload data has errors !").build(), HttpStatus.FORBIDDEN);
                    }
                }else {
                    hashMap.clear();
                    hashMap.put("File", "File upload can't null !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData(StringUtils.EMPTY)
                            .setMessage("Upload data has errors !").build(), HttpStatus.FORBIDDEN);
                }
            }
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    //@PostMapping("/api/file/uploads")
    @RequestMapping(value = "/api/file/uploads", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, HttpServletRequest request){
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 0){
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                if (files != null){
                    if (isValidFilesType(files)){
                        List<Media> medias = new ArrayList<>();
                        for (MultipartFile file: files
                        ) {
                            Media media = upload(file);
                            medias.add(media);
                        }
                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.OK.value())
                                .setData(medias)
                                .setMessage("Upload files success !").build(), HttpStatus.OK);
                    }else {
                        hashMap.clear();
                        hashMap.put("File-Type", "Upload file list has at least one file that is not supported !");
                        hashMap.put("Support", "image/jpeg || image/png || image/gif || video/mp4");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData(StringUtils.EMPTY)
                                .setMessage("Upload data has errors !").build(), HttpStatus.FORBIDDEN);
                    }
                }else {
                    hashMap.clear();
                    hashMap.put("File", "File upload can't null !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData(StringUtils.EMPTY)
                            .setMessage("Upload data has errors !").build(), HttpStatus.FORBIDDEN);
                }
            }
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }


    @CrossOrigin
    //GetMapping("/unauthentic/file/download/{fileName:.+}")
    @RequestMapping(value = "/unauthentic/file/download/{fileName:.+}", method = RequestMethod.GET)
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
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/unauthentic/file/download/")
                .path(fileName)
                .toUriString();
        return new Media(fileName, fileDownloadUri, "Upload success !",
                file.getContentType(), file.getSize(), Calendar.getInstance().getTimeInMillis());
    }

    public boolean isValidFileType(MultipartFile file){
        if (file.getContentType().equalsIgnoreCase("image/jpeg")
                || file.getContentType().equalsIgnoreCase("image/png")
                || file.getContentType().equalsIgnoreCase("image/gif")
                || file.getContentType().equalsIgnoreCase("video/mp4")){
            return true;
        }else {
            return false;
        }
    }

    public boolean isValidFilesType(MultipartFile[] files){
        List<Boolean> check = new ArrayList<>();
        for (MultipartFile file: files
             ) {
            if (isValidFileType(file)){
                check.add(true);
            }else {
                check.add(false);
            }
        }
        boolean end = true;
        for (Boolean b : check){
            if (!b){
                end = false;
                break;
            }
        }
        return end;
    }
}
