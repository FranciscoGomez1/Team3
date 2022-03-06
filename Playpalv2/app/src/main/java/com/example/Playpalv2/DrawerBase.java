package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import java.security.acl.Owner;

public class DrawerBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private ViewGroup parent;

    @Override
    public void setContentView(View view) {

        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, parent,false);
        FrameLayout container = drawerLayout.findViewById(R.id.activity_container);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        int itemId = item.getItemId();
        if (itemId == R.id.nav_dog_profile) {
            startActivity(new Intent(this, DogProfile.class));
            overridePendingTransition(0, 0);


            startActivity(new Intent(this, DogOwnerProfile.class));
            overridePendingTransition(0, 0);
        } else if (itemId == R.id.nav_owner_profile) {
            startActivity(new Intent(this, DogOwnerProfile.class));
            overridePendingTransition(0, 0);
        }
        return false;

    }



    protected void allocateActivityTitle(String titleSring){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleSring);
        }
    }
}