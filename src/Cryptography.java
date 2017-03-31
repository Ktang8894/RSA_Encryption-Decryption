/* Cryptography.java */

/****************************************
 * Kevin Tang
 * Mario Becerra
 ****************************************/

import java.io.*;
import java.util.ArrayList;
import java.math.BigInteger; //For testing purposes

public class Cryptography {
  
  public void getInfo (String InputFile, String key, String OutputFile) {
    
    /* Put file's values into array */
    String[] InputNumbersArray = null;
    
    try {
      BufferedReader in = new BufferedReader(new FileReader(InputFile));
      String str = null;
      ArrayList<String> InputNumbers = new ArrayList<String>();
      while((str = in.readLine()) != null) {
        InputNumbers.add(str);
      }
      InputNumbersArray = InputNumbers.toArray(new String[InputNumbers.size()]);
      in.close();
    }
    catch (FileNotFoundException exc) {
      System.out.println(exc);
    }
    catch (IOException exc) {
      System.out.println(exc);
    }
    

    /* Get Key */
    String[] KeyFileArray = null;
    
    try {
      BufferedReader in = new BufferedReader(new FileReader(key));
      String str = null;
      ArrayList<String> KeyIn = new ArrayList<String>();
      while((str = in.readLine()) != null) {
        KeyIn.add(str);
      }
      KeyFileArray = KeyIn.toArray(new String[KeyIn.size()]);
      in.close();
    }
    catch (FileNotFoundException exc) {
      System.out.println(exc);
    }
    catch (IOException exc) {
      System.out.println(exc);
    }  
    
    String expValLine = KeyFileArray[1];
    String exp = expValLine.substring(expValLine.indexOf(">") + 1, expValLine.indexOf("</")); //Value of exp
    System.out.println("d value from key file: " + exp);
    
    String nValLine = KeyFileArray[2];
    String n = nValLine.substring(nValLine.indexOf(">") + 1, nValLine.indexOf("</")); //Value of n
    System.out.println("n value from key file: " + n);
    
    
    /* Do encryption/decryption */
    String[] OutputNumbersArray = new String[InputNumbersArray.length];
    HugeUnsignedInt[] HugeInput = new HugeUnsignedInt[InputNumbersArray.length];
    //Convert each line in InputNumbersArray into huge ints
    for (int i=0; i<InputNumbersArray.length; i++) {
      HugeInput[i] = new HugeUnsignedInt(InputNumbersArray[i]);
    }
    
    HugeUnsignedInt hugeExpVal = new HugeUnsignedInt(exp);
    HugeUnsignedInt hugeNVal = new HugeUnsignedInt(n);
    HugeUnsignedInt[] inPower = new HugeUnsignedInt[InputNumbersArray.length];
    HugeUnsignedInt[] modn = new HugeUnsignedInt[InputNumbersArray.length];
    HugeUnsignedInt[] HugeOutput = new HugeUnsignedInt[InputNumbersArray.length];
    
    /* Big Int Testing */
    //HugeUnsignedInt not entirely functional; Using this for testing
    BigInteger[] inputTesting = new BigInteger[InputNumbersArray.length];
    for (int i=0; i<InputNumbersArray.length; i++) {
      inputTesting[i] = new BigInteger(InputNumbersArray[i]);
    }
    
    int expInt = Integer.parseInt(exp);
    BigInteger[] outputTesting = new BigInteger[InputNumbersArray.length];
    BigInteger bigNVal = new BigInteger(n);
    
    for (int i=0; i<InputNumbersArray.length; i++) {
     // outputTesting[i] = inputTesting[i];
      //C^e
    //  for (int j=0; j<expInt; j++) {
     //   outputTesting[i] = outputTesting[i].multiply(inputTesting[i]);
     // }
      outputTesting[i] = inputTesting[i].pow(expInt);
      outputTesting[i] = outputTesting[i].mod(bigNVal);
      System.out.println("Testing: " + outputTesting[i]);
    }
    // End Big Int Testing

    /* Write file */
    try {
      File file = new File(OutputFile);
      
      if (!file.exists()) {
        file.createNewFile();
      }
      
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      for (int i=0; i<InputNumbersArray.length; i++) {
        bw.write(outputTesting[i] + "\n");
      }
      bw.close();
    }   
    catch (FileNotFoundException exc) {
      System.out.println(exc);
    }
    catch(IOException exc) {
      exc.printStackTrace();
    }
  }
}