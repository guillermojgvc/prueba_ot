package com.ontop.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	
}