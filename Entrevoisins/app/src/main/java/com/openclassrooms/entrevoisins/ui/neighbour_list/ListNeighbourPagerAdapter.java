package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param  position
     * @return position
     */
    @Override
        public NeighbourFragment getItem(int position) {
        boolean filteredList = false;
        switch (position) {
            case 0: {
                filteredList=false;
                break;
            }
            case 1: {
                filteredList=true;
                break;
          }
        }
        return NeighbourFragment.newInstance(filteredList);
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        // Return the value about the number of TabItem
        return 2;
    }
}