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


    //UI
    @BindView(R.id.recyclerViewIngredientsRecipes)
    RecyclerView recyclerViewIngredientsRecipes;
    @BindView(R.id.recyclerViewStepRecipes)
    RecyclerView recyclerViewStepRecipes;

    RecipeIngredientAdapter mRecipeIngredientAdapter;
    RecipeStepAdapter mRecipeStepAdapter;

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
            initRecyclerViewIngredient();
            initRecyclerViewStep();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(HomeActivity.KEY_INTENT_RECIPE, mRecipe);
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialize the recyclerview for the ingredients and his adapter
     */
    private void initRecyclerViewIngredient() {
        recyclerViewIngredientsRecipes.setLayoutManager(new LinearLayoutManager(this));
        if (mRecipeIngredientAdapter == null) {
            mRecipeIngredientAdapter = new RecipeIngredientAdapter(this, new ArrayList<>(mRecipe.getIngredients()));
            recyclerViewIngredientsRecipes.setAdapter(mRecipeIngredientAdapter);
        } else {
            mRecipeIngredientAdapter.setListIngredient(new ArrayList<>(mRecipe.getIngredients()));
            mRecipeIngredientAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Initialize the recyclerview for the steps and his adapter
     */
    private void initRecyclerViewStep() {
        recyclerViewStepRecipes.setLayoutManager(new LinearLayoutManager(this));
        if (mRecipeStepAdapter == null) {
            mRecipeStepAdapter = new RecipeStepAdapter(this, new ArrayList<>(mRecipe.getSteps()), new RecipeStepAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Step item) {
                    Intent i = new Intent(mContext, StepActivity.class);
                    i.putExtra(KEY_INTENT_STEP, item);
                    i.putExtra(KEY_INTENT_RECIPE_NAME, mRecipe.getName());
                    i.putExtra(KEY_INTENT_STEP_LIST, new ArrayList<>(mRecipe.getSteps()));
                    startActivity(i);
                }
            });
            recyclerViewStepRecipes.setAdapter(mRecipeStepAdapter);
        } else {
            mRecipeStepAdapter.setListStep(new ArrayList<>(mRecipe.getSteps()));
            mRecipeStepAdapter.notifyDataSetChanged();
        }
    }


}
