package anaels.com.bakingrecipe.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import anaels.com.bakingrecipe.DetailStepRecipeFragment;
import anaels.com.bakingrecipe.RecipeActivity;
import anaels.com.bakingrecipe.api.model.Step;

/**
 * A simple pager adapter that represents our work order step
 */
public class DetailStepRecipePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Step> mStepList;
    int maxPos;

    public DetailStepRecipePagerAdapter(FragmentManager fm, ArrayList<Step> stepList) {
        super(fm);
        mStepList = stepList;
        maxPos = mStepList.size()-1;
    }

    @Override
    public Fragment getItem(int position) {
        //We pass the current step into the fragment in order to instantiate the right view
        final DetailStepRecipeFragment lFragment = new DetailStepRecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeActivity.KEY_INTENT_STEP, mStepList.get(position));
        bundle.putParcelableArrayList(RecipeActivity.KEY_INTENT_STEP_LIST, mStepList);
        lFragment.setArguments(bundle);
        return lFragment;
    }

    @Override
    public int getCount() {
        return mStepList.size();
    }

}