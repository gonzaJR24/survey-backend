package com.survey.survey.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String entrada;

    @Column
    private int hcu;

    @Column
    private Long numero;

    @Column
    private String medio;

    @Column
    private String  municipio;

    @Column
    private String sector;

    @Column
    private String username;

    @Column
    private LocalDateTime date;

    public Data() {
    }

    public Data(int id, String entrada, int hcu, Long numero, String medio, String municipio, String sector, String username) {
        this.id = id;
        this.entrada = entrada;
        this.hcu = hcu;
        this.numero = numero;
        this.medio = medio;
        this.municipio = municipio;
        this.sector = sector;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public int getHcu() {
        return hcu;
    }

    public void setHcu(int hcu) {
        this.hcu = hcu;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
