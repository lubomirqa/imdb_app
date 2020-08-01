package ui;

import logic.Genre;
import logic.Movie;
import logic.MovieType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainUI {
  private JPanel rootPanel;
  private JLabel tableLabel;
  private JComboBox genreDropdown;
  private JLabel search;
  private JTable movieTable;
  private JLabel type;
  private JLabel genre;
  private JComboBox typesDropdown;
  private JTextField minVotes;

  public JPanel getRootPanel(){
    return rootPanel;
  }

  public MainUI(){
    createGenresDropdown();
    createMovieTypesDropdown();
    createMinVotesField();
    createTable();
    showMovies();

  }

  private void showMovies(){
    Integer minMovies = Integer.parseInt(minVotes.getText());
    String titleType = (String) typesDropdown.getSelectedItem();
    String genre = (String) genreDropdown.getSelectedItem();
    ArrayList<Movie> movies = Movie.findMovies(minMovies, titleType, genre);
    DefaultTableModel model = (DefaultTableModel)movieTable.getModel();

    model.setRowCount(0);
    for(Movie movie : movies){
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
  }

  private void createTable() {

    movieTable.setModel(new DefaultTableModel(
            null,
            new String[]{"Title", "Year", "Rating", "Num Votes"}
    ));

    TableColumnModel columns = movieTable.getColumnModel();
    columns.getColumn(0).setMinWidth(200);

  }

  private void createMinVotesField(){
    minVotes.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        showMovies();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        showMovies();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        showMovies();
      }
    });
  }

  private void createGenresDropdown(){
    ArrayList<Genre> genres = Genre.getAllGenres();
    for(Genre genre : genres){
      genreDropdown.addItem(genre.getName());
    }

    genreDropdown.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.DESELECTED);
        showMovies();
      }
    });
  }

  private void createMovieTypesDropdown(){
    ArrayList<MovieType> types = MovieType.getAllMovieTypes();
    for(MovieType type : types){
      typesDropdown.addItem(type.getName());
    }

    typesDropdown.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.DESELECTED);
        showMovies();
      }
    });
  }
}
