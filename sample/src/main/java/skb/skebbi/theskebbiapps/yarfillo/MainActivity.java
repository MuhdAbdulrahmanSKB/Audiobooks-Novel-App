package skb.skebbi.theskebbiapps.yarfillo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.general.errors.OnInvalidPathListener;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements OnInvalidPathListener, JcPlayerManagerListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Animation bottonAnim;
    private JcPlayerView player;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        recyclerView = findViewById(R.id.recyclerView);
        player = findViewById(R.id.jcplayer);
    
        // Animations
        bottonAnim = AnimationUtils.loadAnimation(this, R.anim.bounce2);
    
        //Hooks
        player.setAnimation(bottonAnim);

        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 1", R.raw.yf1));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 2", R.raw.yf2));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 3", R.raw.yf3));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 4", R.raw.yf4));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 5", R.raw.yf6));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 6", R.raw.yf6));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 7", R.raw.yf7));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 8", R.raw.yf8));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 9", R.raw.yf9));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 10", R.raw.yf10));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 11", R.raw.yf11));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 12", R.raw.yf12));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 13", R.raw.yf13));
        jcAudios.add(JcAudio.createFromRaw("Yar Fillo Episode 14", R.raw.yf14));
//        jcAudios.add(JcAudio.createFromRaw("", R.raw.));

        player.createNotification(R.drawable.spicon);
        player.initPlaylist(jcAudios, this);
        adapterSetup();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.createNotification(R.drawable.spicon);
    }

    protected void adapterSetup() {
        audioAdapter = new AudioAdapter(player.getMyPlaylist());
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                player.playAudio(player.getMyPlaylist().get(position));
            }

            @Override
            public void onSongItemDeleteClicked(int position) {
                Toast.makeText(MainActivity.this, "Delete song at position " + position,
                        Toast.LENGTH_SHORT).show();
//                if(player.getCurrentPlayedAudio() != null) {
//                    Toast.makeText(MainActivity.this, "Current audio = " + player.getCurrentPlayedAudio().getPath(),
//                            Toast.LENGTH_SHORT).show();
//                }
                removeItem(position);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        player.createNotification(R.drawable.spicon);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.kill();
    }

    @Override
    public void onPathError(JcAudio jcAudio) {
        Toast.makeText(this, jcAudio.getPath() + " with problems", Toast.LENGTH_LONG).show();
//        player.removeAudio(jcAudio);
//        player.next();
    }


    @Override
    public void onPreparedAudio(JcStatus status) {

    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onPaused(JcStatus status) {

    }

    @Override
    public void onContinueAudio(JcStatus status) {

    }

    @Override
    public void onPlaying(JcStatus status) {

    }

    @Override
    public void onTimeChanged(@NonNull JcStatus status) {
        updateProgress(status);
    }

    @Override
    public void onJcpError(@NonNull Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void updateProgress(final JcStatus jcStatus) {
        Log.d(TAG, "Song duration = " + jcStatus.getDuration()
                + "\n song position = " + jcStatus.getCurrentPosition());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // calculate progress
                float progress = (float) (jcStatus.getDuration() - jcStatus.getCurrentPosition())
                        / (float) jcStatus.getDuration();
                progress = 1.0f - progress;
                audioAdapter.updateProgress(jcStatus.getJcAudio(), progress);
            }
        });
    }

    private void removeItem(int position) {
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);

        //        jcAudios.remove(position);
        player.removeAudio(player.getMyPlaylist().get(position));
        audioAdapter.notifyItemRemoved(position);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public void onStopped(JcStatus status) {

    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.item_1:
                break;
            case R.id.item_2:
                 Toast.makeText(MainActivity.this, "Please Upgrade To Premium", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3:
                i = new Intent(this,About.class);
                startActivity(i);
                break;
            case R.id.item_4:
                i = new Intent(this,Developer.class);
                startActivity(i);
                break;
            case R.id.item_5:
                finish();
        }
        return super.onOptionsItemSelected(item); }
}
