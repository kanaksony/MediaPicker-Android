package org.wordpress.mediapickersample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ActionBar;
import android.os.Bundle;

import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.volley.toolbox.ImageLoader;

import org.wordpress.mediapicker.MediaItem;
import org.wordpress.mediapicker.MediaPickerFragment;
import org.wordpress.mediapicker.source.MediaSource;
import org.wordpress.mediapicker.source.MediaSourceDeviceImages;
import org.wordpress.mediapicker.source.MediaSourceDeviceVideos;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends Activity
                            implements MediaPickerFragment.OnMediaSelected {
    private static final String TAB_TITLE_IMAGES = "Images";
    private static final String TAB_TITLE_VIDEOS = "Videos";

    private MediaPickerAdapter mMediaPickerAdapter;
    private SlidingTabLayout   mTabLayout;
    private ViewPager          mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeContentView();
    }

    private void initializeContentView() {
        setContentView(R.layout.activity_media_picker_sample);

        mMediaPickerAdapter = new MediaPickerAdapter(getFragmentManager());

        mTabLayout = (SlidingTabLayout) findViewById(R.id.media_picker_tabs);
        mViewPager = (ViewPager) findViewById(R.id.media_picker_pager);

        if (mViewPager != null) {
            initializeTabs();
            mViewPager.setAdapter(mMediaPickerAdapter);

            if (mTabLayout != null) {
                mTabLayout.setViewPager(mViewPager);
            }
        }

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    /**
     * Helper method; adds tabs to the ViewPager.
     */
    private void initializeTabs() {
        ArrayList<MediaSource> imageSources = new ArrayList<>();
        imageSources.add(new MediaSourceDeviceImages(getContentResolver()));
        mMediaPickerAdapter.addTab(imageSources, TAB_TITLE_IMAGES);

        ArrayList<MediaSource> videoSources = new ArrayList<>();
        videoSources.add(new MediaSourceDeviceVideos(getContentResolver()));
        mMediaPickerAdapter.addTab(videoSources, TAB_TITLE_VIDEOS);
    }

    /*
        OnMediaSelected interface
     */

    @Override
    public void onMediaSelectionStarted() {
    }

    @Override
    public void onMediaSelected(MediaItem mediaContent, boolean selected) {
    }

    @Override
    public void onMediaSelectionConfirmed(ArrayList<MediaItem> mediaContent) {
    }

    @Override
    public void onMediaSelectionCancelled() {
    }

    @Override
    public void onGalleryCreated(ArrayList<MediaItem> mediaContent) {
    }

    @Override
    public ImageLoader.ImageCache getImageCache() {
        return null;
    }

    /**
     * Shows {@link org.wordpress.mediapicker.MediaPickerFragment}'s in a tabbed layout.
     */
    public class MediaPickerAdapter extends FragmentPagerAdapter {
        private class MediaPicker {
            public String pickerTitle;
            public ArrayList<MediaSource> mediaSources;

            public MediaPicker(String name, ArrayList<MediaSource> sources) {
                pickerTitle = name;
                mediaSources = sources;
            }
        }

        private List<MediaPicker> mMediaPickers;

        private MediaPickerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            mMediaPickers = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            if (position < mMediaPickers.size()) {
                MediaPicker mediaPicker = mMediaPickers.get(position);
                MediaPickerFragment fragment = new MediaPickerFragment();
                fragment.setMediaSources(mediaPicker.mediaSources);

                return fragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return mMediaPickers.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mMediaPickers.get(position).pickerTitle;
        }

        public void addTab(ArrayList<MediaSource> mediaSources, String tabName) {
            mMediaPickers.add(new MediaPicker(tabName, mediaSources));
        }
    }
}
