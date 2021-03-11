package com.example.iasback.services;

import com.example.iasback.payload.response.JwtResponse;
import com.example.iasback.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

   public JwtResponse getCurrentSessionUser(){
       UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return   new JwtResponse(
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              userDetails.getRole(),
              userDetails.getName(),
              userDetails.getSurname(),
              userDetails.getPatronymic());
    }
}
