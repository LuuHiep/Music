package activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.lau.music.R;

import java.util.ArrayList;

import model.Song;
import service.Service;

public class PlayActivity extends AppCompatActivity {

    private Toolbar toolbarPlay;
    private TextView tvPlayedMusicTime, tvFullTime, tvTitleTbPlay;
    private SeekBar seekBar;
    private ImageView ivPrevious, ivRepeat, ivPlayPause, ivShufle, ivNext;
    private RecyclerView rvListSongPlaying;

    private ArrayList<Song> songList;
    private int position = 0;
    //service
    public Service musicSrv;
    private Intent playIntent;
    int currentPos;
    //binding
    private boolean musicBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        getPosition();
        getDataListSong();
        setNameSongPlay();
        setSeekBar();
    }

    private void initView(){
        toolbarPlay = (Toolbar) findViewById(R.id.toolbar_play);
        tvPlayedMusicTime = (TextView) findViewById(R.id.tv_played_music_time);
        tvFullTime = (TextView) findViewById(R.id.tv_full_time);
        tvTitleTbPlay = (TextView) findViewById(R.id.tv_title_tb_play);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        ivPrevious = (ImageView) findViewById(R.id.iv_previous);
        ivRepeat = (ImageView) findViewById(R.id.iv_repeat);
        ivPlayPause = (ImageView) findViewById(R.id.iv_play_pause);
        ivShufle = (ImageView) findViewById(R.id.iv_shuffle);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        rvListSongPlaying = (RecyclerView) findViewById(R.id.rv_list_song_play);

        songList = new ArrayList<>();
    }

    private void getPosition(){
        // nhận position
            position = getIntent().getIntExtra("position", 1);
            Log.d("position_1", " " + position);
    }

    private void getDataListSong(){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null,null,null );
        {
            if (songCursor != null && songCursor.moveToFirst()) {
                int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                do {
                    int currenId = songCursor.getInt(songId);
                    String currentTitle = songCursor.getString(songTitle);
                    String currentArtist = songCursor.getString(songArtist);
                    String data = songCursor.getString(songData);
                    songList.add(new Song(currenId,currentTitle, currentArtist,R.drawable.icon_beats));
                    Log.d("data", data);
                } while (songCursor.moveToNext());
            }
        }
    }

    // Connect to the Service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Service.MusicBinder binder = (Service.MusicBinder) service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            songPicked();
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    //start and bind the service when the activity starts
    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, Service.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    private void setNameSongPlay() {
        tvTitleTbPlay.setText(songList.get(position).getTitle());
    }

    private void songPicked(){
        musicSrv.setSong(position);
        musicSrv.playSong();
    }

    private void setSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicSrv.seekTo(seekBar.getProgress());
            }
        });
    }

    private void initClick(){

    }
}
