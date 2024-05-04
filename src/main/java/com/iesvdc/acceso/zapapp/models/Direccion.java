package com.iesvdc.acceso.zapapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoVia;
    private String nombreVia;
    private String numero;
    private String planta;
    private String puerta;
    private String portal;
    private String nombre;
    @ManyToOne
    private CodigoPostal codigoPostal;
    @ManyToOne
    private Usuario usuario;
}
