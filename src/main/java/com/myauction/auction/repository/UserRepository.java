package com.myauction.auction.repository;

import com.myauction.auction.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    Users findByUsername(String username);
    Users findByRole(String role);
}
