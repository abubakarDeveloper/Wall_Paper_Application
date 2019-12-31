package ab_developer.com.wallpaperfreedownload;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>{

    ArrayList<WallpaperItem> dataset;
    AdapterView.OnItemClickListener onItemClickListener;

    public WallpaperAdapter(ArrayList<WallpaperItem> dataset, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WallpaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.wallpaper_item_layout, parent, false);

        return new WallpaperHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final WallpaperHolder holder, final int position) {

        Picasso.get().load(dataset.get(position).webformatURL).into(holder.ivPreview);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(null, holder.itemView, position, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class WallpaperHolder extends RecyclerView.ViewHolder{

        ImageView ivPreview;
        public WallpaperHolder(View itemView) {
            super(itemView);
            ivPreview = itemView.findViewById(R.id.iv_preview);
        }
    }
}
