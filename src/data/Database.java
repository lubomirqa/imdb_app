package data;

import logic.Genre;
import logic.MovieType;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Database {
  static public Connection connection = null;
  private static String CONN_STRING = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/IMDB";
  private static String USERNAME = "275student";
  private static String PASSWORD = "275student";
  private static String GET_ALL_GENRES_SQL = "SELECT DISTINCT genre FROM title_genre";
  private static String GET_ALL_TYPES_SQL = "SELECT DISTINCT titleType FROM title_basics";


  public static void connect(){
    if(null != connection){
      return;
    } else {
      try {
        connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
      } catch (SQLException e) {
        e.printStackTrace();
        exit(-1);
      }
    }
  }

  public static ArrayList<Genre> getAllGenres(){
    connect();
    ArrayList<Genre> genres = new ArrayList<>();

    try {
      PreparedStatement stmt = connection.prepareStatement(GET_ALL_GENRES_SQL);
      ResultSet result = stmt.executeQuery();
      while(result.next()){
        genres.add(new Genre(result.getString("genre")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return genres;
  }

  public static ArrayList<MovieType> getAllMovieTypes(){
    connect();
    ArrayList<MovieType> types = new ArrayList<>();

    try {
      PreparedStatement stmt = connection.prepareStatement(GET_ALL_TYPES_SQL);
      ResultSet result = stmt.executeQuery();
      while(result.next()){
        types.add(new MovieType(result.getString("titleType")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return types;
  }
}
