package com.psyberia.sms_regcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.Balance;
import com.psyberia.sms_regcom.screens.ScreenGetNum;
import com.psyberia.sms_regcom.screens.ScreenOperation;
import com.psyberia.sms_regcom.screens.ScreenOrderAdd;
import com.psyberia.sms_regcom.screens.ScreenRate;
import com.psyberia.sms_regcom.sdk.IOnFragmentInteractionListener;
import com.psyberia.sms_regcom.ui.dialog.ApiKeyDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by combo on 07.04.2017.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IOnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private Callback<Balance> balanseCallback = new Callback<Balance>() {
        @Override
        public void onResponse(Call<Balance> call, Response<Balance> response) {
            if (response.isSuccessful()) {


                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    Toast.makeText(MainActivity.this, ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm != null) {
                    Balance data = response.body();
                    //tvResponse.setText(data.getBalance());

                    String result = String.format(Locale.getDefault(), "%.2f", Float.valueOf(data.getBalance()));
                    //tvBalance.setText(result);//1 - success
                    Toast.makeText(MainActivity.this, "На вашем счету " + result + "руб.", Toast.LENGTH_LONG).show();
                }

            }

        }

        @Override
        public void onFailure(Call<Balance> call, Throwable t) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (null != findViewById(R.id.container)) {
            if (null != savedInstanceState) {
                return;
            }

            ScreenOperation firstFragment = ScreenOperation.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, firstFragment)
                    .commit();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_insert_api_key:
                new ApiKeyDialog(this).show();
                return true;

            case R.id.action_get_num:
                replaceFragment(new ScreenGetNum());
                return true;

            case R.id.action_balance:
                //setTitle(item.getTitle());
                //replaceFragment(new ScreenBalance());
                MyApplication.getApi().getBalance().enqueue(balanseCallback);
                return true;
            case R.id.action_operations:
                //setTitle(item.getTitle());
                //replaceFragment(new ScreenOperationTab1());
                replaceFragment(ScreenOperation.newInstance());
                return true;
            case R.id.action_set_rate:
                //setTitle(item.getTitle());
                replaceFragment(new ScreenRate());
                return true;


            case R.id.action_order_add:
                //setTitle(item.getTitle());
                replaceFragment(new ScreenOrderAdd());
                return true;

            case R.id.action_exit:
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment); //PlaceholderFragment.newInstance(item.getTitle())
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

