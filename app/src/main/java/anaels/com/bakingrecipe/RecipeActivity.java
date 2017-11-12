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
    @BindView(R.id.titleRecipeTextView)
    TextView titleRecipeTextView;
    @BindView(R.id.recyclerViewIngredientsRecipes)
    RecyclerView recyclerViewIngredientsRecipes;
    @BindView(R.id.recyclerViewStepRecipes)
    RecyclerView recyclerViewStepRecipes;

    IngredientAdapter mIngredientAdapter;

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
        titleRecipeTextView.setText(mRecipe.getName());
        initRecyclerViewIngredient();
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
        if (mIngredientAdapter == null) {
            mIngredientAdapter = new IngredientAdapter(this, new ArrayList<>(mRecipe.getIngredients()));
            recyclerViewIngredientsRecipes.setAdapter(mIngredientAdapter);
        } else {
            //We update the adapter
            mIngredientAdapter.setListIngredient(new ArrayList<>(mRecipe.getIngredients()));
            mIngredientAdapter.notifyDataSetChanged();
        }
    }


}
