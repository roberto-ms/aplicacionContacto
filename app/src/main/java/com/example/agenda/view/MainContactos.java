package com.example.agenda.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.agenda.adaptadores.ListaContactosAdapter;
import com.example.agenda.databinding.ActivityMainContactosBinding;
import com.example.agenda.model.Contactos;
import com.example.agenda.repository.ContactosRepository;
import com.example.agenda.viewmodel.ContactosViewModel;
import com.example.agenda.viewmodel.ViewModelFactory;

import java.util.List;

public class MainContactos extends AppCompatActivity implements ContactosInterface {

    private ActivityMainContactosBinding binding;
    private ContactosViewModel mainContactosViewModel;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int index = 0;
    private Contactos currentContacto = null;
    private ListaContactosAdapter adapter;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContactosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ContactosRepository mainContactosRepository = new ContactosRepository(this);
        mainContactosViewModel = new ViewModelProvider(this, new ViewModelFactory(mainContactosRepository)).get(ContactosViewModel.class);

        mainContactosViewModel.obtenerContactos().observe(this, this::mostrarContactos);
        mainContactosViewModel.obtenerAgregarEditarContacto().observe(this, this::updateItem);
        mainContactosViewModel.gobtenerDeleteContacto().observe(this, this::deleteContacto);
        mainContactosViewModel.cargarContactos(0);

        binding.btnAgregar.setOnClickListener(v -> {
            AgregarContacto formularioDialogo = new AgregarContacto(this, 1, null);
            formularioDialogo.setCancelable(false);
            formularioDialogo.show(getSupportFragmentManager(), "formulario");
        });

        binding.switchOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainContactosViewModel.cargarContactos(isChecked ? 1 : 0);
            }
        });

    }

    private void deleteContacto(boolean deleted) {
        if (deleted) {
            adapter.deleteItem(index);
        } else {
            Toast.makeText(this, "No se pudo eliminar el contacto", Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarContactos(List<Contactos> contactosList) {
        binding.listaContactos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListaContactosAdapter(contactosList, this);
        binding.listaContactos.setAdapter(adapter);
        Log.d("Lista", "Contactos: " + contactosList.size());
    }

    private void updateItem(Pair<Integer, Contactos> contactosPair) {
        if (contactosPair.second != null) {
            if (contactosPair.first == 1) {
                adapter.addItem(contactosPair.second);
            } else {
                adapter.updateItem(index, contactosPair.second);
            }
        }
    }

    @Override
    public void agregarEditarContacto(int tipo, Contactos contacto) {
        mainContactosViewModel.registrarActualizarContacto(tipo, contacto);
    }

    @Override
    public void actualizarContacto(int index, Contactos contacto) {
        this.index = index;
        AgregarContacto formularioDialogo = new AgregarContacto(this, 2, contacto);
        formularioDialogo.setCancelable(false);
        formularioDialogo.show(getSupportFragmentManager(), "formulario");
    }

    @Override
    public void actualizarFoto(int index, Contactos contacto) {
        this.index = index;
        this.currentContacto = contacto;
        dispatchTakePictureIntent();
    }

    @Override
    public void eliminarFoto(int index, int idContacto) {
        this.index = index;
        mainContactosViewModel.eliminarContacto(idContacto);
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Se necesita acceso a la camara", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (currentContacto != null) {
                mainContactosViewModel.actualizarFoto(currentContacto.getId(), imageBitmap);
            } else {
                Toast.makeText(this, "Ocurrió un error al agregar la foto", Toast.LENGTH_LONG).show();
            }
        }
    }


}