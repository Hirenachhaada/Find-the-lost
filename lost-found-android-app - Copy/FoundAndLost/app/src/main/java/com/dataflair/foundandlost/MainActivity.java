package com.dataflair.foundandlost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dataflair.foundandlost.Activities.StartingActivity;
import com.dataflair.foundandlost.Fragments.AddFoundItemFragment;
import com.dataflair.foundandlost.Fragments.AddItemFragment;
import com.dataflair.foundandlost.Fragments.NotificationFragment;
import com.dataflair.foundandlost.Fragments.ProfileFragment;
import com.dataflair.foundandlost.Fragments.SearchItemFragment;
import com.dataflair.foundandlost.Fragments.homeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning framelayout resource file to show fragments
        frameLayout = (FrameLayout) findViewById(R.id.FragmentContainer);
        //Assigining Bottomnavigaiton Menu
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottemNavigationView);
        Menu menuNav = bottomNavigationView.getMenu();
        //Setting the default fragment as HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new homeFragment()).commit();
        //Calling the bottoNavigationMethod when we click on any menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                    //Assigining Fragment as Null
                    Fragment fragment = null;
                    switch (item.getItemId()) {

                        //Shows the Appropriate Fragment by using id as address
                        case R.id.homeMenu:
                            fragment = new homeFragment();
                            break;
                        case R.id.notificationMenu:
                            fragment = new NotificationFragment();
                            break;

                        case R.id.addItemMenu:
                            fragment = new AddItemFragment();
                            break;

                            case R.id.searchMenu:
                            fragment = new SearchItemFragment();
                            break;
                        case R.id.profileMenu:
                            fragment = new ProfileFragment();
                            break;

                    }
                    //Sets the selected Fragment into the Framelayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fragment).commit();
                    return true;
                }
            };


    @Override
    protected void onStart() {
        super.onStart();
        //checking user already logged in  or not
        FirebaseApp.initializeApp(this);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            //if user not signed in then we will redirect this activity to LoginActivity
            Intent intent = new Intent(MainActivity.this, StartingActivity.class);
            startActivity(intent);
        }
    }
}