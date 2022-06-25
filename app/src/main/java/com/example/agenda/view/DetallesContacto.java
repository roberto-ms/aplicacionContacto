package com.example.agenda.view;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.agenda.databinding.FragmentDetallesContactoBinding;
import com.example.agenda.model.Contactos;

import java.io.File;

public class DetallesContacto extends DialogFragment {
    private Contactos contacto;
    private FragmentDetallesContactoBinding detallesContactoBinding;

    public DetallesContacto(Contactos contacto) {
        this.contacto = contacto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detallesContactoBinding = FragmentDetallesContactoBinding.inflate(LayoutInflater.from(getContext()));
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(detallesContactoBinding.getRoot())
                .create();
        render();
        return dialog;
    }

    private void render() {

        //idContacto = contacto.getId();
        detallesContactoBinding.txtNombreDetalle.setText("pedro");
        detallesContactoBinding.txtApPaternoDetalle.setText(contacto.getApellidoPaterno());
        detallesContactoBinding.txtApMaternoDetalle.setText(contacto.getApellidoMaterno());
        detallesContactoBinding.txtTelefonoDetalle.setText(contacto.getTelefono());
        if (!contacto.getFoto().equals("")) {
            File imgFile = new File(contacto.getFoto());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                detallesContactoBinding.imageView.setImageBitmap(myBitmap);
            }
        }
        detallesContactoBinding.btnCerrar.setOnClickListener(v -> {
            dismiss();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detallesContactoBinding = null;
    }
}
