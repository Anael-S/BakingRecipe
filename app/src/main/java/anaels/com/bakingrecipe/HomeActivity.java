package anaels.com.bakingrecipe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity {

    ArrayList<Recipe> mRecipeList;
    Context mContext;

    private final String PATH_FILE_RECIPES = "recipe.json";

    //    @BindView(R.id.user)
//    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        } else {
            Toast.makeText(mContext, R.string.error_fetch_recipe, Toast.LENGTH_SHORT).show();
        }
    }
}
