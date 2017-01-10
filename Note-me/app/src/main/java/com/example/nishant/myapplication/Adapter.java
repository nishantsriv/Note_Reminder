package com.example.nishant.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nishant on 1/1/2017.
 */

public class Adapter extends RecyclerView.Adapter<Holder> {
    Context context;
    ArrayList<Keep> keepArrayList = new ArrayList<>();

    public Adapter(Context context, ArrayList<Keep> keepArrayList) {

        this.context = context;
        this.keepArrayList = keepArrayList;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        Holder holder = new Holder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.img.setImageBitmap(keepArrayList.get(position).getPicture());
        holder.title.setText(keepArrayList.get(position).getTitle());
        holder.note.setText(keepArrayList.get(position).getNote());
        holder.remdetail.setText(keepArrayList.get(position).getRemdet());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(context, Update.class);
                intent.putExtra("ID", keepArrayList.get(position).getId());
                intent.putExtra("TITLE", keepArrayList.get(position).getTitle());
                intent.putExtra("NOTE", keepArrayList.get(position).getNote());
                intent.putExtra("PICTURE", Utility.getBytes(keepArrayList.get(position).getPicture()));
                intent.putExtra("REMDETAIL", keepArrayList.get(position).getRemdet());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return keepArrayList.size();
    }


    public void deletekeepnote(int adapterPosition) {
        Keep keep = keepArrayList.get(adapterPosition);
        int id = keep.getId();
        DBAdaper dba = new DBAdaper(context);
        dba.open();
        if (dba.Delete(id)) {
            keepArrayList.remove(adapterPosition);
        } else {
            Toast.makeText(context, "Unable To Delete", Toast.LENGTH_SHORT).show();

        }
        dba.close();
        notifyItemRemoved(adapterPosition);
    }
}
