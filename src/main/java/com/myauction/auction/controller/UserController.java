package com.myauction.auction.controller;

import com.myauction.auction.dto.UserRegisterDto;
import com.myauction.auction.dto.UserLoginDto;
import com.myauction.auction.service.CustomUserDetailsService;
import com.myauction.auction.service.JwtUtil;
import com.myauction.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegisterDto user) throws Exception {
        return userService.registerUser(user);
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginDto user) throws Exception{
//        if (user.getUsername() == null || user.getPassword() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is missing");
//        }
//
//        Authentication authenticate = null;
//        try{
//            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        }catch (BadCredentialsException e){
//            throw new Exception("Invalid Credentiials");
//        }
//
//        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
//
////        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
//        if (authenticate.isAuthenticated()) {
//            try {
//                String jwt = jwtUtil.generateToken(userDetails);
//                return ResponseEntity.ok(Collections.singletonMap("jwt", jwt));
//            } catch (Exception e) {
//                throw new Exception(e);
//            }
//        }
//        return new ResponseEntity<>("Invalid credential", HttpStatus.BAD_REQUEST);
//    }
        @PostMapping("/login")
       public ResponseEntity<?> login(@RequestBody UserLoginDto user ) throws Exception {
        return new ResponseEntity<>(userService.verify(user),HttpStatus.ACCEPTED);
    }


}
