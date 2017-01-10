package com.example.nishant.myapplication;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by nishant on 1/8/2017.
 */
public class SwipeHelper extends ItemTouchHelper.Callback {

    Adapter adapter;

    public SwipeHelper(Adapter adapter) {
        this.adapter=adapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deletekeepnote(viewHolder.getAdapterPosition());
    }
}
