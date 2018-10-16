package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragment.FragmentAlbum;
import fragment.FragmentArtist;
import fragment.FragmentSong;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = new FragmentSong();
            default:
                break;
            case 1:
                fragment = new FragmentAlbum();
                break;
            case 2:
                fragment = new FragmentArtist();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="Song";
                break;
            case 1:
                title="Album";
                break;
            case 2:
                title="Artist";
                break;
        }

        return title;
    }
}
