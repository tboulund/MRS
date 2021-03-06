/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movierecsys.be.Movie;
import movierecsys.bll.MRSLogicFacade;
import movierecsys.bll.MRSManager;
import movierecsys.bll.exception.MrsBllException;

/**
 * @author pgn
 */
public class MovieModel {

    private ObservableList<Movie> movies;
    private MRSLogicFacade logiclayer;

    public MovieModel() throws MrsBllException {
        movies = FXCollections.observableArrayList();
        logiclayer = new MRSManager();
        movies.addAll(logiclayer.getAllMovies());
    }

    /**
     * Gets a reference to the observable list of Movies.
     *
     * @return List of movies.
     */
    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public void createMovie(int year, String title) throws MrsBllException {
        Movie movie = logiclayer.createMovie(year, title);
        movies.add(movie);
    }

    public void deleteMovie(Movie movie) throws MrsBllException {
        logiclayer.deleteMovie(movie);
        movies.remove(movie);
    }

    public void search(String query) throws MrsBllException {
        if (query != null) {
            List<Movie> searchedMovies = logiclayer.searchMovies(query);
            movies.clear();
            movies.addAll(searchedMovies);
        }
    }

    public void updateMovie(Movie selectedMovie) throws MrsBllException {
        logiclayer.updateMovie(selectedMovie);
        if (movies.remove(selectedMovie)) {
            movies.add(selectedMovie);
            movies.sort(Comparator.comparingInt(Movie::getId));
        }
    }

}
