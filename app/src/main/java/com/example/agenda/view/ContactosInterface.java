package com.example.agenda.view;


import com.example.agenda.model.Contactos;
import com.example.agenda.model.Direcciones;


public interface ContactosInterface {
    void agregarEditarContacto(int tipo, Contactos contacto);
    void actualizarContacto(int index, Contactos contacto);
    void actualizarFoto(int index, Contactos contacto);
    void eliminarFoto(int index, int idContacto);
    void detalles(Contactos contacto);
    void formularioDireccion(Direcciones direccion,Contactos contacto);
    void formularioActualizarDireccion(Direcciones direccion,Contactos contacto);
    void registrarDireccion(int tipo,Direcciones direccion);
}
