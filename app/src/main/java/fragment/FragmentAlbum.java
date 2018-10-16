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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lau.music.R;

import java.util.ArrayList;

import adapter.AlbumAdapter;
import model.Album;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class FragmentAlbum extends Fragment {

    private View view;
    private ArrayList<Album> arrayListAlbum;
    private RecyclerView rvAlbum;
    private AlbumAdapter albumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        initViewAlbum();
        return view;
    }

    private void initViewAlbum(){
        RecyclerView recyclerViewAlbum = view.findViewById(R.id.rv_album);
        recyclerViewAlbum.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplication(), VERTICAL, false);
        recyclerViewAlbum.setLayoutManager(layoutManager);
        arrayListAlbum = new ArrayList<>();
        albumAdapter = new AlbumAdapter(arrayListAlbum,getContext());
        // get data listSong
        getListDataAlbum();
        albumAdapter.notifyDataSetChanged();
        recyclerViewAlbum.setAdapter(albumAdapter);
    }

    private void getListDataAlbum(){
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(albumUri, null, null, null, null);
        {
            if (songCursor != null && songCursor.moveToFirst()) {
                int albumName = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                int albumNameArtist = songCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
                int albumData = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                do {
                    String currentTitle = songCursor.getString(albumName);
                    String currentArtist = songCursor.getString(albumNameArtist);
                    String data = songCursor.getString(albumData);
                    arrayListAlbum.add(new Album(0 , currentTitle, currentArtist, data));
                } while (songCursor.moveToNext());
            }
        }

    }
}
