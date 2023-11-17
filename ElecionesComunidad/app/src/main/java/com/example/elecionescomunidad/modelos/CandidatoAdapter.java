package com.example.elecionescomunidad.modelos;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.elecionescomunidad.R;

import java.util.ArrayList;

public class CandidatoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Candidato> canidatos;
    private int layout;

    public CandidatoAdapter(Context context, ArrayList<Candidato> canidatos) {
        this.context = context;
        this.canidatos = canidatos;
        this.layout = R.layout.item_candidato;
    }


    @Override
    public int getCount() {
        return canidatos.size();
    }

    @Override
    public Object getItem(int position) {
        return canidatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return canidatos.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(this.layout, null);
        }
        Candidato candidato = (Candidato) getItem(position);
        TextView nombreCandidato = (TextView) v.findViewById(R.id.nombreCandidato);
        TextView nombrePartido = (TextView) v.findViewById(R.id.nombrePartido);
        ImageView fotoCandidato = (ImageView) v.findViewById(R.id.imagenRepresentante);
        nombreCandidato.setText(candidato.getNombre());
        nombrePartido.setText(candidato.getPartido().getNombre());
        nombrePartido.setTextColor(candidato.getPartido().getColor());
        String nombrefoto = candidato.getNombre().split(" ")[0];
        Resources recursos = context.getResources();
        String nombreArchivo = (candidato.getNombre().toLowerCase().split(" ")[0]).toLowerCase();
        int resourceId = recursos.getIdentifier(nombreArchivo, "drawable", context.getPackageName());
        fotoCandidato.setImageResource(resourceId);
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
