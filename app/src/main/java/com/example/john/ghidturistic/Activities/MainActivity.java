package com.example.john.ghidturistic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Adapters.ViewPagerAdapter;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Helpers.FirebaseService;
import com.example.john.ghidturistic.Models.Objective;
import com.example.john.ghidturistic.Models.User;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageButton accountButton;
    private static boolean userLoggedIn = false;
    private static User appUser = null;
    private static ArrayList<Objective> objectives;
    public FloatingActionButton fab;
    FirebaseService firebaseService;
    ViewPagerAdapter viewPagerAdapter;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseService = FirebaseService.getInstance();
        objectives=firebaseService.getObjectives();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.map_text));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.list_text));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 2, objectives);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        searchView=(SearchView)findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return false;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddObjectiveActivity.class);
                startActivity(intent);
            }
        });
        if (!userLoggedIn) {
            fab.setVisibility(View.GONE);
        }
        accountButton = (ImageButton) findViewById(R.id.account_button);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseService.getUser() != null) {
                    String userEmail = firebaseService.getUser().getEmail();
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(Constants.Keys.EMAIL_KEY, userEmail);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Subscribe
    public static void setUserStatus(int code) {
        if(code==Constants.BusCodes.LOGIN_USER_CODE){
            userLoggedIn=true;
        }else{
            userLoggedIn=false;
        }
    }

    @Subscribe
    public static void setAppUser(User user) {
        appUser = user;
    }



    private void performSearch(String text){
        ArrayList<Objective> newObjectives=new ArrayList<>();
        for(int i=0; i<objectives.size(); i++){
            Objective objective=objectives.get(i);
            String allText=objective.getName()+" "+objective.getDescription()+" "+objective.getPosition().getLat()+" "+objective.getPosition().getLng();
            if(allText.contains(text)){
                newObjectives.add(objective);
            }
        }
        viewPagerAdapter.setObjectives(newObjectives,viewPager.getCurrentItem());
    }

}
