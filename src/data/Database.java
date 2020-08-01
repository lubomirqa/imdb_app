package data;

import logic.Genre;
import logic.Movie;
import logic.MovieType;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Database {
  static public Connection connection = null;
  private static String CONN_STRING = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/IMDB";
  private static String USERNAME = "275student";
  private static String PASSWORD = "275student";
  private static String GET_ALL_GENRES_SQL = "SELECT DISTINCT RTRIM(genre) AS genre FROM title_genre";
  private static String GET_ALL_TYPES_SQL = "SELECT DISTINCT RTRIM(titleType) AS titleType FROM title_basics";

  private static String FIND_MOVIES_SQL = "SELECT TOP 50 primaryTitle, startYear, averageRating, numVotes\n" +
          "FROM title_basics\n" +
          "JOIN title_ratings ON title_basics.tconst = title_ratings.tconst\n" +
          "JOIN title_genre ON title_basics.tconst = title_genre.tconst\n" +
          "WHERE numVotes > ?\n" +
          "AND titleType = ?\n" +
          "AND genre = ?\n" +
          "ORDER BY averageRating DESC";

  /*private static String SEARCH_BY_NAME_SQL = "SELECT DISTINCT TOP 50 primaryTitle, startYear, averageRating, numVotes\n" +
          "FROM title_basics\n" +
          "JOIN title_ratings ON title_basics.tconst = title_ratings.tconst\n" +
          "JOIN title_genre ON title_basics.tconst = title_genre.tconst\n" +
          "WHERE primaryTitle = ?\n" +
          "ORDER BY averageRating DESC";*/

  private static String SEARCH_ALL_BY_NAME_SQL = "SELECT DISTINCT TOP 50 primaryTitle, startYear, averageRating, numVotes\n" +
          "FROM title_basics\n" +
          "JOIN title_ratings ON title_basics.tconst = title_ratings.tconst\n" +
          "JOIN title_genre ON title_basics.tconst = title_genre.tconst\n" +
          "WHERE primaryTitle = ?\n" +
          "AND numVotes > ?\n" +
          "AND titleType = ?\n" +
          "AND genre = ?\n" +
          "ORDER BY averageRating DESC";


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

  public static ArrayList<Movie> findMovies(Integer minMovies, String titleType, String genre){
    connect();
    ArrayList<Movie> movies = new ArrayList<>();

    try {
      PreparedStatement stmt = connection.prepareStatement(FIND_MOVIES_SQL);
      stmt.setInt(1, minMovies);
      stmt.setString(2, titleType);
      stmt.setString(3, genre);
      ResultSet result = stmt.executeQuery();
      while(result.next()){
        //primaryTitle, startYear, averageRating, numVotes
        movies.add(new Movie(result.getString("primaryTitle"),
                result.getInt("startYear"),
                result.getFloat("averageRating"),
                result.getInt("numVotes")
                )
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return movies;
  }

  /*public static ArrayList<Movie> findMovies(String title){
    connect();
    ArrayList<Movie> movies = new ArrayList<>();

    try {
      PreparedStatement stmt = connection.prepareStatement(SEARCH_BY_NAME_SQL);
      stmt.setString(1, title);
      ResultSet result = stmt.executeQuery();
      while (result.next()) {
        movies.add(new Movie(result.getString("primaryTitle"),
                        result.getInt("startYear"),
                        result.getFloat("averageRating"),
                        result.getInt("numVotes")
                )
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return movies;
  }*/

  public static ArrayList<Movie> findMovies(String title, Integer minMovies, String titleType, String genre){
    connect();
    ArrayList<Movie> movies = new ArrayList<>();

    try {
      PreparedStatement stmt = connection.prepareStatement(SEARCH_ALL_BY_NAME_SQL);
      stmt.setString(1, title);
      stmt.setInt(2, minMovies);
      stmt.setString(3, titleType);
      stmt.setString(4, genre);
      ResultSet result = stmt.executeQuery();
      while(result.next()){
        //primaryTitle, startYear, averageRating, numVotes
        movies.add(new Movie(result.getString("primaryTitle"),
                        result.getInt("startYear"),
                        result.getFloat("averageRating"),
                        result.getInt("numVotes")
                )
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return movies;
  }
}
