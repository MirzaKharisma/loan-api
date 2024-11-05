package com.enigmacamp.loanapp.repository;

import com.enigmacamp.loanapp.model.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, String> {
}
