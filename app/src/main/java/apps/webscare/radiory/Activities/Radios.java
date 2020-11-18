package apps.webscare.radiory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import apps.webscare.radiory.Adapters.RadioAdapter;
import apps.webscare.radiory.Dialog;
import apps.webscare.radiory.Models.RadioModel;
import apps.webscare.radiory.R;
import apps.webscare.radiory.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Radios extends AppCompatActivity implements RadioAdapter.RadioClickInterface, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView radiosRecyclerView;
    RadioAdapter radioAdapter;
    ArrayList<RadioModel> radiosList;
    TextView countryName;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    EditText searchBar;
    ImageView navigationDrawerToggleIv, searchIcon;
    int countryId;
    LottieAnimationView preLoadAnimationView;
    int counterVar = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radios_drawer_layout);

        preLoadAnimationView = findViewById(R.id.preLoadAnimationViewRadios);
        countryId = getIntent().getIntExtra("countryId", -1);
        searchIcon = findViewById(R.id.searchRadioImageViewId);
        searchBar = findViewById(R.id.searchRadiosId);
        radiosRecyclerView = findViewById(R.id.radiosRecyclerViewId);
        countryName = findViewById(R.id.textViewRadioCountryID);
        radiosRecyclerView.setHasFixedSize(true);
        radiosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(this, "Count = " + String.valueOf(counterVar++), Toast.LENGTH_SHORT).show();
        radiosList = new ArrayList<>();

        final Dialog dialog = new Dialog("Loading Radios");
        preLoadAnimationView.setAnimation(R.raw.pre_load_animation);
        Call<List<RadioModel>> call = RetrofitClient.getInstance().getApi().getRadios(countryId);
        call.enqueue(new Callback<List<RadioModel>>() {
            @Override
            public void onResponse(Call<List<RadioModel>> call, Response<List<RadioModel>> response) {
                if (response.isSuccessful()) {
                    preLoadAnimationView.setVisibility(View.GONE);
                    radiosList = (ArrayList<RadioModel>) response.body();
                    RadioAdapter radioAdapter = new RadioAdapter(radiosList, Radios.this, Radios.this);
                    radiosRecyclerView.setAdapter(radioAdapter);
                } else {
                    preLoadAnimationView.setVisibility(View.GONE);
                    Toast.makeText(Radios.this, "Response Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RadioModel>> call, Throwable t) {
                Toast.makeText(Radios.this, "Failed to load radios", Toast.LENGTH_SHORT).show();
                preLoadAnimationView.setVisibility(View.GONE);
            }
        });

        countryName.setText(getIntent().getStringExtra("channelName"));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationViewId);
        mNavigationView.setNavigationItemSelectedListener(this);
        navigationDrawerToggleIv = findViewById(R.id.drawerToggleBtnId);
        navigationDrawerToggleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFromRadios();
                hideKeybaord(searchBar);
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    performSearch();
                    searchFromRadios();
                    hideKeybaord(searchBar);
//                    Toast.makeText(HOme.this, "Text : " + searchBar.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onRadioClicked(int position) {
        Intent toPlayer = new Intent(Radios.this, Player.class);
        String streamingLink = getStreamingLink(radiosList.get(position).getContent());
        toPlayer.putExtra("streamingUrl", streamingLink);
        toPlayer.putExtra("countryName", getIntent().getStringExtra("channelName"));
        toPlayer.putExtra("channelName", radiosList.get(position).getTitle());
        startActivity(toPlayer);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.aboutUsMenuItemId:
                Intent toAboutUs = new Intent(Radios.this, AboutUs.class);
                startActivity(toAboutUs);
                finish();
                return true;
         /*   case R.id.categoriestMenuItemID:
                Intent toMain = new Intent(Radios.this, MainActivity.class);
                startActivity(toMain);
                finish();
                return true;*/
            case R.id.radiosMenuItemId:
                Intent toRadios = new Intent(Radios.this, Radios.class);
                startActivity(toRadios);
                finish();
                return true;
            case R.id.countriesMenuItemId:
                Intent toCountries = new Intent(Radios.this, HOme.class);
                startActivity(toCountries);
                finish();
                return true;

            default:
                Toast.makeText(this, "Some Error Occurred In Navigation Menu", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    public String getImageUrl(String longText) {
        String captured = longText.substring(longText.indexOf(" src=\\\"") + 9);
        String[] splitOne = longText.split("src=\\\"");
        String[] realResut = captured.split("\" alt=");
        return realResut[0];
    }

    public String getStreamingLink(String longText) {
        String captured = longText.substring(longText.indexOf("class=\"xradiostream\">") + 21);
        String[] realResut = captured.split("</li>");
        return realResut[0];
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        searchBar.setCursorVisible(false);
    }

    private void searchFromRadios() {
        Call<List<RadioModel>> call = RetrofitClient.getInstance().getApi().searchRadio(searchBar.getText().toString().trim());
        call.enqueue(new Callback<List<RadioModel>>() {
            @Override
            public void onResponse(Call<List<RadioModel>> call, Response<List<RadioModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<RadioModel> searchResults = new ArrayList<>();
                    searchResults = (ArrayList<RadioModel>) response.body();
                    RadioAdapter adapter = new RadioAdapter(searchResults, Radios.this, Radios.this);
                    radiosRecyclerView.setAdapter(adapter);
                    hideKeybaord(searchBar);
                } else {
                    Toast.makeText(Radios.this, "Search response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RadioModel>> call, Throwable t) {
                Toast.makeText(Radios.this, "Search Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}