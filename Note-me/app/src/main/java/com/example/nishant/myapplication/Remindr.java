package com.example.nishant.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Remindr extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Keep> keepArrayList = new ArrayList<>();
    RecyclerView rv;
    TextView emptytxt;
    LinearLayoutManager linearmanager = new LinearLayoutManager(this);
    GridLayoutManager gridmanager = new GridLayoutManager(this, 2);
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remindr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar bottombar = (Toolbar) findViewById(R.id.bottom_bar);
        bottombar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Remindr.this, Add_activity.class);
                startActivity(i);

            }
        });

        rv = (RecyclerView) findViewById(R.id.rview);
        rv.setLayoutManager(linearmanager);
        emptytxt = (TextView) findViewById(R.id.emptyyText);
        retrive();
        adapter = new Adapter(this, keepArrayList);
        if (keepArrayList.isEmpty()) {
            emptytxt.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notelist) {
            Intent i = new Intent(this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (id == R.id.reminders) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void retrive() {
        keepArrayList.clear();
        DBAdaper dba = new DBAdaper(this);
        dba.open();
        Cursor c = dba.getALLinfo();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String title = c.getString(1);
            String note = c.getString(2);
            byte[] blob = c.getBlob(3);
            String remdetail = c.getString(4);
            if (!(remdetail.contentEquals(""))) {
                Keep keep = new Keep(id, title, note, Utility.getPhoto(blob), remdetail);
                keepArrayList.add(keep);
            }
        }
        if (!(keepArrayList.size() < 1)) {
            rv.setAdapter(new Adapter(this,keepArrayList));
        }
        dba.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gridtolinear:
                changelayout();

        }
        return super.onOptionsItemSelected(item);
    }

    private void changelayout() {
        if (rv.getLayoutManager() == linearmanager) {
            rv.setLayoutManager(gridmanager);

        } else {
            rv.setLayoutManager(linearmanager);
        }
    }
}
