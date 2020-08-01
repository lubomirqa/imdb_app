package logic;

import data.Database;

import java.util.ArrayList;

public class Movie {
  private String primaryTitle;
  private Integer startYear;
  private Float averageRating;
  private Integer numVotes;

  public Movie(String pt, Integer sy, Float ar, Integer nv){
    primaryTitle = pt;
    startYear = sy;
    averageRating = ar;
    numVotes = nv;
  }

  public static ArrayList<Movie> findMovies(Integer minMovies, String titleType, String genre){
    return Database.findMovies(minMovies, titleType, genre);
  }

  public String getPrimaryTitle() {
    return primaryTitle;
  }

  public void setPrimaryTitle(String primaryTitle) {
    this.primaryTitle = primaryTitle;
  }

  public Integer getStartYear() {
    return startYear;
  }

  public void setStartYear(Integer startYear) {
    this.startYear = startYear;
  }

  public Float getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(Float averageRating) {
    this.averageRating = averageRating;
  }

  public Integer getNumVotes() {
    return numVotes;
  }

  public void setNumVotes(Integer numVotes) {
    this.numVotes = numVotes;
  }
}
