package cl.inacaptemuco.mobile2022;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import modelo.Entrada;

public class EntradaAdapter extends RecyclerView.Adapter<EntradaAdapter.ViewHolder> {
    private ArrayList<Entrada> entradaArrayList;
    private Context context;

    public EntradaAdapter(ArrayList<Entrada> entradaArrayList, Context context) {
        this.entradaArrayList = entradaArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public EntradaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.entrada_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EntradaAdapter.ViewHolder holder, int position) {
        //Objetivo individual obtenido desde la lista de objetos
        Entrada entrada = entradaArrayList.get(position);
        //Se extraen propiedades y se asigna a TextView
        holder.txv_patente.setText(entrada.getPatente());
        holder.txv_fecha.setText(entrada.getFecha().toString());
        holder.txv_estado.setText(entrada.getEstado());
        holder.txv_comentario.setText(entrada.getComentario());

    }

    @Override
    public int getItemCount() {
        return entradaArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Variables de Objeto
        private final TextView txv_patente,txv_fecha,txv_estado,txv_comentario;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Cada variable de objeto se asocia a elementos de la interfaz archivos de layout
            txv_patente = itemView.findViewById(R.id.txv_patente);
            txv_fecha = itemView.findViewById(R.id.txv_fecha);
            txv_estado = itemView.findViewById(R.id.txv_comentario);
            txv_comentario = itemView.findViewById(R.id.txv_comentario);


            //Listener para actualizar

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    Entrada entrada = entradaArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, ActualizarActivity.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("entrada", entrada);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);
                }
            });

        }


    }
}
