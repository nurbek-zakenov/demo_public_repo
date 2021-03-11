package com.example.iasback.services;

import com.example.iasback.models.Event;
import com.example.iasback.models.User;
//import com.example.iasback.repository.UserRepository;
import com.example.iasback.payload.response.MessageResponse;
import com.example.iasback.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    public List<User> listUser( boolean bool){
        return userMapper.allUsers(bool);
    }


    public User createUser(User user){
        System.out.println(user);
        return userMapper.createUser(user);
    }


    public User editUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.editUser(user);
    }


    public Integer deleteUser(Integer userId, boolean bool){
        System.out.println(userId);
        System.out.println(userMapper.deleteUser(userId, bool));
        return userId ;
    }

    public Integer removeUser(Integer userId){
        System.out.println(userId);
        System.out.println(userMapper.removeUser(userId ));
        return userId ;
    }

    public Integer recoveryUser(Integer userId, boolean bool){
        System.out.println(userId);
        System.out.println(userMapper.recoveryUser(userId, bool));
        return userId ;
    }



    public boolean existUserByUsername(String username){
        return userMapper.existsByUsername(username);

    }



    public boolean checkAdminCounts(){
        return userMapper.checkAdminCounts();

    }


    public List<Event> getEvents() {
        System.out.println(userMapper.getEvents());
       return userMapper.getEvents();
    }

    public Event addEvent(Event event) {
        return userMapper.createEvent(event);
    }

    public Event editEvent(Event event) {
        return userMapper.editEvent(event);
    }
}
