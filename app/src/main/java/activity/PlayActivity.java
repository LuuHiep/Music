package activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lau.music.R;

import java.util.ArrayList;

import adapter.SongPlayAdapter;
import model.Song;
import service.Common;
import service.MusicController;
import service.MusicService;

public class PlayActivity extends AppCompatActivity{

    private Toolbar toolbarPlay;
    private TextView tvPlayedMusicTime, tvFullTime,tvTitleTbPlay;
    private SeekBar seekBar;
    private ImageView ivPrevious, ivRepeat, ivPlayPause, ivShuffle, ivNext;
    private RecyclerView rvListSongPlaying;
    private View view;

    private boolean isSeeking;
    private int position = 0;
    private int totalTime;
    private ArrayList<Song> songList;
    private ArrayList<Song> listSongPlay;
    private SongPlayAdapter songPlayAdapter;

    //service
    public MusicService musicSrv;
    private Intent playIntent;
    int currentPos;
    //binding
    private boolean musicBound = false;
    //controller
    private MusicController controller;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        getListSong();
        getPositionSong();
        //setNameSongPlay();
        setActionbar();
        initClick();
        setSeekBar();
//        updateRepeat();
        upDateNameSongPlay();
    }

    private void initView() {
        toolbarPlay = (Toolbar) findViewById(R.id.toolbar_play);
        tvPlayedMusicTime = (TextView) findViewById(R.id.tv_played_music_time);
        tvFullTime = (TextView) findViewById(R.id.tv_full_time);
        tvTitleTbPlay = (TextView) findViewById(R.id.tv_title_tb_play);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        ivPrevious = (ImageView) findViewById(R.id.iv_previous);
        ivRepeat = (ImageView) findViewById(R.id.iv_repeat);
        ivPlayPause = (ImageView) findViewById(R.id.iv_play_pause);
        ivShuffle = (ImageView) findViewById(R.id.iv_shuffle);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        rvListSongPlaying = (RecyclerView) findViewById(R.id.rv_list_song_play);

        songList = new ArrayList<>();
    }

    private void setActionbar() {
        toolbarPlay = (Toolbar) findViewById(R.id.toolbar_play);
        setSupportActionBar(toolbarPlay);
        setTitle(songList.get(position).getNameSong());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // nhận position
    private void getPositionSong() {
        position = getIntent().getIntExtra("position", 1);
        Log.d("position_1", " " + position);
    }

    private void getListSong() {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
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
                    songList.add(new Song(currenId, currentTitle, currentArtist, R.drawable.icon_beats));
                    Log.d("data", data);
                } while (songCursor.moveToNext());
            }
        }
    }

    private void setNameSongPlay() {
        setTitle(songList.get(position).getNameSong());
        tvTitleTbPlay.setText(songList.get(position).getNameSong());
    }

    // Connect to the Service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
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
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
            setNameSongPlay();
        }
    }

    private void songPicked() {
        musicSrv.setSong(position);
        Log.d("ngaydeptroi", "" + position);
        musicSrv.playSong();
        updateSeekBar();
    }

    private void initClick() {

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playPrev();
                setPlayingMusic();
            }
        });

        ivRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicSrv.isRepeat()) {
                    ivRepeat.setImageResource(R.drawable.repeat);
                    musicSrv.setRepeat(false);
                } else {
                    ivRepeat.setImageResource(R.drawable.repeat_one);
                    musicSrv.setRepeat(true);
                }
                setNameSongPlay();
            }
        });

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playPauseMusic();
                setPlayingMusic();
                if (musicSrv != null) {
                    if (musicSrv.isPlaying()) {
                        ivPlayPause.setImageResource(R.drawable.pause);
                    } else {
                        ivPlayPause.setImageResource(R.drawable.play);
                    }
                }
            }
        });

        ivShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlayingMusic();
                if (musicSrv.isShuffle()) {
                    ivShuffle.setImageResource(R.drawable.shuffle);
                    musicSrv.setShuffle(false);
                } else {
                    ivShuffle.setImageResource(R.drawable.shuffle_one);
                    musicSrv.setShuffle(true);
                }
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playNext();
                setPlayingMusic();
            }
        });
    }

    // Set SeekBar
    private void setSeekBar() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPlayedMusicTime.setText(Common.miliSecondToString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicSrv.seekTo(seekBar.getProgress());
                if (!musicSrv.isPlaying()) {
                    musicSrv.resumeMusic();
                    ivPlayPause.setImageResource(R.drawable.pause);
                }
                isSeeking = false;
                updateSeekBar();
            }
        });

    }

    private void updateSeekBar() {
        totalTime = musicSrv.getTotalTime();
        seekBar.setMax(totalTime);
        int currentLength = musicSrv.getCurrentLength();
        Log.d("currentlength", "" + currentLength);
        if (!isSeeking) {
            seekBar.setProgress(currentLength);

            tvPlayedMusicTime.setText(Common.miliSecondToString(currentLength));
        }
        tvFullTime.setText(Common.miliSecondToString(totalTime));
        Log.d("fulltime", "" + totalTime);
        Handler musicHandler = new Handler();
        musicHandler.post(new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
            }
        });
    }

    // chưa có tác dụng
    private void setPlayingMusic() {
        for (int i = 0; i < songList.size(); i++) {
            if (musicSrv.getIdSong() == songList.get(i).getId()) {
                // Log.d("idsong ", " " + musicService.getId() + " " + i);
                tvTitleTbPlay.setText(songList.get(position).getNameSong());
            }
        }
    }

    private void upDateNameSongPlay(){
        tvTitleTbPlay.setText(songList.get(position).getNameSong());
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        super.onDestroy();
        unbindService(musicConnection);
    }

}
