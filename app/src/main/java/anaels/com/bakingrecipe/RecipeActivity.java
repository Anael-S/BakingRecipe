package anaels.com.bakingrecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
        outState.putParcelable(HomeActivity.KEY_INTENT_LIST_RECIPE, mRecipe);
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
            mRecipeStepAdapter = new RecipeStepAdapter(this, new ArrayList<>(mRecipe.getSteps()));
            recyclerViewStepRecipes.setAdapter(mRecipeStepAdapter);
        } else {
            mRecipeStepAdapter.setListStep(new ArrayList<>(mRecipe.getSteps()));
            mRecipeStepAdapter.notifyDataSetChanged();
        }
    }


}
