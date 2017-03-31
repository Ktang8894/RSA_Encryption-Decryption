/* HugeUnsignedInt.java */

/****************************************
 * Kevin Tang [ktang20]
 * Mario Becerra
 ****************************************/

import java.util.Collections;
import java.util.ArrayList;
import java.lang.Math;

/* pass strings containing #'s as parameters */
public class HugeUnsignedInt {
  private ArrayList<Integer> hugeInt = new ArrayList<Integer>();
  
  public HugeUnsignedInt (String str) {
    for (int i = 0; i < str.length(); i++) {
      hugeInt.add(Character.getNumericValue(str.charAt(i)));
    }
    Collections.reverse(hugeInt);
  }

  //Created for testing purposes
  public long HugeIntValue() {
    long returnVal = 0;
    for (int i =0; i < hugeInt.size(); i++) {
      returnVal += hugeInt.get(i) * (Math.pow(10, i));
    }
    return returnVal;
  }
  
  public void printHugeInt () {
    //System.out.println("Adding 111 + 444: ");
    for ( int i = hugeInt.size()-1; i >= 0; i-- ) {
    //for ( int i = 0; i < hugeInt.size(); i++ ) {
      System.out.print( hugeInt.get(i) + " ");
    }
  }
  
  public HugeUnsignedInt () {
  
  }
  
  public void hugeAdd ( HugeUnsignedInt int1, HugeUnsignedInt int2 ) {
    int int1Size = int1.hugeInt.size();
    int int2Size = int2.hugeInt.size();
    int diff, length;
    if ( int1.hugeInt.size() > int2.hugeInt.size() ) {
      diff = int1Size - int2Size;
      length = int1Size;
      for ( int i = 0; i < diff; i++ ) {
        int2.hugeInt.add(0); // padding with 0's
      }
    } else {
      diff = int2Size - int1Size;
      length  = int2Size;
      for ( int i = 0; i < diff; i++) {
        int1.hugeInt.add(0); // padding with 0's
      }
    }
    
    int carry = 0;
    for ( int i = 0; i < length; i++ ) {
      int val = int1.hugeInt.get(i) + int2.hugeInt.get(i) + carry;
      carry = val/10;
      val = val%10;
      hugeInt.add(val);

      if ( i == length -1 && carry > 0 ){
        hugeInt.add(1);
      }
    }
  }
  
  /* returns 1 if int1 > int2, 0 if int1 < int2, 2 if equal */
  public int hugeGreater ( HugeUnsignedInt int1, HugeUnsignedInt int2 ) {
    if ( int1.hugeInt.size() > int2.hugeInt.size() ) {
      return 1;
    } else if ( int1.hugeInt.size() < int2.hugeInt.size() ) {
      return 0;
    } else {
      int index = int1.hugeInt.size();
      for ( int i = index-1; i >= 0; i-- ) {
        if ( int1.hugeInt.get(i) > int2.hugeInt.get(i) ) {
          return 1;
        } else if ( int1.hugeInt.get(i) < int2.hugeInt.get(i) ) {
          return 0;
        }
      }
    }
    return 2;
  }
  
  
  /* (int1 - int2) , exits if subtraction would result in a negative number */
  public void hugeSub ( HugeUnsignedInt int1, HugeUnsignedInt int2 ) {
    if ( hugeGreater(int1, int2) == 0 ) {
      System.out.println("Subtraction of " + int1.hugeInt + " and " + int2.hugeInt + " would result in a negative number.");
      System.exit(1);
    }
    
    int diff, length;
    if ( int1.hugeInt.size() < int2.hugeInt.size() ) {
      diff = int2.hugeInt.size() - int1.hugeInt.size();
      length = int2.hugeInt.size();
      for ( int i = 0; i < diff; i++) { // padding with 0's
        int1.hugeInt.add(0); 
      }
    } else {
      diff = int1.hugeInt.size();
      length = int1.hugeInt.size();
      for ( int i = 0; i < diff; i++) { // padding with 0's
        int2.hugeInt.add(0);
      }
    }
    
    for ( int i = 0; i < length; i++) {
      int val;
      if ( int2.hugeInt.get(i) != 0 ) {
        if ( int1.hugeInt.get(i) > int2.hugeInt.get(i) ) {
          val = int1.hugeInt.get(i) - int2.hugeInt.get(i);
          hugeInt.add(val);
        } else if ( int1.hugeInt.get(i) < int2.hugeInt.get(i) ) {
          int1.hugeInt.set( i + 1, int1.hugeInt.get( i + 1 ) - 1);
          int1.hugeInt.set( i, int1.hugeInt.get(i) + 10 );
          val = int1.hugeInt.get(i) - int2.hugeInt.get(i);
          hugeInt.add(val);
        } else {
          hugeInt.add(0);
        }
      } else {
        hugeInt.add( int1.hugeInt.get(i) );
      }
    }
  }

  public void hugeMult ( HugeUnsignedInt int1, HugeUnsignedInt int2 ) {
    int int1Size = int1.hugeInt.size();
    int int2Size = int2.hugeInt.size();
    
    for ( int i = 0; i < int1Size+int2Size; i++ ) {
      hugeInt.add(0); // initialize class with 0's to hold product
    }
    
    for ( int i = 0; i < int2Size; i++ ) {
      int carry = 0;
      for ( int j = 0; j < int1Size; j++ ) {
        hugeInt.set(j+i, hugeInt.get(j+i)+carry+(int1.hugeInt.get(j)*int2.hugeInt.get(i)));
        carry = hugeInt.get(j+i)/10;
        hugeInt.set(j+i, hugeInt.get(j+i)%10);
        hugeInt.set( ((i+j)), hugeInt.get( (i+j) )%10 );
      }
      hugeInt.set( i+int1Size, hugeInt.get(i+int1Size)+carry );
    }
  }
  
  /**/
  public long hugeDiv ( long int1, HugeUnsignedInt int2 ) {
    long temp = int1;//.HugeIntValue();
    long temp2 = int2.HugeIntValue();
    
    return temp/temp2;
  }
  
  public void hugeExp (HugeUnsignedInt int1, int n) {
    for (int i=0; i<n; i++) {
      hugeMult(int1, int1);
    }
  }
  
  public void hugeMod (HugeUnsignedInt int1){
    
  }
}
