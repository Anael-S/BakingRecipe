package anaels.com.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import anaels.com.bakingrecipe.adapter.IngredientAdapter;
import anaels.com.bakingrecipe.adapter.StepAdapter;
import anaels.com.bakingrecipe.api.model.Recipe;
import anaels.com.bakingrecipe.api.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Display the detail of a specific recipe
 */
public class RecipeFragment extends Fragment {

    Recipe mRecipe;
    Context mContext;
    ArrayList<Recipe> mRecipeList;

    public static final String KEY_INTENT_STEP = "keyIntentStep";
    public static final String KEY_INTENT_STEP_LIST = "keyIntentStepList";
    public static final String KEY_INTENT_RECIPE_NAME = "keyIntentRecipeName";
    public static final String KEY_INTENT_POSITION_INGREDIENT_LIST = "keyIntentPositionIngredientList";


    //UI
    @BindView(R.id.recyclerViewIngredientsRecipes)
    RecyclerView recyclerViewIngredientsRecipes;
    @BindView(R.id.recyclerViewStepRecipes)
    RecyclerView recyclerViewStepRecipes;

    IngredientAdapter mIngredientAdapter;
    StepAdapter mStepAdapter;

    int positionIngredientList = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);
        Log.i("//RM","onCreateView RecipeFragment called twice???? //RM TODO FIX THIS");
        mContext = getContext();

        Bundle currentBundle = null;
        if (getArguments() != null) {
            currentBundle = getArguments();
        }
        if (savedInstanceState != null) {
            currentBundle = savedInstanceState;
        }
        if (currentBundle != null) {
            mRecipe = currentBundle.getParcelable(HomeActivity.KEY_INTENT_RECIPE);
            mRecipeList = currentBundle.getParcelableArrayList(HomeActivity.KEY_INTENT_LIST_RECIPE);
            positionIngredientList = currentBundle.getInt(KEY_INTENT_POSITION_INGREDIENT_LIST);
        }

        //UI
        if (mRecipe != null) {
            initRecyclerViewIngredient();
            initRecyclerViewStep();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(HomeActivity.KEY_INTENT_RECIPE, mRecipe);
        outState.putParcelableArrayList(HomeActivity.KEY_INTENT_LIST_RECIPE, mRecipeList);
        if (recyclerViewIngredientsRecipes != null) {
            outState.putInt(KEY_INTENT_POSITION_INGREDIENT_LIST, positionIngredientList);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialize the recyclerview for the ingredients and his adapter
     */
    private void initRecyclerViewIngredient() {
        recyclerViewIngredientsRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mIngredientAdapter == null) {
            mIngredientAdapter = new IngredientAdapter(getActivity(), new ArrayList<>(mRecipe.getIngredients()));
            recyclerViewIngredientsRecipes.setAdapter(mIngredientAdapter);
        } else {
            mIngredientAdapter.setListIngredient(new ArrayList<>(mRecipe.getIngredients()));
            mIngredientAdapter.notifyDataSetChanged();
        }
        recyclerViewIngredientsRecipes.scrollToPosition(positionIngredientList);
        recyclerViewIngredientsRecipes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //We update the position of our scrollview
                LinearLayoutManager myLayoutManager = (LinearLayoutManager) recyclerViewIngredientsRecipes.getLayoutManager();
                positionIngredientList = myLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    /**
     * Initialize the recyclerview for the steps and his adapter
     */
    private void initRecyclerViewStep() {
        recyclerViewStepRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mStepAdapter == null) {
            mStepAdapter = new StepAdapter(getActivity(), new ArrayList<>(mRecipe.getSteps()), new StepAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Step item) {
                    if (getActivity().getResources().getBoolean(R.bool.isTablet)) {
                        final StepFragment lFragment = new StepFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(RecipeActivity.KEY_INTENT_STEP, item);
                        bundle.putParcelableArrayList(RecipeActivity.KEY_INTENT_STEP_LIST, new ArrayList<Parcelable>(mRecipe.getSteps()));
                        lFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentStep, lFragment).commit();
                    } else {
                        Intent i = new Intent(mContext, StepActivity.class);
                        i.putExtra(KEY_INTENT_STEP, item);
                        i.putExtra(KEY_INTENT_RECIPE_NAME, mRecipe.getName());
                        i.putExtra(KEY_INTENT_STEP_LIST, new ArrayList<>(mRecipe.getSteps()));
                        startActivity(i);
                    }
                }
            });
            recyclerViewStepRecipes.setAdapter(mStepAdapter);
        } else {
            mStepAdapter.setListStep(new ArrayList<>(mRecipe.getSteps()));
            mStepAdapter.notifyDataSetChanged();
        }
    }

    public int getPositionIngredientList() {
        return positionIngredientList;
    }
}
