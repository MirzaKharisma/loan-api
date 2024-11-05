package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.model.entity.CustomerProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfilePictureRepository extends JpaRepository<CustomerProfilePicture, String> {
}
