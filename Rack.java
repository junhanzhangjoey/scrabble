// Name: 
// USC NetID: 
// CS 455 PA4
// Spring 2025

import java.util.ArrayList;
import java.util.*;
/**
   A Rack of Scrabble tiles
 */

public class Rack {
   private String unique;
   private int[] mult;
   
   public Rack(String rack){
      Map<Character, Integer> letterCount = new TreeMap<>();
      for(Character c : rack.toLowerCase().toCharArray()){
         if(Character.isLetter(c)){
            letterCount.merge(c, 1, Integer::sum);
         }
      }
      this.mult = new int[letterCount.size()];
      StringBuilder sb = new StringBuilder();
      int i = 0;
      for(Map.Entry<Character, Integer> entry: letterCount.entrySet()){
         sb.append(entry.getKey());
         this.mult[i++] = entry.getValue();
      }
      this.unique = sb.toString();
   }
   public ArrayList<String> getAllSubsets(){
      return allSubsets(this.unique, this.mult, 0);
   }

   /**
      Finds all subsets of the multiset starting at position k in unique and mult.
      unique and mult describe a multiset such that mult[i] is the multiplicity of the char
           unique.charAt(i).
      PRE: mult.length must be at least as big as unique.length()
           0 <= k <= unique.length()
      @param unique a string of unique letters
      @param mult the multiplicity of each letter from unique.  
      @param k the smallest index of unique and mult to consider.
      @return all subsets of the indicated multiset.  Unlike the multiset in the parameters,
      each subset is represented as a String that can have repeated characters in it.
      @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();
      
      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }
      
      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);
      
      // prepend all possible numbers of the first char (i.e., the one at position k) 
      // to the front of each string in restCombos.  Suppose that char is 'a'...
      
      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {   
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets 
                                                        // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));  
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }
      
      return allCombos;
   }   
   public String getUnique(){
      return this.unique;
   }
   public int[] getMult(){
      return this.mult;
   }
}