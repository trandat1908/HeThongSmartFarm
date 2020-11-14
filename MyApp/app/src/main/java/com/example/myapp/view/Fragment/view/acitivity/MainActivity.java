package com.example.myapp.view.Fragment.view.acitivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.R;
import com.example.myapp.view.Fragment.view.fragment.ChartFragment;
import com.example.myapp.view.Fragment.view.fragment.ControlFragment;
import com.example.myapp.view.Fragment.view.fragment.DisplayFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    FrameLayout frameLayout;
    public static TabLayout tabLayout;

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;

    public static int currentPositionTab;
    private long backPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setToolBar();
        setTabLayout();
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            Intent start = new Intent(Intent.ACTION_MAIN);
            start.addCategory(Intent.CATEGORY_HOME);
            start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start);
            finish();
            System.exit(0);
        }
        else {
            Toast.makeText(MainActivity.this, R.string.press_exit, Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }


    private void setTabLayout() {
        TabLayout.Tab fisrtTab = tabLayout.newTab();
        fisrtTab.setText(R.string.table);
        fisrtTab.setIcon(R.drawable.ic_table);
        tabLayout.addTab(fisrtTab);


        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText(R.string.control);
        thirdTab.setIcon(R.drawable.ic_settings);
        tabLayout.addTab(thirdTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText(R.string.chart);
        secondTab.setIcon(R.drawable.ic_show_chart);
        tabLayout.addTab(secondTab);


        fragment = new DisplayFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frameContent, fragment);
        ft.commit();

        //thay đổi màu icon khi click vào tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPositionTab = tab.getPosition();
                tab.getIcon().setColorFilter(Color.parseColor("#0078FF"), PorterDuff.Mode.SRC_IN);
                fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DisplayFragment();
                        break;
                    case 1:
                        fragment = new ControlFragment();
                        break;

                    case 2:
                        fragment = new ChartFragment();
                        break;
                }
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frameContent, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initWidget() {
        frameLayout = findViewById(R.id.frameContent);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerlayout = findViewById(R.id.navigationDesign);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DisplayFragment displayFragment = new DisplayFragment();
                fragmentTransaction.replace(R.id.frameContent, displayFragment);
                fragmentTransaction.commit();
                break;

            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn đăng xuất?");
                builder.setCancelable(false);
                builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
