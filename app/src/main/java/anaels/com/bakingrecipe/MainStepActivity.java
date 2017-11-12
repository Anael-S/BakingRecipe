package anaels.com.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import anaels.com.bakingrecipe.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Display the detail of a specific recipe
 */
public class MainStepActivity extends AppCompatActivity {

    Step mStep;
    String mRecipeName;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        mStep = getIntent().getParcelableExtra(RecipeActivity.KEY_INTENT_STEP);
        mRecipeName = getIntent().getStringExtra(RecipeActivity.KEY_INTENT_RECIPE_NAME);

        if (savedInstanceState != null && mStep == null) {
            mStep = savedInstanceState.getParcelable(RecipeActivity.KEY_INTENT_STEP);
            mRecipeName = savedInstanceState.getParcelable(RecipeActivity.KEY_INTENT_RECIPE_NAME);
        }

        //UI
        if (mStep != null) {
            if (getSupportActionBar() != null) {
                if (mStep.getId() != 0) {
                    getSupportActionBar().setTitle(getString(R.string.step_separator,mRecipeName,mStep.getId()));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.recipe_name_separator,mRecipeName));
                }
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RecipeActivity.KEY_INTENT_STEP, mStep);
        super.onSaveInstanceState(outState);
    }


}
