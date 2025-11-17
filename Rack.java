// Name: 
// USC NetID: 
// CS 455 PA4
// Spring 2025

import java.util.*;
/**
   A Rack of Scrabble tiles.
   This class represents a multiset of characters based on a given input string.
   It provides functionality to compute all possible subset strings that can be
   formed from the Rack, accounting for multiplicities of characters.
*/

public class Rack {
   private String unique;
   private int[] mult;
   /**
      Constructs a Rack from a given input string of tiles.
      The constructor converts the input into a multiset representation:
         - Extracts only alphabetic characters
         - Counts multiplicities using a TreeMap so the resulting keys are sorted
         - Builds the `unique` string of distinct characters
         - Builds the corresponding `mult` array of counts

      Example:
         input = "baba"
         unique = "ab"
         mult = [2, 2]

      @param rack the string of Scrabble tiles entered by the user
    */
   public Rack(String rack){
      Map<Character, Integer> letterCount = new TreeMap<>();

      for(Character c : rack.toCharArray()){
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
   /**
   Returns all possible subsets of the Rack represented by this object.
   The Rack is treated as a multiset of characters, summarized by:
      - unique: a sorted string of distinct characters
      - mult: an array giving the multiplicity of each character in unique
   This method simply calls the recursive helper allSubsets(), starting
   at index 0, to generate every sub-multiset of the Rack.

   Each returned subset is represented as a String containing 0 or more
   occurrences of the letters from the Rack. For example, if the Rack is
   "aab", the subsets include "", "a", "aa", "b", "ab", "aab", etc.

   @return an ArrayList of all subset Strings that can be formed from the Rack.
           The order of subsets is consistent with the recursive generation
           used by allSubsets().
 */
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
   /**
      Accessor for the sorted unique letter string.

      @return the string of distinct characters in this Rack.
    */
   public String getUnique(){
      return this.unique;
   }

   /**
      Accessor for the multiplicity array.

      @return the multiplicities associated with the characters in `unique`.
   */
   public int[] getMult(){
      return this.mult;
   }
}