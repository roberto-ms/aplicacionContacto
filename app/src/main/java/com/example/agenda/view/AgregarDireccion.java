package com.example.agenda.view;


import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.widget.Toast;


import com.example.agenda.databinding.FragmentAgregarDireccionBinding;
import com.example.agenda.model.Contactos;
import com.example.agenda.model.Direcciones;


public class AgregarDireccion extends DialogFragment {
    private Direcciones direccion;
    private ContactosInterface contactosInterface;
    private FragmentAgregarDireccionBinding agregarDireccionBinding;
    private int idContacto = 0;
    private Contactos contacto;

    public AgregarDireccion(ContactosInterface contactosInterface, Direcciones direccion, Contactos contacto) {
        this.contactosInterface = contactosInterface;
        this.contacto = contacto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agregarDireccionBinding = FragmentAgregarDireccionBinding.inflate(LayoutInflater.from(getContext()));
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(agregarDireccionBinding.getRoot())
                .create();
        render();
        return dialog;
    }

    private void render() {
        agregarDireccionBinding.btnAgregar.setOnClickListener(v -> {
            String estado = agregarDireccionBinding.txtEstado.getText().toString();
            String municipio = agregarDireccionBinding.txtMunicipio.getText().toString();
            String localidad = agregarDireccionBinding.txtLocalidad.getText().toString();
            String colonia = agregarDireccionBinding.txtColonia.getText().toString();
            String calle = agregarDireccionBinding.txtCalle.getText().toString();
            String numeroInterior = agregarDireccionBinding.txtNumeroInterior.getText().toString();
            if (!estado.equals("") && !municipio.equals("") && !localidad.equals("") && !colonia.equals("") && !calle.equals("") && !numeroInterior.equals("")) {
                dismiss();
                contactosInterface.registrarDireccion(new Direcciones(0,contacto.getId(), estado, municipio, localidad, colonia, calle, numeroInterior));
            } else {
                Toast.makeText(getContext(), "Falta datos por llenar", Toast.LENGTH_LONG).show();
            }
        });
        agregarDireccionBinding.btnCancelar.setOnClickListener(v -> {
            dismiss();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        agregarDireccionBinding = null;
    }

}
