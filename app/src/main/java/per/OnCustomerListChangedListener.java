package per;

import java.util.List;

import model.Song;

public interface OnCustomerListChangedListener {
    void onNoteListChanged(List<Song> songsList);
}
