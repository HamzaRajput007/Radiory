package apps.webscare.radiory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import apps.webscare.radiory.R;

public class AboutUs extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    ImageView navigationDrawerToggleIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationViewId) ;
        mNavigationView.setNavigationItemSelectedListener(this);
        navigationDrawerToggleIv = findViewById(R.id.drawerToggleBtnId);
        navigationDrawerToggleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.aboutUsMenuItemId:
                Intent toAboutUs = new Intent(AboutUs.this , AboutUs.class);
                startActivity(toAboutUs);
                finish();
                return true;
           /* case R.id.categoriestMenuItemID:
                Intent toMain = new Intent(AboutUs.this , MainActivity.class);
                startActivity(toMain);
                finish();
                return true;*/
            case R.id.radiosMenuItemId:
                Intent toRadios = new Intent(AboutUs.this , Radios.class);
                startActivity(toRadios);
                finish();
                return true;
            case R.id.countriesMenuItemId:
                Intent toCountries = new Intent(AboutUs.this , HOme.class);
                startActivity(toCountries);
                finish();
                return true;

            default:
                Toast.makeText(this, "Some Error Occurred In Navigation Menu", Toast.LENGTH_SHORT).show();
                return false;
        }
    }
}