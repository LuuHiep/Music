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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lau.music.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ArtistAdapter;
import model.Artist;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class FragmentArtist extends Fragment {
    private RecyclerView rvArtist;
    private List<Artist> listArtist;
    private ArtistAdapter artistAdapter;
    int numberofColumns = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        rvArtist = view.findViewById(R.id.rv_artist);
        listArtist = new ArrayList<>();
        artistAdapter = new ArtistAdapter((ArrayList<Artist>) listArtist, getActivity());
        setHasOptionsMenu(true);
        getData();
        rvArtist.setAdapter(artistAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvArtist.setLayoutManager(layoutManager);
        return view;
    }

    private void getData() {

        ContentResolver cr = getActivity().getContentResolver();
        Uri musicUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = cr.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.ARTIST);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists._ID);
            int numberSong = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            int numberAlbum = musicCursor.getColumnIndex
                    (MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
            //add songs to list
            do {
                int thisId = musicCursor.getInt(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisNumberSong = musicCursor.getString(numberSong);
                String thisNumberAlbum = musicCursor.getString(numberAlbum);
                listArtist.add(new Artist(thisId, thisTitle, thisNumberAlbum, thisNumberSong));
            }
            while (musicCursor.moveToNext());
        }
        artistAdapter.notifyDataSetChanged();

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvArtist.setLayoutManager(layoutManager);
                Log.d("menu artist", "onOptionsItemSelected: ");
                break;
            case R.id.item_grid:
                rvArtist.setLayoutManager(new GridLayoutManager(getActivity(), numberofColumns));
                Log.d("menu artist", "onOptionsItemSelected: ");
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
