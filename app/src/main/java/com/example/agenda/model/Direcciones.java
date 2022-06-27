package com.example.agenda.model;

public class Direcciones {
    private int id;
    private int id_contacto;
    private String estado;
    private String municipio;
    private String localidad;
    private String colonia;
    private String calle;
    private String numeroInterior;

    public Direcciones(int id,int id_contacto, String estado, String municipio, String localidad, String colonia, String calle, String numeroInterior) {
        this.id = id;
        this.id_contacto = id_contacto;
        this.estado = estado;
        this.municipio = municipio;
        this.localidad = localidad;
        this.colonia = colonia;
        this.calle = calle;
        this.numeroInterior = numeroInterior;
    }

    public int getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(int id_contacto) {
        this.id_contacto = id_contacto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }
}
