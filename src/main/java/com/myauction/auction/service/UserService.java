package com.myauction.auction.service;


import com.myauction.auction.dto.UserLoginDto;
import com.myauction.auction.dto.UserRegisterDto;
import com.myauction.auction.model.Users;

import com.myauction.auction.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserService userService;

    public String registerUser(UserRegisterDto user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists");
        }

        List<String> userNames = userRepository.findAll().stream().map(Users::getUsername).toList();

        Users user1 = new Users();
        user1.setEmail(user.getEmail());
        user1.setUsername(user.getUsername());
        user1.setPassword(encoder.encode(user.getPassword()));
        user1.setRole("USER");
        userRepository.save(user1);
        return "Registered as user";
    }

    public Users registerAdmin(UserRegisterDto user) throws Exception {
        log.info("Admin added");
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username is already exists");
        }
        Users user1 = new Users();
        user1.setEmail(user.getEmail());
        user1.setUsername(user.getUsername());
        user1.setPassword(encoder.encode(user.getPassword()));
        user1.setRole("ADMIN");
        return userRepository.save(user1);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();  // Fetches all users
    }

    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String verify(UserLoginDto user) throws Exception {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
                String jwt = jwtUtil.generateToken(userDetails);
                return jwt;
            }

            return "Login Failed";
        } catch (Exception e) {
            return "Invalid Credentials";
        }
    }






//        Authentication authenticate = null;
//        try{
//        authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        if ()
//        }catch (
//    BadCredentialsException e){
//        throw new Exception("Invalid Credentiials");
//    }
////        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
//        if(authenticate.isAuthenticated()){
//            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
//            String jwt = jwtUtil.generateToken(userDetails);
//            return "Success "+ jwt;
//        }
//        return "Failed to Login";
//}

}





// if (user.getUsername() == null || user.getPassword() == null) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is missing");
//    }
//
//    Authentication authenticate = null;
//        try{
//        authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//    }catch (
//    BadCredentialsException e){
//        throw new Exception("Invalid Credentiials");
//    }
//
//    UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
//
////        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
//        if (authenticate.isAuthenticated()) {
//        try {
//            String jwt = jwtUtil.generateToken(userDetails);
//            return ResponseEntity.ok(Collections.singletonMap("jwt", jwt));
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
//    }
//        return new ResponseEntity<>("Invalid credential", HttpStatus.BAD_REQUEST);
//}


