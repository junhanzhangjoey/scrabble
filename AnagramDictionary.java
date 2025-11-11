// Name: 
// USC NetID: 
// CS 455 PA4
// Spring 2025

import java.util.*;
import java.io.*;

/**
   A dictionary of all anagram sets. 
   Note: the processing is case-sensitive; so if the dictionary has all lower
   case words, you will likely want any string you test to have all lower case
   letters too, and likewise if the dictionary words are all upper case.
 */
public class AnagramDictionary {
   private Map<String, ArrayList<String>> anagramMap;
   private Set<String> wordsSet;
   private boolean isUpperCaseDictionary;
   /**
      Create an anagram dictionary from the list of words given in the file
      indicated by fileName.  
      @param fileName  the name of the file to read from
      @throws FileNotFoundException  if the file is not found
      @throws IllegalDictionaryException  if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException,
                                                    IllegalDictionaryException {
      this.anagramMap = new HashMap<>();
      this.wordsSet = new HashSet<>();
      boolean firstWord = true;
      try(Scanner in = new Scanner(new File(fileName))){
         while(in.hasNext()){
            String word = in.next();
            if(firstWord){
               isUpperCaseDictionary = word.equals(word.toUpperCase());
               firstWord = false;
            }
            if(!wordsSet.add(word)){
               throw new IllegalDictionaryException("ERROR: Illegal dictionary: dictionary file has a duplicate word: " + word);
            }
            String key = sortWord(word);
            anagramMap.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
         }
      }                                
   }
   
   
   /**
      Get all anagrams of the given string. This method is case-sensitive.
      E.g. "CARE" and "race" would not be recognized as anagrams.
      @param s string to process
      @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String string) {
      return new ArrayList<String>(anagramMap.getOrDefault(sortWord(string), new ArrayList<>()));
   }
   public boolean isUpperCaseDict(){
      return isUpperCaseDictionary;
   }
   
   private String sortWord(String word) {
      char[] arrayChars = word.toCharArray();
      Arrays.sort(arrayChars);
      String res = new String(arrayChars);
      return res;
   }
}