package logic;

import data.Database;
import java.util.ArrayList;

public class Genre {
  private String name;

  public Genre(String s){
    name = s;
  }

  public static ArrayList<Genre> getAllGenres(){
    return Database.getAllGenres();
  }

  public String getName() {
    return name;
  }

}
