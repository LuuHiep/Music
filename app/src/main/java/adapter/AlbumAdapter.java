package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.viewHolder> {

    private ArrayList<Album> albumArrayList;
    private Context contextAlbum;

    public AlbumAdapter(ArrayList<Album> albumArrayList, Context contextAlbum) {
        this.albumArrayList = albumArrayList;
        this.contextAlbum = contextAlbum;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_album, viewGroup,false);
        return new AlbumAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int position) {

        viewHolder.tvNameAlbum.setText(albumArrayList.get(position).getNameAlbums());
        viewHolder.tvAlbumAritstName.setText(albumArrayList.get(position).getNameAuthor());
        Bitmap bitmap = BitmapFactory.decodeFile(albumArrayList.get(position).getImageAlbum());
        if (bitmap != null) {
            viewHolder.imageAlbum.setImageBitmap(bitmap);
        } else {
            viewHolder.imageAlbum.setImageResource(R.drawable.icon_album);
        }
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView imageAlbum;
        TextView tvNameAlbum, tvAlbumAritstName;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);

            imageAlbum = (ImageView) itemView.findViewById(R.id.iv_icon_album);
            tvNameAlbum = (TextView) itemView.findViewById(R.id.tv_album_name);
            tvAlbumAritstName = (TextView) itemView.findViewById(R.id.tv_album_artist_name);
        }
    }
}
