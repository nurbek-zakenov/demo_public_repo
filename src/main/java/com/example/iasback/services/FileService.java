package com.example.iasback.services;

import com.example.iasback.models.File;
import com.example.iasback.models.User;
import com.example.iasback.payload.response.UploadFileResponse;
import com.example.iasback.repository.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileMapper fileMapper;



    public UploadFileResponse uploadFile(MultipartFile file, int eventId) {

        String fileName = fileStorageService.storeFile(file,eventId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    public List<UploadFileResponse> uploadFiles(List<MultipartFile> files, int eventId ) {
        return files.stream()
                .map(item-> uploadFile(item,eventId))
                .collect(Collectors.toList());
    }
    public List<File> files(int id){
        return fileMapper.getFiles(id);
    }
}
