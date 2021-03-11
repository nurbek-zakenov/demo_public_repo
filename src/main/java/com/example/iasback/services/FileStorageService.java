package com.example.iasback.services;


import com.example.iasback.exception.FileStorageException;
import com.example.iasback.exception.MyFileNotFoundException;
import com.example.iasback.models.File;
import com.example.iasback.property.FileStorageProperties;
import com.example.iasback.repository.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;


@Service
public class FileStorageService {

    private final Path fileStorageLocation;


    @Autowired
    private FileMapper fileMapper;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Не получается создать директорию для загрухочных файлов", ex);
        }
    }

    public String storeFile(MultipartFile file, int  eventId) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileSystemName = originalFileName + new Date().getTime();

        try {
            if(fileSystemName.contains("..")) {
                throw new FileStorageException("Имя файла содержит недопустимые символы" + fileSystemName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileSystemName);


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            File dbFile = new File();
            dbFile.setOriginal_name(originalFileName);
            dbFile.setExtension(getFileExtension(originalFileName));
            dbFile.setPath(targetLocation.toString());
            dbFile.setFs_name(fileSystemName);
            dbFile.setEvent_id(eventId);
            System.out.println(dbFile);
            fileMapper.addFile(dbFile);
            return fileSystemName;
        } catch (IOException ex) {
            throw new FileStorageException("Не получается сохранить " + fileSystemName + ".", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("Файл не найден " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("Файл не найден" + fileName, ex);
        }
    }

    public String getFileExtension(String fileName){
        String extension = "other";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

}
