package ui;

import logic.Genre;
import logic.MovieType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class MainUI {
  private JPanel rootPanel;
  private JLabel tableLabel;
  private JScrollBar scrollBar1;
  private JTextArea textArea1;
  private JComboBox filterDropdown;
  private JLabel search;
  private JTable movieTable;
  private JLabel type;
  private JLabel genre;
  private JComboBox typesDropdown;

  public JPanel getRootPanel(){
    return rootPanel;
  }

  public MainUI(){
    createTable();
    createSearch();
    createGenresDropdown();
    createMovieTypesDropdown();
  }

  private void createTable() {

    Object[][] data = {
            {"Inception", "2010", "USA", "Comedy"},
            {"Inception", "2010", "USA", "Comedy"},
            {"Inception", "2010", "USA", "Comedy"}
    };

    movieTable.setModel(new DefaultTableModel(
            data,
            new String[]{"Title", "Year", "Country", "Genre"}
    ));

    TableColumnModel columns = movieTable.getColumnModel();
    columns.getColumn(0).setMinWidth(200);

  }

  private void createSearch(){

  }

  private void createGenresDropdown(){
    ArrayList<Genre> genres = Genre.getAllGenres();
    for(Genre genre : genres){
      filterDropdown.addItem(genre.getName());
    }
  }

  private void createMovieTypesDropdown(){
    ArrayList<MovieType> types = MovieType.getAllMovieTypes();
    for(MovieType type : types){
      typesDropdown.addItem(type.getName());
    }
  }
}
