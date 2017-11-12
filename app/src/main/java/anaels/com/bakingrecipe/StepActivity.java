package anaels.com.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import anaels.com.bakingrecipe.adapter.DetailStepRecipePagerAdapter;
import anaels.com.bakingrecipe.animations.ReaderViewPagerTransformer;
import anaels.com.bakingrecipe.api.model.Recipe;
import anaels.com.bakingrecipe.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Display the detail of a specific recipe
 */
public class StepActivity extends AppCompatActivity {

    Step mStep;
    ArrayList<Step> mStepList;
    String mRecipeName;
    Context mContext;

    DetailStepRecipePagerAdapter mPagerAdapter;

    //Footer
    @BindView(R.id.layoutPreviousStep)
    CardView layoutPreviousStep;
    @BindView(R.id.layoutNextStep)
    CardView layoutNextStep;
    @BindView(R.id.pagerDetailStep)
    ViewPager pagerDetailStep;
    @BindView(R.id.tabDots)
    TabLayout tabDots;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        mContext = this;

        mStep = getIntent().getParcelableExtra(RecipeActivity.KEY_INTENT_STEP);
        mRecipeName = getIntent().getStringExtra(RecipeActivity.KEY_INTENT_RECIPE_NAME);
        mStepList = getIntent().getParcelableArrayListExtra(RecipeActivity.KEY_INTENT_STEP_LIST);

        if (savedInstanceState != null && mStep == null) {
            mStep = savedInstanceState.getParcelable(RecipeActivity.KEY_INTENT_STEP);
        }

        //UI
        initUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RecipeActivity.KEY_INTENT_STEP, mStep);
        super.onSaveInstanceState(outState);
    }

    private void initUI() {
        //ActionBar
        updateActionBar();

        //Footer
        updateFooter();

        layoutPreviousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPos = mStep.getId() - 1;
                updateStep(newPos);
            }
        });
        layoutNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPos = mStep.getId() + 1;
                updateStep(newPos);
            }
        });

        //Detail step
        mPagerAdapter = new DetailStepRecipePagerAdapter(getSupportFragmentManager(), mStepList);
        pagerDetailStep.setAdapter(mPagerAdapter);
        pagerDetailStep.setPageTransformer(false, new ReaderViewPagerTransformer(ReaderViewPagerTransformer.TransformType.FLOW));
        tabDots.setupWithViewPager(pagerDetailStep, true);

    }

    private void updateFooter() {
        if (mStep.getId() == 0) {
            layoutPreviousStep.setVisibility(View.INVISIBLE);
        } else {
            layoutPreviousStep.setVisibility(View.VISIBLE);
        }
        if (mStep.getId() == mStepList.size() - 1) {
            layoutNextStep.setVisibility(View.INVISIBLE);
        } else {
            layoutNextStep.setVisibility(View.VISIBLE);
        }
    }

    private void updateStep(int newPosStep) {
        pagerDetailStep.setCurrentItem(newPosStep);
        mStep = mStepList.get(newPosStep);
        updateFooter();
        updateActionBar();
    }

    private void updateActionBar(){
        if (mStep != null) {
            if (getSupportActionBar() != null) {
                if (mStep.getId() != 0) {
                    getSupportActionBar().setTitle(getString(R.string.step_separator, mRecipeName, mStep.getId()));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.recipe_name_separator, mRecipeName));
                }
            }
        }
    }
}
