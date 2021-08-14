package com.franciscain.lautrerive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

import com.franciscain.lautrerive.ui.main.FragmentEnseignement;
import com.franciscain.lautrerive.ui.main.FragmentFAQ;
import com.franciscain.lautrerive.ui.main.FragmentTableauDeBord;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentSélectionné = null;

                switch (item.getItemId()){
                    case R.id.tableau_de_bord:
                        fragmentSélectionné = new FragmentTableauDeBord();
                        break;
                    case R.id.enseignement:
                        fragmentSélectionné = new FragmentEnseignement();
                        break;
                    case R.id.faq:
                        fragmentSélectionné = new FragmentFAQ();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragmentSélectionné).commit();
                return true;
            }
        });

        // TODO : mettre une action sur le bouton fab
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}