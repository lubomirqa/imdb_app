package logic;

import data.Database;

import java.util.ArrayList;

public class MovieType {
  private String name;

  public MovieType(String s){
    name = s;
  }

  public static ArrayList<MovieType> getAllMovieTypes(){
    return Database.getAllMovieTypes();
  }

  public String getName() {
    return name;
  }
}
