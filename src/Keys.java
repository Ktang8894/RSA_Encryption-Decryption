/* Keys.java */

/****************************************
* Kevin Tang
* Mario Becerra
****************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.*;
//import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public class Keys extends JFrame{
  
  private JButton generate;
  private JButton uInput;
  
  public void choose () {
    
    setLayout(new GridLayout(1,2, 10, 10));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 150);
    setVisible(true);
    
    generate = new JButton("Program Generated");
    uInput = new JButton("User Generated");
    add(generate);
    add(uInput);
    
    Handler handler = new Handler();
    generate.addActionListener(handler);
    uInput.addActionListener(handler);
  }
  
  /* Choose Menu Handler Class */
  private class Handler implements ActionListener {
    public void actionPerformed(ActionEvent evt) {
      /* Program Generated */
      if (evt.getSource() == generate) {
        getPrimes();
      }
      
      /* User Input */
      if (evt.getSource() == uInput) {
        
        /* p value */
        String pVal = JOptionPane.showInputDialog(null,"Please Enter a prime number greater than or equal to 127", "p Value", JOptionPane.INFORMATION_MESSAGE);
        int p = Integer.parseInt(pVal);
        
        while (isPrime(p) == false || p < 127) {
          p = Integer.parseInt(JOptionPane.showInputDialog(null,"Input was not prime or was not greater than or equal to 127\nPlease Enter a prime number"));  
        }
        
        /* q value */
        String qVal = JOptionPane.showInputDialog(null,"Please Enter a prime number greater than or equal to 127", "q Value", JOptionPane.INFORMATION_MESSAGE);
        int q = Integer.parseInt(qVal);
        
        while (isPrime(q) == false || q < 127) {
          q = Integer.parseInt(JOptionPane.showInputDialog(null,"Input was not prime or was not greater than or equal to 127\nPlease Enter a prime number")); 
        }
        
        RSA rsa = new RSA();
        
        int n = rsa.n(p, q);
        int phi = rsa.phi(p, q);
        int e = rsa.e(n, phi);
        long d = rsa.d(phi, e);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n);
        System.out.println("phi = " + phi);
        System.out.println("e = " + e);
        System.out.println("d = " + d);
        createPub(e, n);
        createPri(d, n);
      }
    }
  }
  
  /* Program Generated Prime #'s */
  public void getPrimes(){
    String[] linesArray = null;
    
    /* Put file's values into array */
    try {
      BufferedReader in = new BufferedReader(new FileReader("primeNumbers.txt"));
      String str = null;
      ArrayList<String> lines = new ArrayList<String>();
      while((str = in.readLine()) != null) {
        lines.add(str);
      }
      linesArray = lines.toArray(new String[19]);
      in.close();
    }
    catch (FileNotFoundException exc) {
      System.out.println(exc);
    }
    catch (IOException exc) {
      System.out.println(exc);
    }
    
    /* Randomly assign prime #'s */
    Random rn = new Random();
    int p = Integer.parseInt(linesArray[rn.nextInt(19) + 0]);
    int q = Integer.parseInt(linesArray[rn.nextInt(19) + 0]);

    RSA rsa = new RSA();
    
    int n = rsa.n(p, q);
    int phi = rsa.phi(p, q);
    int e = rsa.e(n, phi);
    long d = rsa.d(phi, e);
    System.out.println("p = " + p);
    System.out.println("q = " + q);
    System.out.println("n = " + n);
    System.out.println("phi = " + phi);
    System.out.println("e = " + e);
    System.out.println("d = " + d);
    createPub(e, n);
    createPri(d, n);
  }
  
  /* Create Public Key File */
  public void createPub (int e, int n) {
    try {
      String publicKey = "<rsakey>\n" +
        "\t<evalue>" + e + "</evalue>\n" +
        "\t<nvalue>" + n + "</nvalue>\n" +
        "</rsakey>";
      File file = new File("pubkey");
      
      if (!file.exists()) {
        file.createNewFile();
      }
      
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(publicKey);
      bw.close();
    }
    catch(IOException exc) {
      exc.printStackTrace();
    }
  }
  
  /* Create Private Key File */
  public void createPri (long d, int n) {
    try {
      String privateKey = "<rsakey>\n" +
        "\t<dvalue>" + d + "</dvalue>\n" +
        "\t<nvalue>" + n + "</nvalue>\n" +
        "</rsakey>";
      File file = new File("prikey");
      
      if (!file.exists()) {
        file.createNewFile();
      }
      
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(privateKey);
      bw.close();
    }
    catch(IOException exc) {
      exc.printStackTrace();
    }
  }
  
  /* Checking if number is Prime */
  public static boolean isPrime(int num) {
    if (num < 2) return false;
    if (num == 2) return true;
    if (num % 2 == 0) return false;
    for (int i = 3; i * i <= num; i += 2)
      if (num % i == 0) return false;
    return true;
  }
}