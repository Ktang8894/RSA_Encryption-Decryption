/* RSA.java */

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

public class RSA {

  /* d Value */
  public long d (int phi, int e) {
    long iTimesPhi = phi;
    long i = 1;
    
    while ((1 + iTimesPhi)%e != 0) {
      i++;
      iTimesPhi = i*phi;
    }
    
    return (1 + iTimesPhi)/e;
  }
  
  /* e Value */
  public int e (int n, int phi) {
    int i = 0;
    //e is less than n 
    for (i=3; i<n; i++) {
      //e is coprime with phi
     if (findGCD(i, phi) != 1) 
       i++;    
     else 
       return i;
    }
    return 0;
  }
  
  /* Finding GCD */
  public int findGCD(int x, int y) {
    if(y == 0) {
      return x; //Return GCD 
    }
    return findGCD(y, x%y); //Recursively call findGCD 
  }
  
  /* Phi Value */
  public int phi(int p, int q) {
    return (p-1)*(q-1);
  }
  
  /* n Value */
  public int n(int p, int q){
    return p*q;
  }
}