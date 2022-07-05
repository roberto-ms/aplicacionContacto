package com.example.agenda.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.agenda.repository.ContactosRepository;
import com.example.agenda.repository.DireccionesRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private ContactosRepository mainContactosRepository;
    private DireccionesRepository mainDireccionesRepository;

    public ViewModelFactory(ContactosRepository mainContactosRepository, DireccionesRepository mainDireccionesRepository) {
        this.mainContactosRepository = mainContactosRepository;
        this.mainDireccionesRepository = mainDireccionesRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ContactosViewModel(mainContactosRepository, mainDireccionesRepository);
    }
}
