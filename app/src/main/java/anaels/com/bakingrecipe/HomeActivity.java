package anaels.com.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import anaels.com.bakingrecipe.adapter.RecipeAdapter;
import anaels.com.bakingrecipe.api.NetworkService;
import anaels.com.bakingrecipe.api.model.Recipe;
import anaels.com.bakingrecipe.helper.AssetsHelper;
import anaels.com.bakingrecipe.helper.InternetConnectionHelper;
import anaels.com.bakingrecipe.helper.SerializeHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity, display the list of recipes
 */
public class HomeActivity extends AppCompatActivity {

    ArrayList<Recipe> mRecipeList;
    RecipeAdapter mRecipeAdapter;
    Context mContext;

    public static final String KEY_INTENT_RECIPE = "keyIntentRecipe";
    public static final String KEY_INTENT_LIST_RECIPE = "keyIntentRecipeList";


    @BindView(R.id.recyclerViewRecipes)
    RecyclerView recyclerViewRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext = this;

        //If we already got our recipe lists, we recover them
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mRecipeList = savedInstanceState.getParcelableArrayList(KEY_INTENT_LIST_RECIPE);
            initRecyclerView();
        } else {
            loadRecipes();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_INTENT_LIST_RECIPE, mRecipeList);
        super.onSaveInstanceState(outState);
    }


    /**
     * Load the recipes fron internet if we have an internet connection
     * Load them fron a local json file otherwise
     */
    private void loadRecipes() {
        //If we have an internet connection
        if (InternetConnectionHelper.isNetworkAvailable(this)) {
            //We get our recipe from the network
            NetworkService.getRecipes(this, new NetworkService.OnRecipeRecovered() {
                @Override
                public void onRecipeRecovered(ArrayList<Recipe> recipeList) {
                    mRecipeList = recipeList;
                    initRecyclerView();
                }
            }, new NetworkService.OnError() {
                @Override
                public void onError() {
                    loadRecipeFromLocalFile();
                }
            });
        } else {
            //otherwise we fetch them locally
            loadRecipeFromLocalFile();
        }
    }

    /**
     * Load the recipe from the local json file
     */
    private void loadRecipeFromLocalFile() {
        String jsonRecipes = AssetsHelper.readJsonFileFromAssets(AssetsHelper.PATH_FILE_RECIPES, mContext);
        Type returnType = new TypeToken<ArrayList<Recipe>>() {
        }.getType();
        ArrayList<Recipe> recipeList = SerializeHelper.deserializeJson(jsonRecipes, returnType);
        if (recipeList != null && !recipeList.isEmpty()) {
            mRecipeList = recipeList;
            initRecyclerView();
        } else {
            Toast.makeText(mContext, R.string.error_fetch_recipe, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initialize the recyclerview and his adapter
     */
    private void initRecyclerView() {
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        if (mRecipeAdapter == null) {
            mRecipeAdapter = new RecipeAdapter(this, mRecipeList, new RecipeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Recipe item) {
                    Intent i = new Intent(mContext, RecipeActivity.class);
                    i.putExtra(KEY_INTENT_RECIPE, item);
                    startActivity(i);
                }
            });
            recyclerViewRecipes.setAdapter(mRecipeAdapter);
        } else {
            //We update the adapter
            mRecipeAdapter.setListRecipe(mRecipeList);
            mRecipeAdapter.notifyDataSetChanged();
        }
    }
}
