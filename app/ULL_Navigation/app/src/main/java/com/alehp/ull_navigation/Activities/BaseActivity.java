package com.alehp.ull_navigation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alehp.ull_navigation.Fragments.AboutFragment;
import com.alehp.ull_navigation.Fragments.HomeFragment;
import com.alehp.ull_navigation.Fragments.MapsFragment;
import com.alehp.ull_navigation.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView usernameText;

    Toolbar toolbar;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_draw);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        usernameText = navigationView.getHeaderView(0).findViewById(R.id.usernameText);
        setHeader();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction tx;
                Intent intent;
                switch (item.getItemId()) {

                    case R.id.menu_home:
                        tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.content_frame, new HomeFragment());
                        tx.addToBackStack("fragBack").commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_maps:
                        tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.content_frame, new MapsFragment());
                        tx.addToBackStack("fragBack").commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_ar:
                        intent = new Intent(getApplicationContext(), ARNavigation.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_settings:
                        intent = new Intent(getApplicationContext(), SettingsULLActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_logout:
                        logout();
                        goToLogin();
                        break;
                    case R.id.menu_about:
                        tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.content_frame, new AboutFragment());
                        tx.addToBackStack("fragBack").commit();
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void setHeader() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);


        if (acct != null) {
            String personName = acct.getDisplayName().toLowerCase();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            usernameText.setText(WordUtils.capitalize(personName.trim()));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSingInResult(result);
        }
    }

    private void handleSingInResult(GoogleSignInResult result){
        if(result.isSuccess()== true){
            GoogleSignInAccount account = result.getSignInAccount();
//            usernameText.setText("account.getDisplayName()");
        }else{
           goToLogin();
        }
    }

    private void goToLogin(){
        Intent intent =new Intent(this, LoginActivityULL.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    private void revokeAccess(){
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }

    private void logout(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(BaseActivity.this, "Has cerrado sesion", Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}