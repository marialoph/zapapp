package com.iesvdc.acceso.zapapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.iesvdc.acceso.zapapp.models.Usuario;

@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Long> {

    List<Usuario> findByUsername(String username);

}
