package com.example.piggybankapp.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class CommonAdapter extends RecyclerView.Adapter {

    private int size;
    private int layout;

    public CommonAdapter(int layout, int size){
        this.layout = layout;
        this.size = size;
    }

    public static class CommonHolder extends RecyclerView.ViewHolder {
        private View view;
        public CommonHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout, parent, false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder item, int position) {
        //item.setIsRecyclable(false); //CHA: con esto el reciclador no es reciclable, las vistas no se reemplazarán entre sí(edittext)
        bindView(item, position);
    }

    public abstract void bindView(RecyclerView.ViewHolder item, int position);

    @Override
    public int getItemCount() {
        return this.size;
    }

    //CHA: con estos metodos: getItemViewType, getItemId, ya no repite el contenido en un Edittext
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
