package com.example.bolkrtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class contentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<contentModel> modelList;
    Context context;

    public contentAdapter(List<contentModel> modelList, Context context)
    {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1:
                View view1 = inflater.inflate(R.layout.img_layout, parent, false);
                return new recyclerHolder1(view1);
            default:
                View view = inflater.inflate(R.layout.content_layout, parent, false);
                return new recyclerHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String imgLink = "https://i.ibb.co/5rLDjPH/sample-img.png";
        switch (getItemViewType(position))
        {
            case 0:
                final contentModel myList = modelList.get(position);
                if (myList.getName()!=null)
                {
                    ((recyclerHolder) holder).textView.setText(myList.getName());
                }
                Picasso.get().load(myList.getLink()).into(((recyclerHolder) holder).imageView);
                break;
            case 1:
                Picasso.get().load(imgLink).into(((recyclerHolder1) holder).imageView);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int m= modelList.size();
        if (position==m/2)
            return 1;
        else return 0;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+ modelList.size());
        return modelList.size();
    }

    public class recyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public recyclerHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageV);
            imageView= itemView.findViewById(R.id.imageV1);
            textView= itemView.findViewById(R.id.nameV);

        }
    }
    public class recyclerHolder1 extends RecyclerView.ViewHolder {
        ImageView imageView;
        public recyclerHolder1(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageV);

        }
    }
}
