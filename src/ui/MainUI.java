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
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class MainUI extends GUI {
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
  private JLabel moviesNumberLabel;
  private JTextField showNumberField;
  private JLabel yearLabel;
  private JComboBox yearDropdown;
  private JButton discardButton;
  private JLabel Year;
  ArrayList<Movie> movies;
  ArrayList<Movie> userMovies;
  private int removedMovies;
  private JFileChooser fc;
  private DefaultTableModel userModel;
  private boolean uploaded = false;


  public JPanel getRootPanel() {
    return rootPanel;
  }

  public MainUI() {
    createGenresDropdown();
    createMovieTypesDropdown();
    createYearDropdown();
    discardYearFilter();
    createSearch();
    createSearchKey();
    createMinVotesField();
    createShowCountField();
    createTable(movieTable);
    createTable(userTable);
    createUserTable();
    showMovies();
    saveFile();
    openFile();
    userMovies = new ArrayList<>();
    removeMovie();
    createUserRow();
    userModel = (DefaultTableModel) userTable.getModel();
  }

  private void createTable(JTable table) {

    table.setModel(new DefaultTableModel(
            null,
            new String[]{"Title", "Rating", "Year", "Votes"}
    ));

    TableColumnModel columns = table.getColumnModel();
    columns.getColumn(0).setMinWidth(200);

  }

  private void createMinVotesField() {
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

  private void createShowCountField() {
    showNumberField.getDocument().addDocumentListener(new DocumentListener() {
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

  private void createGenresDropdown() {
    ArrayList<Genre> genres = Genre.getAllGenres();
    for (Genre genre : genres) {
      genreDropdown.addItem(genre.getName());
    }

    genreDropdown.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) ;
        showMovies();
      }
    });
  }

  private void createMovieTypesDropdown() {
    ArrayList<MovieType> types = MovieType.getAllMovieTypes();
    for (MovieType type : types) {
      typesDropdown.addItem(type.getName());
    }

    typesDropdown.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) ;
        showMovies();
      }
    });
  }

  private void createYearDropdown(){
    int year = Calendar.getInstance().get(Calendar.YEAR);
    for(int i = year; i >= 1900; i--){
      yearDropdown.addItem(i);
    }

    yearDropdown.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED);
          filterByYear();
      }
    });
  }

  private void filterByYear(){
    Object derivedYear = yearDropdown.getSelectedItem();
    int year = (int) derivedYear;

    userModel.setRowCount(0);

    for(Movie movie : userMovies){
      if(movie.getStartYear() == year){
        userModel.addRow(new Object[]{
                movie.getPrimaryTitle(),
                movie.getAverageRating(),
                movie.getStartYear(),
                movie.getNumVotes()
        });
      }
    }
    userModel.setRowCount(userModel.getRowCount());
    totalMoviesCount.setText(String.valueOf(userModel.getRowCount()));
  }

  private void createSearch() {
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAllMovies();
      }
    });
  }

  private void createUserTable() {
    addAllButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showUserMovies();
      }
    });
  }

  private void createUserRow() {
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showUserRow();
      }
    });
  }

  private void createSearchKey() {
    searchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAllMovies();
      }
    });
  }

  private void showMovies() {
    Integer showCount = Integer.parseInt(showNumberField.getText());
    Integer minMovies = Integer.parseInt(minVotes.getText());
    String titleType = (String) typesDropdown.getSelectedItem();
    String genre = (String) genreDropdown.getSelectedItem();
    movies = Movie.findMovies(showCount, minMovies, titleType, genre);
    DefaultTableModel model = (DefaultTableModel) movieTable.getModel();

    model.setRowCount(0);
    for (Movie movie : movies) {
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
  }

  private void showUserMovies() {
    addMovies();

    userModel.setRowCount(0);
    for (Movie movie : userMovies) {
      userModel.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
    userModel.setRowCount(userModel.getRowCount());
    totalMoviesCount.setText(String.valueOf(userModel.getRowCount()));
  }

  private void showUserMoviesAfterFilter(){
    userModel.setRowCount(0);
    for (Movie movie : userMovies) {
      userModel.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
    userModel.setRowCount(userModel.getRowCount());
    totalMoviesCount.setText(String.valueOf(userModel.getRowCount()));
  }

  private void showUserRow() {
    addMovie();

    userModel.setRowCount(0);

    for (Movie movie : userMovies) {
      userModel.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }

    userModel.setRowCount(userModel.getRowCount());

    countMovies();
  }

  private void showAllMovies() {
    Integer showCount = Integer.parseInt(showNumberField.getText());
    String title = searchField.getText();
    Integer minMovies = Integer.parseInt(minVotes.getText());
    String titleType = (String) typesDropdown.getSelectedItem();
    String genre = (String) genreDropdown.getSelectedItem();
    movies = Movie.findMovies(showCount, title, minMovies, titleType, genre);
    DefaultTableModel model = (DefaultTableModel) movieTable.getModel();

    model.setRowCount(0);
    for (Movie movie : movies) {
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
  }

  private void addMovies() {
    for (int i = 0; i < movies.size(); i++) {
      userMovies.add(movies.get(i));
    }
  }

  private void discardYearFilter(){
    discardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showUserMoviesAfterFilter();
      }
    });
  }

  private void removeMovie() {
    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        removeRow();
      }
    });
  }

  private void addMovie() {
    try {
      userMovies.add(movies.get(movieTable.getSelectedRow()));
    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(null, "Row not selected");
    }
  }

  private void removeRow() {
    try {
      int rowIndex = userTable.getSelectedRow();
      userMovies.remove(rowIndex);

      userModel.removeRow(userTable.getSelectedRow());
      removedMovies++;
      userModel.setRowCount(userTable.getRowCount());
      countMovies();
    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(null, "Row not selected");
    }
  }

  private void countMovies() {
      totalMoviesCount.setText(String.valueOf(userModel.getRowCount()));
  }

  private void saveFile() {
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          File file = new File(System.getProperty("user.dir") + "/lib/movies.txt");
          if (!file.exists()) {
            file.createNewFile();
          }
          FileWriter fw = new FileWriter(file.getAbsoluteFile());
          BufferedWriter bw = new BufferedWriter(fw);

          for (int i = 0; i < (userMovies.size()); i++) {
            bw.write(userMovies.get(i).getPrimaryTitle() + "/");
            bw.write(userMovies.get(i).getAverageRating().toString() + "/");
            bw.write(userMovies.get(i).getStartYear().toString() + "/");
            bw.write(userMovies.get(i).getNumVotes().toString() + "/\n");
          }
          bw.close();
          fw.close();
          JOptionPane.showMessageDialog(null, "Data exported");

        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  private void openFile() {
    openButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogTitle("Choose a file with movies");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (fc.showOpenDialog(openButton) == JFileChooser.APPROVE_OPTION) {
        }

        String filePath = fc.getSelectedFile().getAbsolutePath();
        File file = new File(filePath);

        try {
          BufferedReader br = new BufferedReader(new FileReader(file));
          DefaultTableModel model = (DefaultTableModel) userTable.getModel();
          while (model.getRowCount() > 0) {
            model.removeRow(0);
          }
          Object[] tableLines = br.lines().toArray();

          userMovies = new ArrayList<>(tableLines.length);

          for (int i = 0; i < tableLines.length; i++) {
            String line = tableLines[i].toString().trim();
            String[] dataRow = line.split("/");

            Movie movie = new Movie(dataRow[0], Integer.parseInt(dataRow[2]), Float.parseFloat(dataRow[1]), Integer.parseInt(dataRow[3]));

            model.addRow(dataRow);
            userMovies.add(movie);
          }

          countMovies();
          uploaded = true;

        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
  }

}