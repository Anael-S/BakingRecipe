package anaels.com.bakingrecipe;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import java.util.ArrayList;

import anaels.com.bakingrecipe.api.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Display the detail of a specific recipe
 */
public class RecipeActivity extends AppCompatActivity {

    Recipe mRecipe;
    Context mContext;
    ArrayList<Recipe> mRecipeList;

    public static final String KEY_INTENT_STEP = "keyIntentStep";
    public static final String KEY_INTENT_STEP_LIST = "keyIntentStepList";
    public static final String KEY_INTENT_RECIPE_NAME = "keyIntentRecipeName";

    @Nullable
    @BindView(R.id.fragmentStep)
    FrameLayout fragmentStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        mContext = this;

        mRecipe = getIntent().getParcelableExtra(HomeActivity.KEY_INTENT_RECIPE);
        mRecipeList = getIntent().getParcelableArrayListExtra(HomeActivity.KEY_INTENT_LIST_RECIPE);

        if (savedInstanceState != null && mRecipe == null) {
            mRecipe = savedInstanceState.getParcelable(HomeActivity.KEY_INTENT_RECIPE);
        }

        //UI
        if (mRecipe != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mRecipe.getName());
            }
        }

        RecipeFragment fragmentRecipe = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(HomeActivity.KEY_INTENT_RECIPE, mRecipe);
        bundle.putParcelableArrayList(HomeActivity.KEY_INTENT_LIST_RECIPE, mRecipeList);
        fragmentRecipe.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentRecipe, fragmentRecipe).commit();

        //If we're on a tablet
        if(fragmentStep != null){
            StepFragment fragmentStep = new StepFragment();
            bundle = new Bundle();
            bundle.putParcelable(RecipeActivity.KEY_INTENT_STEP, mRecipe.getSteps().get(0));
            bundle.putParcelableArrayList(RecipeActivity.KEY_INTENT_STEP_LIST, new ArrayList<Parcelable>(mRecipe.getSteps()));
            fragmentStep.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentStep, fragmentStep).commit();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(HomeActivity.KEY_INTENT_RECIPE, mRecipe);
        super.onSaveInstanceState(outState);
    }


}
