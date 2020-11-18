package apps.webscare.radiory.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import apps.webscare.radiory.Adapters.CountriesAdapter;
import apps.webscare.radiory.Adapters.GridViewCountriesAdapter;
import apps.webscare.radiory.Models.GetCountriesModel;
import apps.webscare.radiory.R;
import apps.webscare.radiory.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HOme extends AppCompatActivity implements CountriesAdapter.OnCountryClickedInterface , GridViewCountriesAdapter.GridClickInterface , NavigationView.OnNavigationItemSelectedListener {



    RecyclerView countriesRecyclerview , countriesGridViewRecycler;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    ImageView navigationDrawerToggleIv , homeImageView;
//    ImageView loadingView;
    ArrayList<GetCountriesModel> countriesModels;
    ImageView linearViewBtn , gridViewBtn , searchIcon;
    EditText searchBar;
    TextView usaTv , ukTv , canadaTv , homeTv;
    ArrayList<GetCountriesModel> searchedRecords;
    boolean isSearched = false;
    CountriesAdapter countriesAdapter , searchedCountriesAdapter , linearClickedAdapter;
    GridViewCountriesAdapter gridViewCountriesAdapter ,searchedGridViewCountriesAdapter , gridClickedAdapter;
    LottieAnimationView preLoadAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries_drawer_layout);

        preLoadAnimationView = findViewById(R.id.preLoadAnimationView);
//        preLoadAnimationView.startAnimation();
        linearViewBtn = findViewById(R.id.linearViewBtnId);
        gridViewBtn = findViewById(R.id.gridViewBtnID);
//        loadingView = findViewById(R.id.loadingImageViewId);
        homeImageView = findViewById(R.id.imageView2);
        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHome = new Intent(HOme.this , HOme.class);
                startActivity(toHome);
                finish();
            }
        });

        searchIcon = findViewById(R.id.searchCountryImageViewId);
        homeTv = findViewById(R.id.textViewHome);
        homeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHome = new Intent(HOme.this , HOme.class);
                startActivity(toHome);
                finish();
            }
        });
        usaTv = findViewById(R.id.textViewUSA);
        ukTv = findViewById(R.id.textViewUK);
        canadaTv = findViewById(R.id.textViewCanada);
        usaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRadios( "USA" ,7);

            }
        });
        ukTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRadios( "UK" ,10);
            }
        });
        canadaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRadios( "Canada" ,15);
            }
        });
        countriesRecyclerview = findViewById(R.id.recyclerViewCountriesId);
        countriesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        countriesRecyclerview.setHasFixedSize(true);
        countriesRecyclerview.scheduleLayoutAnimation();

//        countriesRecyclerview.setItemAnimator(new DefaultItemAnimator());

        countriesGridViewRecycler = findViewById(R.id.recyclerViewCountriesGridViewId);
        countriesGridViewRecycler.setHasFixedSize(true);
        countriesGridViewRecycler.setLayoutManager(new GridLayoutManager(this , 3));
        countriesGridViewRecycler.scheduleLayoutAnimation();

//        Glide.with(this).load(getResources().getDrawable(R.drawable.loading_gif)).into(loadingView);
        countriesRecyclerview.setVisibility(View.GONE);
     /*   final Dialog dialog = new Dialog("Loading HOme");
        dialog.startDialog(this);*/
        preLoadAnimationView.setAnimation(R.raw.pre_load_animation);
        Call<List<GetCountriesModel>> call = RetrofitClient.getInstance().getApi().getCountries();
        call.enqueue(new Callback<List<GetCountriesModel>>() {
            @Override
            public void onResponse(Call<List<GetCountriesModel>> call, Response<List<GetCountriesModel>> response) {
                if (response.isSuccessful()){
                    countriesModels = (ArrayList<GetCountriesModel>) response.body();
                    countriesAdapter = new CountriesAdapter(countriesModels , HOme.this , HOme.this);
                    countriesRecyclerview = findViewById(R.id.recyclerViewCountriesId);
                    countriesRecyclerview.setLayoutManager(new LinearLayoutManager(HOme.this));
                    countriesRecyclerview.setHasFixedSize(true);
                    countriesRecyclerview.setAdapter(countriesAdapter);
                    countriesGridViewRecycler.setVisibility(View.VISIBLE);
                    preLoadAnimationView.setVisibility(View.GONE);


                    gridViewCountriesAdapter = new GridViewCountriesAdapter(countriesModels , HOme.this , HOme.this);
                    countriesGridViewRecycler.setHasFixedSize(true);
                    countriesGridViewRecycler.setLayoutManager(new GridLayoutManager(HOme.this , 2));
                    countriesGridViewRecycler.setAdapter(gridViewCountriesAdapter);
                }
                else{
                   preLoadAnimationView.setVisibility(View.GONE);
                    Toast.makeText(HOme.this, "Response Not Successful", Toast.LENGTH_SHORT).show();
                    countriesGridViewRecycler.setVisibility(View.VISIBLE);
//                    loadingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<GetCountriesModel>> call, Throwable t) {
                Toast.makeText(HOme.this, "Failed to Load HOme   " + t.getMessage() , Toast.LENGTH_SHORT).show();
                Log.d("Failed", t.getMessage() + "     Cause : " + t.getCause());
                countriesGridViewRecycler.setVisibility(View.VISIBLE);
                preLoadAnimationView.setVisibility(View.GONE);
            }
        });
        searchBar = findViewById(R.id.searchCountriesId);
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

        linearViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countriesRecyclerview.setVisibility(View.VISIBLE);
                countriesGridViewRecycler.setVisibility(View.GONE);
                linearViewBtn.setImageDrawable(getResources().getDrawable(R.drawable.list_red));
                gridViewBtn.setImageDrawable(getResources().getDrawable(R.drawable.grid_greh));
                Animation rightToLeft = AnimationUtils.loadAnimation(HOme.this , R.anim.move_left);
                linearViewBtn.startAnimation(rightToLeft);
                if(isSearched){
                    linearClickedAdapter = new CountriesAdapter(searchedRecords , HOme.this , HOme.this);
                    countriesRecyclerview.setAdapter(linearClickedAdapter);
                }
                else{
                    linearClickedAdapter = new CountriesAdapter(countriesModels , HOme.this , HOme.this);
                    countriesRecyclerview.setAdapter(linearClickedAdapter);
                }
//                isSearched = false;
            }
        });

        gridViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countriesGridViewRecycler.setVisibility(View.VISIBLE);
                countriesRecyclerview.setVisibility(View.GONE);
                gridViewBtn.setImageDrawable(getResources().getDrawable(R.drawable.grid_red));
                linearViewBtn.setImageDrawable(getResources().getDrawable(R.drawable.list_grey));
                Animation leftToRight = AnimationUtils.loadAnimation(HOme.this , R.anim.move_right);
                gridViewBtn.startAnimation(leftToRight);
                if(isSearched){
                    gridClickedAdapter = new GridViewCountriesAdapter(searchedRecords , HOme.this , HOme.this);
                    countriesGridViewRecycler.setAdapter(gridClickedAdapter);
                }
                else{
                    gridClickedAdapter = new GridViewCountriesAdapter(countriesModels , HOme.this , HOme.this);
                    countriesGridViewRecycler.setAdapter(gridClickedAdapter);
                }
