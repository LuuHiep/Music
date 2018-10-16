package fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lau.music.R;

import java.util.ArrayList;

import adapter.SongAdapter;
import model.Song;

public class FragmentSong extends Fragment{

    private View view;
    private RecyclerView rvSong;
    private ArrayList<Song> songArrayList;
    private SongAdapter songAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song, container, false);
        initViewSong();
        return view;
    }

    private void initViewSong(){
        RecyclerView rvSong = view.findViewById(R.id.rv_song);
        rvSong.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplication(),LinearLayoutManager.VERTICAL,false);
        rvSong.setLayoutManager(layoutManager);
        songArrayList = new ArrayList<>();
        songAdapter = new SongAdapter(songArrayList, getContext());
        getDataListSong();
        songAdapter.notifyDataSetChanged();
        rvSong.setAdapter(songAdapter);
    }

    private void getDataListSong (){
        ContentResolver contentResolver = getActivity().getContentResolver();
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
                    songArrayList.add(new Song(currenId,currentTitle, currentArtist,R.drawable.icon_beats));
                    Log.d("data", data);
                } while (songCursor.moveToNext());
            }
        }
    }
}
