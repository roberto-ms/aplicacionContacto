package com.example.agenda.viewmodel;

import android.graphics.Bitmap;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.agenda.model.Contactos;
import com.example.agenda.model.Direcciones;
import com.example.agenda.repository.ContactosRepository;
import com.example.agenda.repository.DireccionesRepository;

import java.util.List;

public class ContactosViewModel extends ViewModel {
    private ContactosRepository mainContactosRepository;
    private MutableLiveData<List<Contactos>> contactosData;
    private MutableLiveData<Pair<Integer, Contactos>> agregarEditarContacto;
    private MutableLiveData<Boolean> deleteContacto;
    private DireccionesRepository mainDireccionesRepository;
    private MutableLiveData<List<Direcciones>> direccionesData;
    private MutableLiveData<Pair<Integer, Direcciones>> agregarEditarDireccion;
    private MutableLiveData<Boolean> deleteDireccion;
    private MutableLiveData<Pair<Integer,Direcciones>> obtenerDireccionContacto;

    public ContactosViewModel(ContactosRepository mainContactosRepository, DireccionesRepository mainDireccionesRepository) {
            this.mainContactosRepository = mainContactosRepository;
            this.contactosData = new MutableLiveData<>();
            this.agregarEditarContacto = new MutableLiveData<>();
            this.deleteContacto = new MutableLiveData<>();
            //---------------------------------------------------
            this.mainDireccionesRepository = mainDireccionesRepository;
            this.direccionesData = new MutableLiveData<>();
            this.agregarEditarDireccion = new MutableLiveData<>();
            this.deleteDireccion = new MutableLiveData<>();
    }

    public MutableLiveData<List<Contactos>> obtenerContactos() {
        return contactosData;
    }

    public MutableLiveData<Pair<Integer, Contactos>> obtenerAgregarEditarContacto() {
        return agregarEditarContacto;
    }

    public MutableLiveData<Boolean> gobtenerDeleteContacto() {
        return deleteContacto;
    }

    public void cargarContactos(int orden) {
        contactosData.setValue(mainContactosRepository.listaContactos(orden));
    }

    public void registrarActualizarContacto(int tipo, Contactos contacto) {
        if (tipo == 1) {
            agregarEditarContacto.setValue(new Pair<>(tipo, mainContactosRepository.registrarContacto(contacto.getNombre(), contacto.getApellidoPaterno(), contacto.getApellidoMaterno(), contacto.getTelefono())));
        } else {
            agregarEditarContacto.setValue(new Pair<>(tipo, mainContactosRepository.actualizarContacto(contacto)));
        }
    }

    public void actualizarFoto(int idContacto, Bitmap foto) {
        agregarEditarContacto.setValue(new Pair<>(2, mainContactosRepository.guardarFoto(idContacto, foto)));
    }

    public void eliminarContacto(int idContacto) {
        deleteContacto.setValue(mainContactosRepository.eliminarContacto(idContacto));
    }

    public void registrarActualizarDireccion(int tipo, Direcciones direccion,int idContacto) {
        if (tipo == 1){
            agregarEditarDireccion.setValue(new Pair<>(tipo,mainDireccionesRepository.actualizarDireccion(direccion,idContacto)));
        }else{
            agregarEditarDireccion.setValue(new Pair<>(tipo,mainDireccionesRepository.registrarDireccion(idContacto,direccion.getEstado(),direccion.getMunicipio(),direccion.getLocalidad(),direccion.getColonia(),direccion.getCalle(),direccion.getNumeroInterior())));
        }
    }
}

