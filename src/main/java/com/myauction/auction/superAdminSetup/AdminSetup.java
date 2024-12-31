package com.myauction.auction.superAdminSetup;

import com.myauction.auction.model.Users;
import com.myauction.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class AdminSetup implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByRole("SUPER_ADMIN") == null) {
            Users superAdmin = new Users();
            superAdmin.setEmail("superadmin@gmail.com");
            superAdmin.setUsername("superadmin");
            superAdmin.setPassword(passwordEncoder.encode("super@123"));
            superAdmin.setRole("SUPER_ADMIN");
            userRepository.save(superAdmin);
            System.out.println("Super Admin created!");
        }
    }
}


