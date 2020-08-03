package ui;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static ui.MainUI.*;

public class GUI {

  private static int TABLE_WIDTH = 600;
  private static int TABLE_HEIGHT = 500;
  static JFrame frame;
  static JPanel root;


  public static void createGui(){
    MainUI ui = new MainUI();
    root = ui.getRootPanel();
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(root);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
    frame.setVisible(true);
    createMenu();
  }


}
