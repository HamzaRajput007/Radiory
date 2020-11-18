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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import apps.webscare.radiory.R;

// todo also you need to implement the APIs into the application
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText searchBar ;
    RelativeLayout countriesCategory , channelsCategory , popMusicCategory;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    ImageView navigationDrawerToggleIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_drawer_layout);
// Todo apply navigation drawer to all remaining activities when you are back
        countriesCategory = findViewById(R.id.categoryCountriesId);
        countriesCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCountries = new Intent(MainActivity.this , HOme.class);
                startActivity(toCountries);
            }
        });

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

        searchBar = findViewById(R.id.searchCountriesId);
       /* searchBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchBar.getRight() - searchBar.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(MainActivity.this, "Seach Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.aboutUsMenuItemId:
                Intent toAboutUs = new Intent(MainActivity.this , AboutUs.class);
                startActivity(toAboutUs);
                finish();
                return true;
          /*  case R.id.categoriestMenuItemID:
                Intent toMain = new Intent(MainActivity.this , MainActivity.class);
                startActivity(toMain);
                finish();
                return true;*/
            case R.id.radiosMenuItemId:
                Intent toRadios = new Intent(MainActivity.this , Radios.class);
                startActivity(toRadios);
                finish();
                return true;
            case R.id.countriesMenuItemId:
                Intent toCountries = new Intent(MainActivity.this , HOme.class);
                startActivity(toCountries);
                finish();
                return true;


            default:
                Toast.makeText(this, "Some Error Occurred In Navigation Menu", Toast.LENGTH_SHORT).show();
                return false;
        }
    }
}