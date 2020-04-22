package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addNeighbourWithSuccess() {
        Neighbour neighbourToAdd = service.getNeighbours().get(0);
        service.createNeighbour(neighbourToAdd);
        assertTrue(service.getNeighbours().contains(neighbourToAdd));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess () {
        List<Neighbour> favorite_neighbours = service.getFavorite_Neighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.FAVORITE_NEIGHBOURS;
        assertThat(favorite_neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));

    }

    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        Neighbour favoriteNeighbourToDelete = service.getNeighbours().get(0);
        service.createFavoriteNeighbour(favoriteNeighbourToDelete);
        favoriteNeighbourToDelete.setIsFavorite();
        assertEquals(false, favoriteNeighbourToDelete.getIsFavorite());
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        Neighbour favoriteNeighbourToAdd = service.getNeighbours().get(0);
        service.createFavoriteNeighbour(favoriteNeighbourToAdd);
        favoriteNeighbourToAdd.setIsFavorite();
        assertEquals(true, favoriteNeighbourToAdd.getIsFavorite());
    }

}
