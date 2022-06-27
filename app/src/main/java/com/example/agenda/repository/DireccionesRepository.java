package com.example.agenda.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}
