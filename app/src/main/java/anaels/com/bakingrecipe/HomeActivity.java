package anaels.com.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import anaels.com.bakingrecipe.api.NetworkService;
import anaels.com.bakingrecipe.api.model.Recipe;
import anaels.com.bakingrecipe.helper.AssetsHelper;
import anaels.com.bakingrecipe.helper.InternetConnectionHelper;
import anaels.com.bakingrecipe.helper.SerializeHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Recipe> mRecipeList;
    RecipeAdapter mRecipeAdapter;
    Context mContext;

    private final String PATH_FILE_RECIPES = "recipe.json";

    private final String KEY_INTENT_RECIPE = "keyIntentRecipe";

    @BindView(R.id.recyclerViewRecipes)
    RecyclerView recyclerViewRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext = this;

        loadRecipes();
    }

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

    private void loadRecipeFromLocalFile() {
        String jsonRecipes = AssetsHelper.readJsonFileFromAssets(PATH_FILE_RECIPES, mContext);
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


    private void initRecyclerView(){
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));
        if (mRecipeAdapter == null) {
            mRecipeAdapter = new RecipeAdapter(this, mRecipeList, new RecipeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Recipe item) {
                    //TODO
//                    Intent i = new Intent(mContext, RecipeActivity.class);
//                    i.putExtra(KEY_INTENT_RECIPE, item);
//                    startActivity(i);
                }
            });
            recyclerViewRecipes.setAdapter(mRecipeAdapter);
        }else {
            //We update the adapter
            //TODO
        }

    }
}
