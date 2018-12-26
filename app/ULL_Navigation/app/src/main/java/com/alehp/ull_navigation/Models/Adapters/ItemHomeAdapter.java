package com.alehp.ull_navigation.Models.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alehp.ull_navigation.Models.ItemHome;
import com.alehp.ull_navigation.R;

import java.util.List;

public class ItemHomeAdapter extends RecyclerView.Adapter<ItemHomeAdapter.ViewHolder> {

    private List<ItemHome> items;
    private int layout;
    private OnItemClickListener itemClickListener;

    public ItemHomeAdapter(List<ItemHome> items, int layout, OnItemClickListener listener){
        this.items = items;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public ImageView itemImage;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.itemName= itemView.findViewById(R.id.textView_home_item);
            this.itemImage = itemView.findViewById(R.id.imageView_home_item);
        }

        public void bind(final ItemHome itemHome, final OnItemClickListener listener){

            this.itemName.setText(itemHome.getName());
//            int imageID = context.getResources().getIdentifier(itemHome.getImage(), null, context.getPackageName());
//            this.itemImage.setImageDrawable(context.getResources().getDrawable(imageID));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemHome, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(ItemHome name, int position);
    }

}
