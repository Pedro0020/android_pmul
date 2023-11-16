package com.example.elecionescomunidad.modelos;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class EleccionesAdapter extends ArrayAdapter<Candidato> {

    public EleccionesAdapter(@NonNull Context context, int resource, @NonNull List<Candidato> objects) {
        super(context, resource, objects);
    }
}
