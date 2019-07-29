package br.com.movapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.movapp.R;
import br.com.movapp.activity.fragments.ContaFragment;
import br.com.movapp.activity.fragments.ExerciciosFragment;
import br.com.movapp.activity.fragments.HomeFragment;
import br.com.movapp.activity.fragments.TreinoFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigation;
    private int dataFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        if(!retornoAlterarFragment()){
            loadFragment(new HomeFragment());
        }
    }

    private boolean retornoAlterarFragment() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            dataFiltro = extras.getInt("FRAGMENT");
            if(dataFiltro == 2){
                return loadFragment(new ExerciciosFragment());
            }
        }
        return false;
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
       Fragment fragment = null;
       switch (menuItem.getItemId()){
           case R.id.navigation_home:
               fragment = new HomeFragment();
               break;
           case R.id.navigation_exercicios:
               fragment = new ExerciciosFragment();
               break;
           case R.id.navigation_treino:
               fragment = new TreinoFragment();
               break;

           case R.id.navigation_conta:
               fragment = new ContaFragment();
               break;
       }
       return loadFragment(fragment);
    }
}
