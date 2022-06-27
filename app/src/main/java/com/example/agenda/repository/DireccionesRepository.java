package com.example.agenda.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.agenda.db.DbHelper;
import com.example.agenda.model.Contactos;
import com.example.agenda.model.Direcciones;

public class DireccionesRepository extends DbHelper {
    Context context;

    public DireccionesRepository(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Direcciones registrarDireccion(int id_contacto,String estado, String municipio, String localidad, String colonia, String calle, String numeroInterior) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_contacto", id_contacto);
        values.put("estado", estado);
        values.put("municipio", municipio);
        values.put("localidad", localidad);
        values.put("colonia", colonia);
        values.put("calle", calle);
        values.put("numero_interior", numeroInterior);
        Long id = db.insert(TABLE_DIRECCIONES, null, values);
        db.close();
        return new Direcciones(Math.toIntExact(id),id_contacto, estado, municipio, localidad, colonia, calle, numeroInterior);
    }
    public Direcciones actualizarDireccion(Direcciones direccion,int idContacto) {
        ContentValues values = new ContentValues();
        values.put("estado", direccion.getEstado());
        values.put("municipio", direccion.getMunicipio());
        values.put("localidad", direccion.getLocalidad());
        values.put("colonia",direccion.getColonia());
        values.put("calle", direccion.getCalle());
        values.put("numero_interior", direccion.getNumeroInterior());
        actualizarDireccionPorId(idContacto + "", values);
        return direccion;
    }
    private boolean actualizarDireccionPorId(String idDireccion, ContentValues values) {
        int update = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            update = db.update(TABLE_DIRECCIONES, values, "id_contacto = ?", new String[]{idDireccion});
            db.close();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return update == 1;
    }
    public Direcciones obtenerDireccion(int idContacto){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorDireccion = null;
        Direcciones direccion = null;
        cursorDireccion = db.rawQuery("SELECT * FROM " + TABLE_DIRECCIONES + " WHERE id_contacto = " + idContacto, null);
        if (cursorDireccion.moveToFirst()) {
            direccion = (new Direcciones(cursorDireccion.getInt(0),cursorDireccion.getInt(1),cursorDireccion.getString(2),cursorDireccion.getString(3),cursorDireccion.getString(4),cursorDireccion.getString(5),cursorDireccion.getString(6),cursorDireccion.getString(7)));
        }
        cursorDireccion.close();
        db.close();
        return direccion;
    }
}
