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

import adapter.ArtistAdapter;
import model.Artist;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class FragmentArtist extends Fragment {
    private View view;
    private ArrayList<Artist> arrayListArtist;
    private RecyclerView rvArtist;
    private ArtistAdapter artistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artist, container, false);
        initViewArtist();
        return view;
    }

    public void initViewArtist() {
        RecyclerView recyclerViewArtist = view.findViewById(R.id.rv_artist);
        recyclerViewArtist.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplication(), VERTICAL, false);
        recyclerViewArtist.setLayoutManager(layoutManager);
        arrayListArtist = new ArrayList<>();
        artistAdapter = new ArtistAdapter(arrayListArtist, getContext());
        // get data listSong
        getDataListArtist();
        artistAdapter.notifyDataSetChanged();
        recyclerViewArtist.setAdapter(artistAdapter);
    }

    public void getDataListArtist(){
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri songUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        {
            if (songCursor != null && songCursor.moveToFirst()) {
                int nameArtist = songCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
                int numberAlbum = songCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
                int numberSong = songCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);

                do {
                    String currentArtist = songCursor.getString(nameArtist);
                    String currentNumberAlbum = songCursor.getString(numberAlbum);
                    String cursorNumberSong = songCursor.getString(numberSong);
                    arrayListArtist.add(new Artist(0, currentArtist,currentNumberAlbum,cursorNumberSong, R.drawable.icon_artist));
                } while (songCursor.moveToNext());
            }
        }
    }
}
