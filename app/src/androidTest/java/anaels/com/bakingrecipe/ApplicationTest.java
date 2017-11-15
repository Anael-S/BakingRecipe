package anaels.com.bakingrecipe;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void isRecyclerViewRecipeDisplayed() {
        //check if the recyclerview containing all the recipe is displayed
        onView(withId(R.id.recyclerViewRecipes)).check(matches(isDisplayed()));
    }

    @Test
    public void isDetailRecipeDisplayed() {
        //click on the third recipe
        onView(withId(R.id.recyclerViewRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        //check if the detail recipe view is displayed
        onView(withId(R.id.recipeGlobalLayout)).check(matches(isDisplayed()));

        //click on the first step
        onView(withId(R.id.recyclerViewStepRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}