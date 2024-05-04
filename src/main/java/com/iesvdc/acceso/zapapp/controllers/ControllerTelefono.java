package com.iesvdc.acceso.zapapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.acceso.zapapp.repositories.RepoCodigoPostal;
import com.iesvdc.acceso.zapapp.repositories.RepoTelefono;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;



@Controller
@RequestMapping("/telefono")
public class ControllerTelefono {
    @Autowired
    private RepoTelefono repoTelefono;

    @Autowired
    private RepoCodigoPostal repoCodigoPostal;

    @Autowired
    private RepoUsuario repoUsuario;




}