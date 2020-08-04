package ui;

import logic.Genre;
import logic.Movie;
import logic.MovieType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainUI extends GUI{
  protected JPanel rootPanel;
  private JLabel tableLabel;
  private JComboBox genreDropdown;
  private JLabel minVotesField;
  private JTable movieTable;
  private JLabel type;
  private JLabel genre;
  private JComboBox typesDropdown;
  private JTextField minVotes;
  private JTextField searchField;
  private JButton searchButton;
  protected JButton addButton;
  protected JButton removeButton;
  public JTable userTable;
  private JButton saveButton;
  private JButton openButton;
  private JButton addAllButton;
  private JLabel totalField;
  private JLabel totalMoviesCount;
  ArrayList<Movie> movies;
  ArrayList<Movie> userMovies;
  private int removedMovies;


  public JPanel getRootPanel(){
    return rootPanel;
  }

  public MainUI(){
    createGenresDropdown();
    createMovieTypesDropdown();
    createSearch();
    createSearchKey();
    createMinVotesField();
    createTable(movieTable);
    createTable(userTable);
    createUserTable();
    showMovies();
    saveFile();
    userMovies = new ArrayList<>();
    removeMovie();
    createUserRow();
  }

  private void createTable(JTable table) {

    table.setModel(new DefaultTableModel(
            null,
            new String[]{"Title", "Rating", "Year", "Votes"}
    ));

    TableColumnModel columns = table.getColumnModel();
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

  private void createSearch(){
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAllMovies();
      }
    });
  }

  private void createUserTable(){
    addAllButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showUserMovies();
      }
    });
  }

  private void createUserRow(){
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showUserRow();
      }
    });
  }

  private void createSearchKey(){
    searchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAllMovies();
      }
    });
  }

  private void showMovies(){
    Integer minMovies = Integer.parseInt(minVotes.getText());
    String titleType = (String) typesDropdown.getSelectedItem();
    String genre = (String) genreDropdown.getSelectedItem();
    movies = Movie.findMovies(minMovies, titleType, genre);
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

  private void showUserMovies(){
    addMovies();

    DefaultTableModel model = (DefaultTableModel)userTable.getModel();

    model.setRowCount(0);
    for(Movie movie : userMovies){
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
    countMovies();
  }

  private void showUserRow(){
    addMovie();

    DefaultTableModel model = (DefaultTableModel)userTable.getModel();

    model.setRowCount(0);
    for(Movie movie : userMovies){
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
    countMovies();
  }

  private void showAllMovies(){
    String title = searchField.getText();
    Integer minMovies = Integer.parseInt(minVotes.getText());
    String titleType = (String) typesDropdown.getSelectedItem();
    String genre = (String) genreDropdown.getSelectedItem();
    movies = Movie.findMovies(title, minMovies, titleType, genre);
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

  private void addMovies(){
    for(int i = 0; i < movies.size(); i++){
      userMovies.add(movies.get(i));
    }
  }

  private void removeMovie(){
    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        removeRow();
      }
    });
  }

  private void addMovie(){
    try {
      userMovies.add(movies.get(movieTable.getSelectedRow()));
    } catch(ArrayIndexOutOfBoundsException e){
      JOptionPane.showMessageDialog(null, "Row not selected");
    }
  }

  private void removeRow(){
    DefaultTableModel model = (DefaultTableModel)movieTable.getModel();

    try {
      ((DefaultTableModel) userTable.getModel()).removeRow(userTable.getSelectedRow());
      removedMovies++;
      model.setRowCount(userTable.getRowCount() - removedMovies);
      countMovies();
    } catch(ArrayIndexOutOfBoundsException e){
      JOptionPane.showMessageDialog(null, "Row not selected");
    }
  }

  private void saveFile(){
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          File file = new File("/Users/mac/imdb_app/lib/movies.txt");
          if (!file.exists()) {
            file.createNewFile();
          }
          FileWriter fw = new FileWriter(file.getAbsoluteFile());
          BufferedWriter bw = new BufferedWriter(fw);

          for(int i = 0; i < (movies.size()); i++){
            bw.write(userMovies.get(i).getPrimaryTitle() + "/");
            bw.write(userMovies.get(i).getAverageRating().toString() + "/");
            bw.write(userMovies.get(i).getStartYear().toString() + "/");
            bw.write(userMovies.get(i).getNumVotes().toString() + "/\n");
          }
          bw.close();
          fw.close();
          JOptionPane.showMessageDialog(null, "Data exported");

        } catch(Exception ex){
          ex.printStackTrace();
        }
      }
    });
  }

  private void countMovies(){
    String count = String.valueOf(userMovies.size() - removedMovies);
    totalMoviesCount.setText(count);
  }

}
