package com.example.newsapps;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private  Context context;
    Callback callback;
    private List<ModelNews> model;
    interface Callback{
        void Call(int position);
    }
    public MainAdapter(Context context, List<ModelNews> model, Callback callback){
        this.context = context;
        this.callback = callback;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(model.get(position).getDate());
        holder.title.setText(model.get(position).getJudul());
        Picasso.get().load(model.get(position).getImage())
                .fit()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         private  RelativeLayout relativeLayout;
         private TextView title, date;
         private  ImageView image;
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.im_gambar_item);
            title = itemView.findViewById(R.id.tv_judul_item);
            date = itemView.findViewById(R.id.tv_tanggal_item);
            relativeLayout = itemView.findViewById(R.id.rv_click);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      callback.Call(getAdapterPosition());
                }
            });
        }
    }
}
