package com.iesvdc.acceso.zapapp.repositories;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.acceso.zapapp.models.CodigoPostal;

@Repository
public interface RepoCodigoPostal extends JpaRepository<CodigoPostal, Long> {

    // Optional<CodigoPostal> findByCodigoPostal(String codigoPostal);
}
