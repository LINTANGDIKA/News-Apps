package com.example.newsapps.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapps.Model.ModelNews;
import com.example.newsapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {
    private  Context context;
    public Callback callback;
    private List<ModelNews> model ;
    private List<ModelNews> modelfull;
    public interface Callback{
        void Call(int position);
    }
    public MainAdapter(Context context, List<ModelNews> model, Callback callback){
        this.context = context;
        this.callback = callback;
        this.model = model;
        modelfull = new ArrayList<>(model);
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
        return (model != null) ? model.size() : 0;
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
    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelNews> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(modelfull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ModelNews item : modelfull) {
                    if (item.getJudul().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            model.clear();
            model.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
