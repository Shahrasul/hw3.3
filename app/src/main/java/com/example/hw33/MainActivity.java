package com.example.hw33;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

    public class MainActivity extends AppCompatActivity {

        private NavController navController;
        private ActionBar toolBar;
        private BottomNavigationView bottomNavigationView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            setUpNavigation();

        }

        public void setUpNavigation(){
            bottomNavigationView =findViewById(R.id.nav_view);
            NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        }

    }