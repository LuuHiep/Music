package services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.lau.music.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import activity.MainActivity;
import model.Song;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener{
    //media player
    private MediaPlayer mediaPlayer;

    private final IBinder musicBind = new MusicBinder();
    //song list
    private ArrayList<Song> listsongs;

    //current position
    private int songPosn;
    private int idSong;

    public boolean shuffle = false;
    public boolean repeat;
    private Random rand;

//    // notification
    private static final int NOTIFICATION_ID = 1609;
    public static final String ACTION_STOP_SERVICE = "com.example.iceman.mp3player.ACTION_STOP_SERVICE";
    private boolean isShowNotification = false;
    private NotificationManager notificationManager;
    private Notification n;
    private RemoteViews bigViews;
    private RemoteViews views;
    private Song currentSong;

    @Override
    public void onCreate() {
        //initialize position
        songPosn = 0;
        //create player
        mediaPlayer = new MediaPlayer();

        initMusicPlayer();
        super.onCreate();
    }

    //binder
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.release();
        return super.onUnbind(intent);
    }

    // Play a song
    public void playSong() {
        //play
        mediaPlayer.reset();
        //get song
        Song playSong = listsongs.get(songPosn);
        int currSong = playSong.getId();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currSong);
        //set the data source
        try {
            mediaPlayer.setDataSource(getApplicationContext(), trackUri);
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        setShowNotification();
    }

    //set the song
    public void setSong(int songIndex) {
        songPosn = songIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mediaPlayer.getCurrentPosition()>0){
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
      mediaPlayer.start();
    }

    //pass song list
    public void setList(ArrayList<Song> theSongs) {
        listsongs = theSongs;
    }

    public void initMusicPlayer() {
        //set player properties
        mediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //set listeners
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    private String getNameSongPlay() {
        String nameSong = listsongs.get(songPosn).getNameSong();
        return nameSong;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    private void releaseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            stopMusic();
            mediaPlayer.release();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void playPauseMusic() {
        if (mediaPlayer.isPlaying()) {
            pauseMusic();
        } else {
            resumeMusic();
        }
    }

    //skip to previous track
    public void playPrev() {

        if (songPosn < 0) {
            songPosn = listsongs.size() - 1;
        } else {
            songPosn--;
        }
        playSong();
    }

    public void playNext() {
        if(repeat){
            songPosn = songPosn;
        } else if (shuffle) {
            int newSong = songPosn;
            while (newSong == songPosn) {
                newSong = rand.nextInt(listsongs.size());
            }
            songPosn = newSong;
        } else {
            songPosn++;
            if (songPosn >= listsongs.size()) songPosn = 0;
        }
        playSong();
    }

    public boolean playShufle(){
        if (shuffle){
            shuffle = false;
        } else {
            shuffle = true;
        }
        return shuffle;
    }

    public boolean playRepeat(){
        if(repeat){
            repeat = false;
        } else {
            repeat = true;
        }
        return repeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    // Set SeekBar
    public void seekTo(int seconds) {
        mediaPlayer.seekTo(seconds * 1000);
    }

    public int getTotalTime() {

        return mediaPlayer.getDuration() / 1000;
    }

    public int getCurrentLength() {
        return mediaPlayer.getCurrentPosition() / 1000;
    }

    public int getSongPosn() {
        return songPosn;
    }

    public void setSongPosn(int songPosn) {
        this.songPosn = songPosn;
    }

    // notification
    private void setShowNotification(){

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification notification  = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.icon_artist)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();
        startForeground(1,notification);
    }

    @Override
    public void onDestroy() {
        stopMusic();
        super.onDestroy();
    }
}
