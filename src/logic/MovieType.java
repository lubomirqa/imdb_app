package logic;

import data.Database;

import java.util.ArrayList;

public class MovieType extends Feature{

  public MovieType(String s){
    name = s;
  }

  public static ArrayList<MovieType> getAllMovieTypes(){
    return Database.getAllMovieTypes();
  }

}
