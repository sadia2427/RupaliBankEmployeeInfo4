package com.example.rupalibankemployeeinfo;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.rupalibankemployeeinfo.api.model.SignInInfromation;
import com.example.rupalibankemployeeinfo.ui.changepassword.ChangePasswordFragment;
import com.example.rupalibankemployeeinfo.ui.signin.SessionManager;
import com.example.rupalibankemployeeinfo.ui.signin.SignInActivity;
import com.example.rupalibankemployeeinfo.ui.signin.StoreUserInformation;


import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
//    SearchView searchView;
    private Toolbar toolbar;
    private SessionManager session;
    private StoreUserInformation mStoreUserInformation;
    private SignInInfromation mSignInInfromation;

    private AppBarConfiguration mAppBarConfiguration;
    private String mRegID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        searchView=findViewById(R.id.search_item);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // set action of fab button
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////                SearchFragment fragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.example_fragment);
////                fragment.<specific_function_name>();
//                Fragment fragment=new SearchFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.nav_host_fragment, fragment);
//                ft.addToBackStack(null);
//                fm.popBackStackImmediate();
//                ft.commit();
//            }
//        });
        session=new SessionManager(getApplicationContext());
        mStoreUserInformation=new StoreUserInformation(getApplicationContext());
        mSignInInfromation=new SignInInfromation(getApplicationContext());

//        if (!session.isLoggedIn()){
//
//            Intent intent = new Intent(MainActivity.this,SignInActivity.class);
//            startActivity(intent);
//            finish();
//        }

        if(getIntent().getStringExtra("changePass")!=null){
            Bundle mBundle=new Bundle();
            mBundle.putString("regNo", String.valueOf(getIntent().getStringExtra("changePass")));
            Fragment fragment = new ChangePasswordFragment();
            fragment.setArguments(mBundle);

            //getActivity().getFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search,R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_about,R.id.nav_changepassword, R.id.nav_notices, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            session.logoutUser(getApplicationContext());
            mStoreUserInformation.clearInformation(getApplicationContext());
            mSignInInfromation.clearSignin(getApplicationContext());
            startActivityForResult(new Intent(MainActivity.this, SignInActivity.class),100);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
