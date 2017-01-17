package com.example.amrsaidam.tigarty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.com.tigarty.api.Constants;
import com.example.amrsaidam.tigarty.Fragments.MainFragment;
import com.example.amrsaidam.tigarty.Fragments.ProductList;
import com.example.amrsaidam.tigarty.Fragments.SalePointFragment;
import com.example.amrsaidam.tigarty.Fragments.TraderInvoiceFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
public class Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  new ProductListOperationsTest().run();
        //  drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

//        DrawerLayout.DrawerListener drawerListener =  new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                drawer.openDrawer(drawerView,true);
//
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//        };
//        drawer.addDrawerListener(drawerListener);
        //  this.getResources().getV
        //drawer.addDrawerListener();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getActionBar().setTitle(mTitle);
                // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // getActionBar().setTitle(mDrawerTitle);
                // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


        };
//        ViewDragHelper viewDragHelper = new ViewDragHelper();
//        viewDragHelper.smoothSlideViewTo()
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        drawer.getRootView();


        //drawer.openDrawer(drawer.getRootView(),false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Fragment mainFragment = new MainFragment();
        initiateFragment(mainFragment, "mainFragment");


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this.getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.pie_chart_icon) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_menu, new MainFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(this.getBaseContext(), "in nn ", Toast.LENGTH_SHORT).show();
            initiateFragment(new SalePointFragment(), "salePoint");
        } else if (id == R.id.nav_gallery) {
            initiateFragment(new TraderInvoiceFragment(), "traderInvoice");
        } else if (id == R.id.nav_slideshow) {
            initiateFragment(new ProductList(), "productListFragment");
        } else if (id == R.id.nav_manage) {
            initiateFragment(new ProductList(), "productListFragment");
        } else if (id == R.id.LogoutAction) {
            try {
                logOut();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        } else if (id == R.id.nav_send) {
//
//        }

        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, android.view.Menu menu) {
        //return super.onMenuOpened(featureId, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_menu);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    private void initiateFragment(Fragment fragment, String tag) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(fragment, tag);
        fragmentTransaction.replace(R.id.content_menu, fragment);
        fragmentTransaction.commit();
    }

    private void logOut() throws IOException {
        File tigartyDir = new File(Environment.getExternalStorageDirectory(), "" + Constants.TIGARTY_DIR);
        File userFile = new File(tigartyDir, Constants.USER_FILE + ".txt");
        userFile.delete();

        Intent intent = new Intent(Menu.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
