package logic;

import data.Database;
import java.util.ArrayList;

public class Genre extends Feature{

  public Genre(String s){
    name = s;
  }

  public static ArrayList<Genre> getAllGenres(){
    return Database.getAllGenres();
  }

}
