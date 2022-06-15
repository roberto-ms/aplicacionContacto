package com.example.agenda.viewmodel;

import android.graphics.Bitmap;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.agenda.model.Contactos;
import com.example.agenda.repository.ContactosRepository;

import java.util.List;

public class ContactosViewModel extends ViewModel {
    private ContactosRepository mainContactosRepository;
    private MutableLiveData<List<Contactos>> contactosData;
    private MutableLiveData<Pair<Integer, Contactos>> agregarEditarContacto;
    private MutableLiveData<Boolean> deleteContacto;

    public ContactosViewModel(ContactosRepository mainContactosRepository) {
        this.mainContactosRepository = mainContactosRepository;
        this.contactosData = new MutableLiveData<>();
        this.agregarEditarContacto = new MutableLiveData<>();
        this.deleteContacto = new MutableLiveData<>();

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

}

