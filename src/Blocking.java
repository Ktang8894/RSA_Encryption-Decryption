/* Blocking.java */

/****************************************
 * Kevin Tang
 * Mario Becerra
 ****************************************/

import java.io.*;
import java.util.Scanner;

public class Blocking {

  private String inputFileName;
  private String outputFileName;
  
  public Blocking (String file, String file2) {
    inputFileName = file;
    outputFileName = file2;
  }
  
  /* implements the subset of ASCII characters we were given in pdf */
  private String specialChars (int n) {
    String ch = "";
    if ( n == 32 ) {
      ch = "05";
    } else if ( n > 13 ) {
      int temp = n - 27;
      ch = temp + ch;
    } else if ( n == 0 ) {
      ch = "00"; // null char
    } else if ( n == 11 ) {
      ch = "01"; // vertical tab
    } else if ( n == 9 ) {
      ch = "02"; // horizontal tab
    } else if ( n == 10 ) {
      ch = "03"; // new line
    } else {
      ch = "04"; // carriage return
    }
    return ch;
  }
  
  private String toAscii (int n) {
    String ch = "";
    if ( n == 5 ) {
      ch = " ";
      System.out.println(ch);
    } else if ( n == 0 ) {
      ch = "\0";
    } else if ( n == 1 ) {
      ch = "v"; // '/v' gives error printing 'v' for now
    } else if ( n == 2 ) {
      ch = "\t";
    } else if ( n == 3 ) {
      ch = "\n";
    } else if ( n == 4 ){
      ch = "\r";
    } else {
      n = n + 27; // adding 27 to go back to regular ascii character set
      ch = Character.toString((char)n);
      System.out.println(ch);
      return ch;
    }
    return ch;
  }
 
  public void block (int blockSize) {
    FileReader inputStream = null;
    int c = -1;
    int count = 0;
    String s = "";
    try {
      inputStream = new FileReader(inputFileName); //input file must be a .txt file
      File f = new File(outputFileName);
      PrintWriter pw = null;
      try {
        pw = new PrintWriter(f);
        while ( (c = inputStream.read()) > -1 ) { //reads chars 1 at a time
          count++;
          String ch = specialChars(c);
          s = ch + s;
          if ( count == blockSize ) {
            count = 0;
            pw.printf("%s\n", s);
            pw.flush();
            s = "";
          }
        }
        if ( count > 0) { // takes care of padding
          int nullChars = blockSize - count;
          while (nullChars != 0) {
            s = "00" + s;
            nullChars--;
          }
          pw.printf("%s\n", s);
          pw.flush();
        }
      } catch (FileNotFoundException e) {
        System.out.println("Could not write to file.");
      } finally {
        if ( pw != null ) {
          pw.close();
        }
      }
    } catch (IOException e) {
      System.out.println("Could not open file.");
    }
  }
  
  
  /*does not need blocksize*/
  public void unBlock () {
    File file = null;
    try { //opens file to read from
      file = new File(inputFileName);
      Scanner scanner = new Scanner(file);
      File f = new File(outputFileName); //output file is currently hardcoded to be decoded.txt
      PrintWriter pw = null;
      try { // prints to output file
        pw = new PrintWriter(f);
        while ( scanner.hasNext() ) {
          String line = scanner.nextLine();
          String[] res = line.split("");
          int i = res.length-1;
          while ( i > 0 ) {
            String num = res[i-1] + res[i];
            int temp = Integer.parseInt(num);
            String ch = toAscii(temp);
            pw.printf("%s", ch);
            pw.flush();
            i = i-2;
          }
        }
      } catch (FileNotFoundException e) {
        System.out.println("Could not write to file.");
      } finally {
        if ( pw != null ) {
          pw.close();
        }
      }
    } catch (FileNotFoundException e) {
        System.out.println("Could not find file.");
    }
  }
}