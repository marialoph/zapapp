package com.iesvdc.acceso.zapapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.acceso.zapapp.models.Telefono;

@Repository
public interface RepoTelefono extends JpaRepository<Telefono, Long> {

}
