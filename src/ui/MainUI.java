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
import java.util.Arrays;
import java.util.Collections;

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
  private JButton userSearchButton;
  private JTextField userSearchField;
  ArrayList<Movie> movies;
  ArrayList<Movie> userMovies;
  private int removedMovies;
  private JFileChooser fc;


  public JPanel getRootPanel() {
    return rootPanel;
  }

  public MainUI() {
    createGenresDropdown();
    createMovieTypesDropdown();
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

    DefaultTableModel model = (DefaultTableModel) userTable.getModel();

    model.setRowCount(0);
    for (Movie movie : userMovies) {
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }
    countMovies();
  }

  private void showUserRow() {
    addMovie();

    System.out.println("userMovies before adding size = " + userMovies.size());
    System.out.println("userMovies before adding 1st title = " + userMovies.get(0).getPrimaryTitle());
    System.out.println("userMovies before adding 2nd title = " + userMovies.get(1).getPrimaryTitle());

    DefaultTableModel model = (DefaultTableModel) userTable.getModel();
    model.setRowCount(0);

    for (Movie movie : userMovies) {
      model.addRow(new Object[]{
              movie.getPrimaryTitle(),
              movie.getAverageRating(),
              movie.getStartYear(),
              movie.getNumVotes()
      });
    }

    System.out.println("userMovies after adding size = " + userMovies.size());
    System.out.println("userMovies after adding 1st title = " + userMovies.get(0).getPrimaryTitle());
    System.out.println("userMovies after adding 2nd title = " + userMovies.get(1).getPrimaryTitle());

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
    DefaultTableModel model = (DefaultTableModel) userTable.getModel();

    try {
      model.removeRow(userTable.getSelectedRow());
      removedMovies++;
      model.setRowCount(userTable.getRowCount() - removedMovies);
      countMovies();
    } catch (ArrayIndexOutOfBoundsException e) {
      JOptionPane.showMessageDialog(null, "Row not selected");
    }
  }

  private void countMovies() {
    String count = String.valueOf(userMovies.size() - removedMovies);
    totalMoviesCount.setText(count);
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
          //
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

          System.out.println("userMovies size before the loop = " + userMovies.size());

          for (int i = 0; i < tableLines.length; i++) {
            String line = tableLines[i].toString().trim();
            String[] dataRow = line.split("/");

            Movie movie = new Movie(dataRow[0], Integer.parseInt(dataRow[2]), Float.parseFloat(dataRow[1]), Integer.parseInt(dataRow[3]));

            model.addRow(dataRow);
            userMovies.add(movie);
          }

          //
          System.out.println("tableLines length = " + tableLines.length);
          System.out.println(userMovies.get(0).getPrimaryTitle());
          System.out.println("prelast title = " + userMovies.get(userMovies.size()-2).getPrimaryTitle());
          System.out.println("last title = " + userMovies.get(userMovies.size()-1).getPrimaryTitle());
          System.out.println("userMovies size = " + userMovies.size());
          //

          countMovies();

        } catch (Exception ex) {
          ex.printStackTrace();
        }

      }
    });
  }

}