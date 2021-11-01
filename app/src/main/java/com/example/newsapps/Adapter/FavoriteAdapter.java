package com.example.newsapps.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapps.Model.ModelNews;
import com.example.newsapps.R;
import com.example.newsapps.Ui.RealmHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    public Callback callback;
    Realm realm;
    private List<ModelNews> model;
    public interface Callback {
        void onClick(int position);
    }
    public FavoriteAdapter( Context context, List<ModelNews> model, Callback callback) {
        this.callback = callback;
        this.context = context;
        this.model = model;
    }
    RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.newsitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.date.setText(model.get(position).getDate());
        holder.title.setText(model.get(position).getJudul());
        Picasso.get().load(model.get(position).getImage())
                .fit()
                .into(holder.image);
        holder.posku = model.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return (model != null) ? model.size() : 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout relativeLayout;
        private TextView title, date;
        private  ImageView image;
        Integer posku;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this::onCreateContextMenu);
            image = itemView.findViewById(R.id.im_gambar_item);
            title = itemView.findViewById(R.id.tv_judul_item);
            date = itemView.findViewById(R.id.tv_tanggal_item);
            relativeLayout = itemView.findViewById(R.id.rv_click);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getAdapterPosition());
                }
            });
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1, 1, "Hapus");
            Delete.setOnMenuItemClickListener(this::onMenuItemClick);
        }
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case 1:
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("Apakah kamu mau menghapus item ini?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    realm = Realm.getInstance(configuration);
                                    RealmHelper realmHelper = new RealmHelper(realm);
                                    realmHelper.delete(posku);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Hapus data")
                            .setIcon(R.drawable.nea_logo_gradient);
                    AlertDialog alert = builder.create();
                    alert.show();//showing the dialog

                    break;
            }
            return true;
        }
    }

}
