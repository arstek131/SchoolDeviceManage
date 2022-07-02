package com.example.arslan.qrcode;


import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CespiteAdapter extends RecyclerView.Adapter<CespiteAdapter.ViewHolder>
{

    private List<CespiteOgg> cespiteOggList;
    private Context context;

    public CespiteAdapter(List<CespiteOgg> cespiteOggList, Context context) {

        this.cespiteOggList = cespiteOggList;
        this.context = context;
    }

    public void clearCerpiteList(){
        if(cespiteOggList != null){
            cespiteOggList.clear();
        }
    }

    public boolean isEmpty(){
        Log.d("size", cespiteOggList.size()+"C");
        return cespiteOggList.isEmpty();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public CardView cv;
        public TextView txtNumInventario;
        public TextView txtNomeCespite;
        public TextView txtDtCatalogazione;
        public TextView txtAula;
        public TextView txtNomeUser;


        ViewHolder(View itemView)
        {

            super (itemView);
            //cv = (CardView) itemView.findViewById(R.id.cardView);
            txtNumInventario = (TextView) itemView.findViewById(R.id.txtNumeroInventario);
            txtNomeCespite = (TextView) itemView.findViewById(R.id.txtNomeCespite);
            txtDtCatalogazione = (TextView) itemView.findViewById(R.id.txtDataCatalogazione);
            txtAula = (TextView) itemView.findViewById(R.id.txtAula);
            txtNomeUser= (TextView) itemView.findViewById(R.id.txtNomeUser);


        }
    }



    @Override
    public CespiteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View cespiteView = inflater.inflate(R.layout.cespite_card_view, parent, false);

        return new ViewHolder(cespiteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        CespiteOgg cespiteOgg = cespiteOggList.get(position);

        holder.txtNumInventario.setText(cespiteOgg.getNumInventario());
        holder.txtNomeCespite.setText(cespiteOgg.getNomeCespite());
        holder.txtDtCatalogazione.setText(cespiteOgg.getDtCatalogazione());
        holder.txtAula.setText(cespiteOgg.getAula());
        holder.txtNomeUser.setText(cespiteOgg.getNomeUser());

    }

    @Override
    public int getItemCount()
    {

        return cespiteOggList.size();
    }

}
