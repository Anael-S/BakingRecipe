package anaels.com.bakingrecipe;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import java.util.ArrayList;

import anaels.com.bakingrecipe.api.model.Recipe;
import anaels.com.bakingrecipe.api.model.Step;
import anaels.com.bakingrecipe.helper.StepHelper;
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

    RecipeFragment fragmentRecipe;
    int positionIngredientList;
    int positionStepList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        mContext = this;
        positionIngredientList = 0;
        positionStepList= 0;

        mRecipe = getIntent().getParcelableExtra(HomeActivity.KEY_INTENT_RECIPE);
        mRecipeList = getIntent().getParcelableArrayListExtra(HomeActivity.KEY_INTENT_LIST_RECIPE);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(HomeActivity.KEY_INTENT_RECIPE);
            positionIngredientList =  savedInstanceState.getInt(RecipeFragment.KEY_INTENT_POSITION_INGREDIENT_LIST);
            positionStepList =  savedInstanceState.getInt(RecipeFragment.KEY_INTENT_POSITION_STEP_LIST);
        }

        //UI
        if (mRecipe != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mRecipe.getName());
            }
        }

        fragmentRecipe = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(HomeActivity.KEY_INTENT_RECIPE, mRecipe);
        bundle.putParcelableArrayList(HomeActivity.KEY_INTENT_LIST_RECIPE, mRecipeList);
        bundle.putInt(RecipeFragment.KEY_INTENT_POSITION_INGREDIENT_LIST, positionIngredientList);
        bundle.putInt(RecipeFragment.KEY_INTENT_POSITION_STEP_LIST, positionStepList);
        fragmentRecipe.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentRecipe, fragmentRecipe).commit();

        int lastSelectedStep = StepHelper.getSelectStepPosition(new ArrayList<>(mRecipe.getSteps()));

        //If we're on a tablet
        if (fragmentStep != null) {
            //We select the first step
            mRecipe.getSteps().get(lastSelectedStep).setSelected(true);
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
        outState.putInt(RecipeFragment.KEY_INTENT_POSITION_INGREDIENT_LIST, fragmentRecipe.getPositionIngredientList());
        outState.putInt(RecipeFragment.KEY_INTENT_POSITION_STEP_LIST, fragmentRecipe.getPositionStepList());
        super.onSaveInstanceState(outState);
    }


}
