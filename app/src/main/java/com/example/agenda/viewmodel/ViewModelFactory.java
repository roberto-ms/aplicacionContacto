package com.example.agenda.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.agenda.repository.ContactosRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private ContactosRepository mainContactosRepository;


    public ViewModelFactory(ContactosRepository mainContactosRepository) {
        this.mainContactosRepository = mainContactosRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ContactosViewModel(mainContactosRepository);
    }
}
