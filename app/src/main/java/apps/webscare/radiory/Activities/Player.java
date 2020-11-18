package apps.webscare.radiory.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import apps.webscare.radiory.Dialog;
import apps.webscare.radiory.R;

public class Player extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // todo implement click listener to navigation Drawer [DONE]
    // todo countries item is not responding properly fix it after. [DONE]
    // todo add navigation drawer to all remaining activities [DONE]
    // todo implement Radios API [DONE]
    // todo create the player activity backend[DONE]
    // todo create a home screen like Live Radio Player[DONE]
    // todo implement search through clicking the right drawable of the searchBox Edit_text[DONE]
    // todo implement the animation between grid and linear view of countries [DONE]
    // todo remove the country number in at the left side of countries item [DONE]
    // todo implement animations in the recyclerview(s) item load(s)[DONE]
    // todo player screen navigation drawer is not operational fix it [DONE]
    // todo make a way to get back to the all countries after searching the country.[DONE]
    // todo implement lottie animation in player screen [DONE]
    // todo hide the keyboard when search is clicked in both home and radios screen[DONE]
    // todo  Home -> 3rd Column [DONE]
    // todo  Search -> Case Sensitive [DONE]
    // todo  Player -> Error Issue Navigate back to Radios Screen [DONE]
    // todo  App:  Pre-loading gif [DONE]

    ImageView playPauseImageview;
    boolean isPlaying = true;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    ImageView navigationDrawerToggleIv;
    MediaPlayer mediaPlayer;
    String streamingUrl;
    TextView countryNameTv, radioNameTv;
    com.airbnb.lottie.LottieAnimationView lottieAnimationView;
    int errorUnKnown = 0;
    boolean isResponded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_drawer_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        streamingUrl = getIntent().getStringExtra("streamingUrl");
        playPauseImageview = findViewById(R.id.playPauseBtn);
        playPauseImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    playPauseImageview.setImageResource(R.drawable.play_btn_image);
                    isPlaying = false;
                    mediaPlayer.pause();
                    lottieAnimationView.pauseAnimation();
                } else {
                    playPauseImageview.setImageResource(R.drawable.pause_btn_img);
                    mediaPlayer.start();
                    isPlaying = true;
                    lottieAnimationView.playAnimation();
                }
            }
        });
        countryNameTv = findViewById(R.id.countryNamePlayerId);
        radioNameTv = findViewById(R.id.radioNamePlayerId);

        countryNameTv.setText(getIntent().getStringExtra("countryName"));
        radioNameTv.setText(getIntent().getStringExtra("channelName"));

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

        lottieAnimationView = findViewById(R.id.animationView);
        lottieAnimationView.setAnimation(R.raw.player_screen_animation);
        lottieAnimationView.pauseAnimation();

//        Animation playerAnimation = AnimationUtils.loadAnimation(this , R.raw.player_screen_animation);
//        lottieAnimationView.startAnimation(playerAnimation);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        final Dialog dialog = new Dialog("Loading Radio Stream");
        dialog.startDialog(this);
        prepareMediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });

        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                int duration = mediaPlayer.getDuration();
                dialog.progressDialog.dismiss();
                lottieAnimationView.playAnimation();
                isResponded = true;
                if (duration != 0 || duration != -1) {
                    playPauseImageview.setImageDrawable(getResources().getDrawable(R.drawable.pause_btn_img, null));
                    mediaPlayer.start();
                } else {
                    Toast.makeText(Player.this, "Channel Unable to Play Yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                dialog.progressDialog.dismiss();
                switch (i) {
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        mediaPlayer.stop();
                        if (errorUnKnown == 0) {
                            Toast.makeText(Player.this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
                            errorUnKnown++;
                            finish();
                        }
                        return true;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        Toast.makeText(Player.this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show();
//                        playPauseImageview.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_image, null));
                        mediaPlayer.stop();
                        finish();
                        return true;
                    default:
                        break;
                }
                switch (i1) {
                    case MediaPlayer.MEDIA_ERROR_IO:
//                        playPauseImageview.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_image, null));
                        mediaPlayer.stop();
                        Toast.makeText(Player.this, "MEDIA_ERROR_IO", Toast.LENGTH_SHORT).show();
                        lottieAnimationView.pauseAnimation();
                        finish();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_MALFORMED:
//                        playPauseImageview.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_image, null));
                        mediaPlayer.stop();
                        lottieAnimationView.pauseAnimation();
                        Toast.makeText(Player.this, "MEDIA_ERROR_MALFORMED", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
//                        playPauseImageview.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_image, null));
                        mediaPlayer.stop();
                        lottieAnimationView.pauseAnimation();
                        Toast.makeText(Player.this, "MEDIA_ERROR_UNSUPPORTED", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;
                    case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
//                        playPauseImageview.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_image, null));
                        mediaPlayer.stop();
                        lottieAnimationView.pauseAnimation();
                        Toast.makeText(Player.this, "MEDIA_ERROR_TIMED_OUT", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isResponded && errorUnKnown == 0) {
                    dialog.progressDialog.dismiss();
                    Toast.makeText(Player.this, "Response Time Out !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, 10000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.aboutUsMenuItemId:
                Intent toAboutUs = new Intent(this, AboutUs.class);
                startActivity(toAboutUs);
                finish();
                return true;
          /*  case R.id.categoriestMenuItemID:
                Intent toMain = new Intent(this, MainActivity.class);
                startActivity(toMain);
                finish();
                return true;*/
            case R.id.radiosMenuItemId:
                Intent toRadios = new Intent(this, Radios.class);
                startActivity(toRadios);
                finish();
                return true;
            case R.id.countriesMenuItemId:
                Intent toCountries = new Intent(this, HOme.class);
                startActivity(toCountries);
                finish();
                return true;

            default:
                Toast.makeText(this, "Some Error Occurred In Navigation Menu", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    private void prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource(streamingUrl);
            mediaPlayer.prepareAsync();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}