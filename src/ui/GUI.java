package ui;

import javax.swing.*;

public class GUI {

  private static int TABLE_WIDTH = 650;
  private static int TABLE_HEIGHT = 550;
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
  }


}
