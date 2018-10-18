package fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lau.music.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AlbumAdapter;
import model.Album;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class FragmentAlbum extends Fragment {

    private RecyclerView rvAlbum;
    private List<Album> listalbum;
    private AlbumAdapter albumAdapter;
    int numberofColumns = 2;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        rvAlbum = view.findViewById(R.id.rv_album);
        setHasOptionsMenu(true);

        listalbum = new ArrayList<>();
        albumAdapter = new AlbumAdapter((ArrayList<Album>) listalbum, getActivity());
        getData();
        rvAlbum.setAdapter(albumAdapter);
        rvAlbum.setLayoutManager(new GridLayoutManager(getActivity(), numberofColumns));
        return view;
    }

    private void getData() {


        ContentResolver cr = getActivity().getContentResolver();
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = cr.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ARTIST);
            int artMusic = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            //add songs to list
            do {
                int thisId = musicCursor.getInt(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisArt = musicCursor.getString(artMusic);
                listalbum.add(new Album(thisId, thisTitle, thisArtist, thisArt));
            }
            while (musicCursor.moveToNext());
        }
        albumAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_grid:
                rvAlbum.setLayoutManager(new GridLayoutManager(getActivity(), numberofColumns));
                break;
            case R.id.rv_album:
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvAlbum.setLayoutManager(layoutManager);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
