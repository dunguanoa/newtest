package com.example.dell.banhang.ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dell.banhang.R;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    Button btnMenu,btnWork, btnData,btnDetail;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerToggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager  = getSupportFragmentManager();
        /*
        Anhxa();
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMenu = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(openMenu);
            }
        });
        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openWork = new Intent(MainActivity.this, WorkActivity.class);
                startActivity(openWork);
            }
        });
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openData = new Intent(MainActivity.this, DataActivity.class);
                startActivity(openData);
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openDetail = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(openDetail);
            }
        });*/


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        switch(id){
            case R.id.menu1:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent a = new Intent(this, MenuActivity.class);
                startActivity(a);
                ;break;
            case R.id.work:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent b = new Intent(this, WorkActivity.class);
                startActivity(b);
                ;break;
            case R.id.detail:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent c = new Intent(this, DetailActivity.class);
                startActivity(c);
                ;break;
            case R.id.estimate:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent d = new Intent(this, DataActivity.class);
                startActivity(d);
                ;break;
            case R.id.exit:
                finish();
                ;break;

        }

        return true;
    }



}

