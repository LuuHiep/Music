package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lau.music.R;

import java.util.ArrayList;

import activity.PlayActivity;
import model.Song;

public class SongAdapter extends RecyclerView.Adapter <SongAdapter.viewHolder> {

    public static final String SONG_PATH = "song_path";
    public static final String LIST_SONG = "list_song";
    public static final String SONG_POS = "position";

    private ArrayList<Song> songArrayList;
    private Context contextSong;

    public SongAdapter(ArrayList<Song> songArrayList, Context contextSong) {
        this.songArrayList = songArrayList;
        this.contextSong = contextSong;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_song, viewGroup,false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, final int position) {
        viewHolder.tvSongName.setText(songArrayList.get(position).getTitle());
        viewHolder.tvSongAritstName.setText(songArrayList.get(position).getArtist());
        viewHolder.iconBeats.setImageResource((songArrayList.get(position).getImageSong()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSongPlay = new Intent(contextSong, PlayActivity.class);
                intentSongPlay.putExtra("position", position);
                Log.d("position_0", "" + position);
                contextSong.startActivity(intentSongPlay);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

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
