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

import org.junit.Assert;
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

    }

    // Check if detail TextView Name contains the Neighbour name
    @Test
    public void detailActivity_TextViewNameTest() {
        final int position = 0;
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();

        ViewInteraction recyclerList = onView( allOf(withId(R.id.list_neighbours),isDisplayed()));
        recyclerList.perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        ViewInteraction detailName = onView( allOf(withId(R.id.detail_Tv_Name), isDisplayed()));
        detailName.check(matches(withText(mNeighbours.get(position).getName())));
    }

    // Check if click on detail Fab Favorite add the nieghbour
    @Test
    public void detailActivity_addToFavoriteTest() {
        final int position = 0;
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();

        ViewInteraction recyclerList = onView( allOf(withId(R.id.list_neighbours),isDisplayed()));
        recyclerList.perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        ViewInteraction favButton = onView( allOf(withId(R.id.detail_Fab_Favorite),isDisplayed()));
        favButton.perform(click());

        ViewInteraction backArrow = onView( allOf(withId(R.id.detail_Iv_BackArrow),isDisplayed()));
        backArrow.perform(click());

        // tabItem doesn't work ;-)
        onView( ViewMatchers.withText("Favorites")).perform(ViewActions.click());
        //Check the neighbour favorite neighbour status in favorites neighbours list
        Assert.assertTrue(mNeighbours.get(position).getIsFavorite().equals(true));

    }
}