//                isSearched = false;
            }
        });

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    performSearch();
                    searchCountry();
                    hideKeybaord(searchBar);
//                    Toast.makeText(HOme.this, "Text : " + searchBar.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCountry();
                hideKeybaord(searchBar);
            }
        });

    }

    @Override
    public void onCountryClicked(int position) {
        if (isSearched){
            goToRadios( searchedRecords.get(position).getName() ,searchedRecords.get(position).getTermId());
        }else{
            goToRadios( countriesModels.get(position).getName() ,countriesModels.get(position).getTermId());
        }
    }

    @Override
    public void onGridItemClicked(int position) {
        if (isSearched){
            goToRadios( searchedRecords.get(position).getName() ,searchedRecords.get(position).getTermId());
        }else{
            goToRadios( countriesModels.get(position).getName() ,countriesModels.get(position).getTermId());
        }
    }

    public void goToRadios(String channelName , int channelId){
        Intent toRadio = new Intent(this , Radios.class);
        toRadio.putExtra("channelName" ,channelName);
        toRadio.putExtra("countryId" ,channelId);
        startActivity(toRadio);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id){
            case R.id.aboutUsMenuItemId:
                Intent toAboutUs = new Intent(HOme.this , AboutUs.class);
                startActivity(toAboutUs);
                finish();
                return true;
           /* case R.id.categoriestMenuItemID:
                Intent toMain = new Intent(HOme.this , MainActivity.class);
                startActivity(toMain);
                finish();
                return true;*/
            case R.id.radiosMenuItemId:
                Intent toRadios = new Intent(HOme.this , Radios.class);
                startActivity(toRadios);
                finish();
                return true;
            case R.id.countriesMenuItemId:
                Intent toCountries = new Intent(HOme.this , HOme.class);
                startActivity(toCountries);
                finish();
                return true;

            default:
                Toast.makeText(this, "Some Error Occurred In Navigation Menu", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        searchBar.setCursorVisible(false);
    }

    private void searchCountry(){
        searchedRecords = new ArrayList<>();
        String camelString = toCamelCase(searchBar.getText().toString());
        for (GetCountriesModel radioModel:
                countriesModels) {
            if (radioModel.getName().contains(toCamelCase(camelString))){
                searchedRecords.add(radioModel);
            }
        }

        if (searchedRecords.size() != 0){
            searchedCountriesAdapter = new CountriesAdapter(searchedRecords , HOme.this , HOme.this);
            countriesRecyclerview.setAdapter(searchedCountriesAdapter);

            searchedGridViewCountriesAdapter = new GridViewCountriesAdapter(searchedRecords , HOme.this , HOme.this);
            countriesGridViewRecycler.setAdapter(searchedGridViewCountriesAdapter);
            isSearched = true;
            hideKeybaord(searchBar);
        }
        else{
            Toast.makeText(HOme.this, "No Such Country Registered", Toast.LENGTH_SHORT).show();
        }
        searchBar.setText("");
    }

    public String toCamelCase(String s) {
        // create a StringBuilder to create our output string
        StringBuilder sb = new StringBuilder();

        // determine when the next capital letter will be
        Boolean nextCapital = false;

        // loop through the string
        for (int i = 0; i < s.length(); i++) {

            // if the current character is a letter
            if (i == 0 ) {
                if (Character.isLetter(s.charAt(0))) {

                    // get the current character
                    char tmp = s.charAt(0);

                    // make it a capital if required
                    tmp = Character.toUpperCase(tmp);

                    // add it to our output string
                    sb.append(tmp);
                }
            }else {
                sb.append(s.charAt(i));
            }
        }
        // return our output string
        return sb.toString();
    }

}