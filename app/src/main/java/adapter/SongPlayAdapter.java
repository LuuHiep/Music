package adapter;

import android.app.Activity;
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
import java.util.Collections;
import java.util.List;

import model.Song;
import per.OnCustomerListChangedListener;

public class SongPlayAdapter extends RecyclerView.Adapter<SongPlayAdapter.viewHolder> {
    private Context contextPlay;
    private ArrayList<Song> listSongPlay;
    private LayoutInflater layoutInflater;

    public SongPlayAdapter(Context contextPlay, ArrayList<Song> listSongPlay, LayoutInflater layoutInflater) {
        this.contextPlay = contextPlay;
        this.listSongPlay = listSongPlay;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_song, viewGroup, false);
        return new SongPlayAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {
        viewHolder.tvSongName.setText(listSongPlay.get(position).getNameSong());
        viewHolder.tvSongAritstName.setText(listSongPlay.get(position).getNameArtist());
        viewHolder.iconBeats.setImageResource(R.drawable.icon_beats);
    }

    @Override
    public int getItemCount() {
        return listSongPlay.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView iconBeats;
        private TextView tvSongName, tvSongAritstName;

        public viewHolder(@NonNull final View itemView) {
            super(itemView);

            iconBeats = (ImageView) itemView.findViewById(R.id.iv_icon_beats);
            tvSongName = (TextView) itemView.findViewById(R.id.tv_song_name);
            tvSongAritstName = (TextView) itemView.findViewById(R.id.tv_song_artist_name);
        }
    }
}
