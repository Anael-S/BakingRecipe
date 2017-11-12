package anaels.com.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import anaels.com.bakingrecipe.adapter.RecipeIngredientAdapter;
import anaels.com.bakingrecipe.adapter.RecipeStepAdapter;
import anaels.com.bakingrecipe.api.model.Recipe;
import anaels.com.bakingrecipe.api.model.Step;
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
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentRecipe, fragmentRecipe, fragmentRecipe.getClass().getSimpleName()).addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentRecipe, fragmentRecipe).commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(HomeActivity.KEY_INTENT_RECIPE, mRecipe);
        super.onSaveInstanceState(outState);
    }


}
