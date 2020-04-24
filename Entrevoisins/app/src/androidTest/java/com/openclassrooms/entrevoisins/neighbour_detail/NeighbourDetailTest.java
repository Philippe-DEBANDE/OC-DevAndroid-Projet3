package com.openclassrooms.entrevoisins.neighbour_detail;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class NeighbourDetailTest {
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    // Check if click on a neighbour show the detail neighbour

    @Test
    public void detailActivity_LaunchTest() {
        final int position = 0;
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();

        ViewInteraction recyclerList = onView( allOf(withId(R.id.list_neighbours),isDisplayed()));
        recyclerList.perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        // If click is not perform the test fail
        onView(withId(R.id.detail_Tv_Name)).check(matches(isDisplayed()));

    }

    // Check if detail TextView Name contains the Neighbour name
    @Test
    public void detailActivity_TextViewNameTest() {
        final int position = 0;
        // If name empty or changing the name the test fail
        final String nb_Name = "Caroline";
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();

        ViewInteraction recyclerList = onView( allOf(withId(R.id.list_neighbours),isDisplayed()));
        recyclerList.perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        ViewInteraction detailName = onView( allOf(withId(R.id.detail_Tv_Name), isDisplayed()));
        detailName.check(matches(withText(nb_Name)));
    }

    // Check if click on detail Fab Favorite add the neighbour
    @Test
    public void detailActivity_addToFavoriteTest() throws InterruptedException {
        int position = 1;
        final String nb_Name = "Jack";
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();

        ViewInteraction recyclerList = onView( allOf(withId(R.id.list_neighbours),isDisplayed()));
        recyclerList.perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        ViewInteraction favButton = onView( allOf(withId(R.id.detail_Fab_Favorite),isDisplayed()));
        favButton.perform(click());

        ViewInteraction backArrow = onView( allOf(withId(R.id.detail_Iv_BackArrow),isDisplayed()));
        backArrow.perform(click());

        // tabItem doesn't work ;-)
        //Wait to see the Neighbours list ;-)
        Thread.sleep (2000);
        onView( ViewMatchers.withText("Favorites")).perform(ViewActions.click());

        //Check the neighbour in favorites neighbours list = "Jack"
        position = 0;
        recyclerList.perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        ViewInteraction detailName = onView(allOf(withId(R.id.detail_Tv_Name)));
        detailName.check(matches(withText(nb_Name)));

    }
}
