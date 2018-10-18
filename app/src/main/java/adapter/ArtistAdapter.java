package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lau.music.R;

import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.Artist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.viewHolder> {
    private ArrayList<Artist> artistArrayList;
    private Context contextArtist;

    public ArtistAdapter(ArrayList<Artist> artistArrayList, Context contextArtist) {
        this.artistArrayList = artistArrayList;
        this.contextArtist = contextArtist;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_artist, viewGroup, false);
        return new ArtistAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {
        viewHolder.tvArtistName.setText(artistArrayList.get(position).getNameArtist());
        viewHolder.tvAlbumNumber.setText(artistArrayList.get(position).getNumberAlbum());
        viewHolder.tvSongNumber.setText(artistArrayList.get(position).getNumberSong());
    }

    @Override
    public int getItemCount() {
        return artistArrayList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView ivArtistImage;
        private TextView tvArtistName, tvAlbumNumber, tvSongNumber;

        public viewHolder(@NonNull final View itemView) {
            super(itemView);

            ivArtistImage = (ImageView) itemView.findViewById(R.id.iv_icon_album);
            tvArtistName = (TextView) itemView.findViewById(R.id.tv_artist_name);
            tvSongNumber = (TextView) itemView.findViewById(R.id.tv_song_number);
            tvAlbumNumber = (TextView) itemView.findViewById(R.id.tv_album_number);
        }
    }

}
