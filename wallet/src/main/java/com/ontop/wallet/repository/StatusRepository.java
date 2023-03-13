package com.ontop.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.wallet.entities.Status;

@Repository("statusRepository")
public interface StatusRepository extends JpaRepository<Status, Integer> {
}