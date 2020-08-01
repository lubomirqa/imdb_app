import ui.MainUI;

import javax.swing.*;

public class Main {

  private static int TABLE_WIDTH = 600;
  private static int TABLE_HEIGHT = 500;

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        createGui();
      }
    });

  }

  private static void createGui(){
    MainUI ui = new MainUI();
    JPanel root = ui.getRootPanel();
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(root);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
    frame.setVisible(true);
  }
}
