package com.example.agenda.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.agenda.databinding.FragmentAgregarContactoBinding;
import com.example.agenda.model.Contactos;


public class AgregarContacto extends DialogFragment {
    private int tipo;
    private Contactos contacto;
    private ContactosInterface eventoEdicionContacto;
    private FragmentAgregarContactoBinding agregarContactoBinding;
    private int idContacto = 0;

    public AgregarContacto(ContactosInterface eventoEdicionContacto, int tipo, Contactos contacto) {
        this.tipo = tipo;
        this.contacto = contacto;
        this.eventoEdicionContacto = eventoEdicionContacto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agregarContactoBinding = FragmentAgregarContactoBinding.inflate(LayoutInflater.from(getContext()));
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(agregarContactoBinding.getRoot())
                .create();
        render();
        return dialog;
    }


    private void render() {
        if (tipo == 2 && contacto != null) {
            idContacto = contacto.getId();
            agregarContactoBinding.txtNombre.setText(contacto.getNombre());
            agregarContactoBinding.txtApPaterno.setText(contacto.getApellidoPaterno());
            agregarContactoBinding.txtApMaterno.setText(contacto.getApellidoMaterno());
            agregarContactoBinding.txtTelefono.setText(contacto.getTelefono());

            agregarContactoBinding.toolbarTitle.setText("Editar Contacto");
            agregarContactoBinding.btnAgregar.setText("Aceptar");
        }

        agregarContactoBinding.btnAgregar.setOnClickListener(v -> {
            String nombre = agregarContactoBinding.txtNombre.getText().toString();
            String apPaterno = agregarContactoBinding.txtApPaterno.getText().toString();
            String apMaterno = agregarContactoBinding.txtApMaterno.getText().toString();
            String telefono = agregarContactoBinding.txtTelefono.getText().toString();

            if (!nombre.equals("") && !apPaterno.equals("") && !apMaterno.equals("") && !telefono.equals("")) {
                dismiss();
                eventoEdicionContacto.agregarEditarContacto(tipo, new Contactos(idContacto, nombre, apPaterno, apMaterno, telefono, ""));
            } else {
                Toast.makeText(getContext(), "Falta datos por llenar", Toast.LENGTH_LONG).show();
            }
        });
        agregarContactoBinding.btnCancelar.setOnClickListener(v -> {
            dismiss();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        agregarContactoBinding = null;
    }
}