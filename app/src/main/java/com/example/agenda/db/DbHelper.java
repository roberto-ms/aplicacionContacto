package com.example.agenda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NOMBRE = "contactos.db";
    public static final String TABLE_CONTACTOS = "t_contactos";
    public static final String TABLE_DIRECCIONES = "t_direcciones";
    public static final String ID_CONTACTO = "id_contacto";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido_paterno TEXT NOT NULL," +
                "apellido_materno TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "foto TEXT," +
                "posicion INTEGER )");

        db.execSQL("CREATE TABLE " + TABLE_DIRECCIONES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_contacto INTEGER NOT NULL," +
                "estado VARCHAR NOT NULL," +
                "municipio VARCHAR NOT NULL," +
                "localidad VARCHAR NOT NULL," +
                "colonia VARCHAR NOT NULL," +
                "calle VARCHAR NOT NULL," +
                "numero_interior VARCHAR NOT NULL," +
                "FOREIGN_KEY" + (ID_CONTACTO) + "REFERENCES" + TABLE_CONTACTOS + ("id") + ")");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE " + TABLE_CONTACTOS);
        //db.execSQL("DROP TABLE " + TABLE_DIRECCIONES);
        onCreate(db);
    }
}
