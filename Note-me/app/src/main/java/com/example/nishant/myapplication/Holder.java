package com.example.nishant.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nishant on 1/1/2017.
 */

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView img;
    TextView title, note;
    TextView remdetail;
    ItemClickListener itemClickListener;

    public Holder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.imgmodel);
        title = (TextView) itemView.findViewById(R.id.titlemodel);
        note = (TextView) itemView.findViewById(R.id.notemodel);
        remdetail = (TextView) itemView.findViewById(R.id.remdetail);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
