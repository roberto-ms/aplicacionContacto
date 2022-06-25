package com.example.agenda.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.model.Contactos;
import com.example.agenda.view.AgregarContacto;
import com.example.agenda.view.ContactosInterface;
import com.example.agenda.view.DetallesContacto;

import java.io.File;
import java.util.List;


public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    private List<Contactos> contactosList;
    private ContactosInterface eventoEdicionContacto;

    public ListaContactosAdapter(List<Contactos> contactosList, ContactosInterface eventoEdicionContacto) {
        this.contactosList = contactosList;
        this.eventoEdicionContacto = eventoEdicionContacto;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.render(position, contactosList.get(position));
    }

    /*public void setContactosList(List<Contactos> contactosList) {
        this.contactosList = contactosList;
        notifyDataSetChanged();
    }*/

    public void updateItem(int index, Contactos contacto) {
        contactosList.set(index, contacto);
        notifyDataSetChanged();
    }

    public void addItem(Contactos contacto) {
        contactosList.add(contacto);
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        contactosList.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactosList.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        private final TextView viewNombre;
        private final TextView viewTelefono;
        private final ImageView fotoImageView;
        private final ImageView menuIcon;

        public ContactoViewHolder(View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            fotoImageView = itemView.findViewById(R.id.fotoImageView);
            menuIcon = itemView.findViewById(R.id.menuIcon);
        }

        public void render(int index, Contactos contacto) {
            if (contacto.getNombre() != null && contacto.getApellidoPaterno() != null && contacto.getApellidoMaterno() != null) {
                viewNombre.setText(contacto.getNombre() + " " + contacto.getApellidoPaterno() + " " + contacto.getApellidoMaterno());
            }
            viewTelefono.setText(contacto.getTelefono());

            if (!contacto.getFoto().equals("")) {
                File imgFile = new File(contacto.getFoto());
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    fotoImageView.setImageBitmap(myBitmap);
                }
            }

            menuIcon.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), menuIcon);
                popupMenu.inflate(R.menu.opciones_contacto);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.editar:
                                eventoEdicionContacto.actualizarContacto(index, contacto);
                                break;
                            case R.id.editar_foto:
                                eventoEdicionContacto.actualizarFoto(index, contacto);
                                break;
                            case R.id.eliminar:
                                eventoEdicionContacto.eliminarFoto(index, contacto.getId());
                                break;
                            case R.id.detalles:
                                eventoEdicionContacto.detalles(contacto);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            });

        }
    }
}
