package com.example.bottom_menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    List<QrModel> list;
    Context context;

    public ListAdapter(List<QrModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dateTime.setText(list.get(position).getDateTime());
        holder.title.setText(list.get(position).getTitle());
        switch (list.get(position).getType()) {
            case 1:
                holder.imgLogo.setImageResource(R.drawable.ic_contacts2);
                break;
            case 2:
                holder.imgLogo.setImageResource(R.drawable.ic_email);
                break;
            case 3:
                holder.imgLogo.setImageResource(R.drawable.ic_message1);
                break;
            case 4:
                holder.imgLogo.setImageResource(R.drawable.ic_link);
                break;
            case 5:
                holder.imgLogo.setImageResource(R.drawable.ic_title);
                break;
            case 6:
                holder.imgLogo.setImageResource(R.drawable.ic_call_purple);
                break;
            case 7:
                holder.imgLogo.setImageResource(R.drawable.ic_wifi);
            default:
                holder.imgLogo.setImageResource(R.drawable.ic_unknown);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView title;
        TextView dateTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imageViewLogo);
            title = itemView.findViewById(R.id.textViewTitle);
            dateTime = itemView.findViewById(R.id.textViewTime);

        }
    }
}
