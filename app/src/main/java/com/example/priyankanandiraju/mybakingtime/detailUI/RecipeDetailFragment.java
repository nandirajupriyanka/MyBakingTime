package com.example.priyankanandiraju.mybakingtime.detailUI;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyankanandiraju.mybakingtime.R;
import com.example.priyankanandiraju.mybakingtime.recipe.Recipe;
import com.example.priyankanandiraju.mybakingtime.recipe.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String STEP_POSITION = "STEP_POSITION";
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
    private Recipe mItem;
    private Step mStep;
    @Nullable
    private SimpleExoPlayer exoPlayer;
    @BindView(R.id.tv_step_description)
    TextView tvDescription;
    @BindView(R.id.playerView)
    SimpleExoPlayerView exoPlayerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args.containsKey(ARG_ITEM_ID)) {
            mItem = args.getParcelable(ARG_ITEM_ID);
            if (args.containsKey(STEP_POSITION)) {
                int position = args.getInt(STEP_POSITION);
                mStep = mItem.getStepsList().get(position);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initializePlayer() {
        if (mStep != null) {
            tvDescription.setText(mStep.getDescription());
            if (mStep.getVideoUrl() != null || !mStep.getVideoUrl().equals("")) {
                if (exoPlayer == null) {
                    TrackSelector trackSelector = new DefaultTrackSelector();
                    LoadControl loadControl = new DefaultLoadControl();
                    exoPlayer = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector, loadControl);
                    exoPlayerView.setPlayer(exoPlayer);
                    // Prepare MediaSource.
                    String userAgent = Util.getUserAgent(this.getActivity(), "MyBakingTime");
                    Uri mediaUri = Uri.parse(mStep.getVideoUrl());
                    MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                            this.getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                    exoPlayer.prepare(mediaSource);
                    exoPlayer.setPlayWhenReady(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        Log.v(TAG, "onResume()");
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause()");
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop()");
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView()");
        super.onDestroyView();
        releasePlayer();
    }
}
