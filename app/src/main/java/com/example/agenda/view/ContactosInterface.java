package com.example.agenda.view;


import com.example.agenda.model.Contactos;

public interface ContactosInterface {
    void agregarEditarContacto(int tipo, Contactos contacto);
    void actualizarContacto(int index, Contactos contacto);
    void actualizarFoto(int index, Contactos contacto);
    void eliminarFoto(int index, int idContacto);
}
