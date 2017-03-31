/* GUI.java */

/****************************************
 * Kevin Tang
 * Mario Becerra
 ****************************************/

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
  
  public static void main(String args[]) {
    GUI application = new GUI();
  }
  
  private javax.swing.JButton blockButton;
  private javax.swing.JButton encryptButton;
  private javax.swing.JButton keyButton;
  private javax.swing.JButton unblockButton;
  //private int blockSize = 8; // blocking size is hardcoded
  // End of variables declaration   
  
  public GUI() {
    super("Main Menu");
    initComponents();
  }
  
  private void initComponents() {
    
    keyButton = new JButton();
    blockButton = new JButton();
    unblockButton = new JButton();
    encryptButton = new JButton();
    
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    
    keyButton.setText("Key Creation");
    keyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        keyButtonActionPerformed(evt);
      }
    });
    
    blockButton.setText("Block A File");
    blockButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        blockButtonActionPerformed(evt);
      }
    });
    
    unblockButton.setText("UnBlock A File");
    unblockButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        unblockButtonActionPerformed(evt);
      }
    });
    
    encryptButton.setText("Encrypt/Decrypt");
    encryptButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        encryptButtonActionPerformed(evt);
      }
    });
    Container container = getContentPane();
    container.setLayout( new GridLayout(2,2, 10, 10) );
    keyButton.setPreferredSize(new Dimension(150,50));
    container.add( keyButton);
    blockButton.setPreferredSize(new Dimension(150,50));
    container.add( blockButton );
    unblockButton.setPreferredSize(new Dimension(150,50));
    container.add( unblockButton );
    encryptButton.setPreferredSize(new Dimension(150,50));
    container.add( encryptButton );
    
    pack();
    setVisible(true);
  }
  
  /*
   * 1. Prompts for name of file to be blocked.
   * 2. Prompts for blocksize.
   * 3. Prompts you to name file to put blocked message in.
   */
  private void blockButtonActionPerformed(ActionEvent evt) {                                            
    // TODO add your handling code here:
    int blockSize = 0;
    String inputName = JOptionPane.showInputDialog("Enter the name of the file you want blocked: ");
    if ( inputName == null) {
      System.out.println("No File entered.");
      return;
    }
    
    String block = JOptionPane.showInputDialog("Enter Block Size: ");
    try { 
      blockSize = Integer.parseInt(block);
    } catch (NumberFormatException e){
      System.out.println("No Block Size Entered.");
      return;
    }
    
    String outputName = JOptionPane.showInputDialog("What should the 'blocked' file be named?");
    if ( outputName == null) {
      System.out.println("No output file named.");
      return;
    }
    Blocking blockFile = new Blocking(inputName, outputName);
    blockFile.block(blockSize);
  }                                           
  
  private void keyButtonActionPerformed(ActionEvent evt) {                                          
    Keys keyGen;
    keyGen = new Keys();
    keyGen.choose();
  }                                         
  
  private void unblockButtonActionPerformed(ActionEvent evt) {                                              
    // TODO add your handling code here:
    String inputName = JOptionPane.showInputDialog("Enter the name of the file you want to unblock: ");
    //String block = JOptionPane.showInputDialog("Enter Block Size: ");
    if ( inputName == null) {
      System.out.println("No File entered.");
      return;
    }
    
    String outputName = JOptionPane.showInputDialog("Enter the name of the file you want to create: ");
    //String outputName = "decoded.txt"; // default name to output to, should we prompt for a file name instead?
    Blocking unBlockFile = new Blocking(inputName, outputName);
    unBlockFile.unBlock();
  }                                             
  
  private void encryptButtonActionPerformed(ActionEvent evt) {                                              
    // TODO add your handling code here:
    String input = JOptionPane.showInputDialog("Enter the name of the blocked file to be used for the input number: ");
    if ( input == null) {
      System.out.println("No File entered.");
      return;
    }
    
    String key = JOptionPane.showInputDialog("Enter the name of the file containing your key pair: ");
    if ( key == null) {
      System.out.println("No File entered.");
      return;
    }
  
    String output = JOptionPane.showInputDialog("Enter the name of the blocked file to be used for the output number: ");
    
    if ( output == null) {
      System.out.println("No File entered.");
      return;
    }
    
    Cryptography cryptInfo = new Cryptography();
    cryptInfo.getInfo(input, key, output);
  }
  
  
}