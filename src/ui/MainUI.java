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
import java.util.Collections;

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
  private JTable userTable;
  private JButton saveButton;
  private JButton openButton;
  ArrayList<Movie> movies;
  ArrayList<Movie> userMovis;


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
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showUserMovies();
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
    for(Movie movie : userMovis){
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
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
    userMovis = new ArrayList<>();
    System.out.println("userMovies size = " + userMovis.size());
    System.out.println("movies size = " + movies.size());
    System.out.println(movies.get(0).getPrimaryTitle());
    for(int i = 0; i < movies.size(); i++){
      userMovis.add(movies.get(i));
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
            bw.write(userMovis.get(i).getPrimaryTitle() + "/");
            bw.write(userMovis.get(i).getAverageRating().toString() + "/");
            bw.write(userMovis.get(i).getStartYear().toString() + "/");
            bw.write(userMovis.get(i).getNumVotes().toString() + "/\n");
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



}
