package com.example.agenda.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.agenda.db.DbHelper;
import com.example.agenda.model.Contactos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactosRepository extends DbHelper {
    Context context;

    public ContactosRepository(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public List<Contactos> listaContactos(int orden) {
        ArrayList<Contactos> contactosList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorContactos = null;
        String ordenQuery = "";
        if (orden == 1){
            ordenQuery = " ORDER BY nombre ASC";
        }
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + ordenQuery, null);
        if (cursorContactos.moveToFirst()) {
            do {
                contactosList.add(new Contactos(cursorContactos.getInt(0), cursorContactos.getString(1), cursorContactos.getString(2), cursorContactos.getString(3), cursorContactos.getString(4), cursorContactos.getString(5)));
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();
        db.close();
        return contactosList;
    }


    public Contactos actualizarContacto(Contactos contacto) {
        ContentValues values = new ContentValues();
        values.put("nombre", contacto.getNombre());
        values.put("apellido_paterno", contacto.getApellidoPaterno());
        values.put("apellido_materno", contacto.getApellidoMaterno());
        values.put("telefono", contacto.getTelefono());
        actualizarContactoPorId(contacto.getId() + "", values);
        return contacto;
    }

    public Contactos registrarContacto(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido_paterno", apellidoPaterno);
        values.put("apellido_materno", apellidoMaterno);
        values.put("telefono", telefono);
        values.put("foto", "");
        values.put("posicion", 0);
        Long id = db.insert(TABLE_CONTACTOS, null, values);
        db.close();
        return new Contactos(Math.toIntExact(id), nombre, apellidoPaterno, apellidoMaterno, telefono, "");
    }


    private boolean actualizarContactoPorId(String idContacto, ContentValues values) {
        int update = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            update = db.update(TABLE_CONTACTOS, values, "id = ?", new String[]{idContacto});
            db.close();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return update == 1;
    }

    public Contactos guardarFoto(int idContacto, Bitmap foto) {
        if (isExternalStorageWritable()) {
            String path = "";
            try {
                File destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                String root = destination.getPath();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fname = "contacto_" + timeStamp + ".jpg";

                File file = new File(myDir, fname);
                if (file.exists())
                    file.delete();
                FileOutputStream out = new FileOutputStream(file);
                foto.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                path = file.getPath();
                ContentValues values = new ContentValues();
                values.put("foto", path);
                if (actualizarContactoPorId(idContacto + "", values))
                    return obtenerContactoPorId(idContacto);
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
            return null;
        } else {
            return null;
        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean eliminarContacto(int idContacto) {
        SQLiteDatabase db = getWritableDatabase();
        int deleted = db.delete(TABLE_CONTACTOS, "id=?", new String[]{(idContacto + "")});
        db.close();
        return deleted == 1;
    }

    public Contactos obtenerContactoPorId(int idContacto) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorContactos = null;
        Contactos contacto = null;
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + idContacto, null);
        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos(cursorContactos.getInt(0), cursorContactos.getString(1), cursorContactos.getString(2), cursorContactos.getString(3), cursorContactos.getString(4), cursorContactos.getString(5));
        }
        cursorContactos.close();
        db.close();
        return contacto;
    }

}
