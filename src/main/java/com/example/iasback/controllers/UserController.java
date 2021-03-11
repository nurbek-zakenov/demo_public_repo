package com.example.iasback.controllers;

import com.example.iasback.models.Event;
import com.example.iasback.models.File;
import com.example.iasback.models.User;
import com.example.iasback.payload.request.SignupRequest;
import com.example.iasback.payload.response.JwtResponse;
import com.example.iasback.payload.response.MessageResponse;

import com.example.iasback.payload.response.UploadFileResponse;
import com.example.iasback.services.FileService;
import com.example.iasback.services.FileStorageService;
import com.example.iasback.services.SessionService;
import com.example.iasback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SessionService sessionService;


    @Autowired
    private FileService fileService;


    @Autowired
    private FileStorageService fileStorageService;



    @GetMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> users(boolean bool) {
        return userService.listUser(bool);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createUser( @RequestBody SignupRequest signupRequest) {

        if(userService.existUserByUsername(signupRequest.getUsername())){
            return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Логин Занят"));
        }

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getName(),
                signupRequest.getSurname(),
                signupRequest.getPatronymic(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getRole_id()
        );

        return ResponseEntity.ok(userService.createUser(user))  ;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(int userId, boolean bool, int roleId) {

        if(roleId==1){
            if(userService.checkAdminCounts()){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Невозможно удаление так как минимальное количество администраторов должно оставаться равным 3 "));
            }
        }


        return ResponseEntity.ok(userService.deleteUser(userId, bool));
    }

    @GetMapping("/recovery")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Integer recoveryUser(int userId, boolean bool) {
        return userService.recoveryUser(userId, bool);
    }

    @GetMapping("/remove")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Integer removeUser(int userId) {
        return userService.removeUser(userId);

    }


    @GetMapping("/events")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Event> getEvents () {
        return userService.getEvents();
    }


    @PostMapping("/add_event")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Event createEvent( @RequestBody Event event) {
        return userService.addEvent(event);
    }


    @PostMapping("/edit_event")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Event editUser(@RequestBody Event event) {
        return userService.editEvent(event);
    }




    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("eventId") int eventId, @RequestParam(value = "file") List<MultipartFile> files) {
        return fileService.uploadFiles(files,eventId);

    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Ошибка");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/get_files")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<File> getFiles(int id) {
        return fileService.files(id);
    }


}
