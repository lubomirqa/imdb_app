package ui;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class GUI {

  private static int TABLE_WIDTH = 600;
  private static int TABLE_HEIGHT = 500;
  static JFrame frame;
  static private JMenuBar menuBar;
  static private JMenu menu;
  static private JMenuItem openButton;
  static private JMenuItem saveButton;

  public static void createGui(){
    MainUI ui = new MainUI();
    JPanel root = ui.getRootPanel();
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(root);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
    frame.setVisible(true);
    createMenu();
  }

  private static void createMenu(){

    System.setProperty("apple.laf.useScreenMenuBar", "true");

    menuBar = new JMenuBar();

    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_A);
    menu.getAccessibleContext().setAccessibleDescription("Menu Description");

    openButton = new JMenuItem("Open");
    saveButton = new JMenuItem("Save");
    menu.add(openButton)
        .add(saveButton);

    menuBar.add(menu);
    frame.setJMenuBar(menuBar);

  }
}
