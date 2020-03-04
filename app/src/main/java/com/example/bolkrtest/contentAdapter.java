package com.example.bolkrtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class contentAdapter extends RecyclerView.Adapter<contentAdapter.recyclerHolder> {


    List<contentModel> modelList;
    Context context;

    public contentAdapter(List<contentModel> modelList, Context context)
    {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public contentAdapter.recyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_layout, parent, false);
        return new contentAdapter.recyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contentAdapter.recyclerHolder holder, int position)
    {
        final contentModel myList = modelList.get(position);
        if (myList.getName()!=null)
        {
            holder.textView.setText(myList.getName());
        }
        contentModel cm = new contentModel();
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class recyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public recyclerHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageV);
            textView= itemView.findViewById(R.id.nameV);

        }
    }
}
