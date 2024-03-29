package br.com.movapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.movapp.R;
import br.com.movapp.model.ViewExercicio;

public class ExerciciosViewAdapter extends RecyclerView.Adapter<ExerciciosViewAdapter.ViewHolder> {

    ArrayList<ViewExercicio> mValues;
    Context mContext;
    protected ItemListener mListener;

    public ExerciciosViewAdapter(Context context, ArrayList<ViewExercicio> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        ViewExercicio item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        }

        public void setData(ViewExercicio item) {
            this.item = item;
            textView.setText(item.text);
            if(item.drawable != null){
                imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(item.drawable, 0, item.drawable.length), 300, 200, true));
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public ExerciciosViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_exercicios_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(ViewExercicio item);
    }
}