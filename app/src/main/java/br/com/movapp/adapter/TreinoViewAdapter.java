package br.com.movapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import br.com.movapp.R;
import br.com.movapp.activity.ExecutarExercicio;
import br.com.movapp.model.Categoria;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.utils.ImageUtils;


public class TreinoViewAdapter extends RecyclerView.Adapter<TreinoViewAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<TipoExercicio> exercicio;
    private String dia;

    public TreinoViewAdapter(Context context, Activity activity, List exercicio, String dia) {
        this.activity = activity;
        this.context = context;
        this.exercicio = exercicio;
        this.dia = dia;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.treino_dia_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(exercicio.get(position));

        TipoExercicio pu = exercicio.get(position);

        holder.exerNome.setText(pu.getNome());
        //holder.exerTipo.setText(converSubCategoriaToString(pu.getCategorias()));
        holder.exeImg.setImageBitmap(ImageUtils.getBitmapFromBytes(pu.getImage()));

    }

    @Override
    public int getItemCount() {
        return exercicio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView exerNome;
        public TextView exerTipo;
        public ImageView exeImg;

        public ViewHolder(View itemView) {
            super(itemView);

            exerNome = (TextView) itemView.findViewById(R.id.pNametxt);
            exerTipo = (TextView) itemView.findViewById(R.id.pJobProfiletxt);
            exeImg = (ImageView ) itemView.findViewById(R.id.userImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TipoExercicio tipoExercicio = (TipoExercicio) view.getTag();

                    Intent executarExercicio = new Intent(activity, ExecutarExercicio.class);
                    executarExercicio.putExtra("CODEXERCICIO", tipoExercicio.getCod());
                    executarExercicio.putExtra("DIATREINO", dia);
                    activity.startActivity(executarExercicio);

                }
            });

        }
    }

    public String converSubCategoriaToString(Set<Categoria> categorias){
        if (categorias.size() > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (Categoria categoria : categorias) {
                for(Categoria subCategoria :categoria.getSubCategorias()){
                    nameBuilder.append("'").append(subCategoria.getNome().replace("'", "\\'")).append("',");

                }
            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            return nameBuilder.toString();
        } else {
            return "";
        }

    }
}