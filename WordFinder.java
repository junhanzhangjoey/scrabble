import java.util.*;
import java.io.*;

/**
 * WordFinder: main program for PA4.
 *
 * This program loads a dictionary of anagrams, prompts the user for Rack inputs,
 * and prints all valid words that can be formed from those tiles, together with
 * their Scrabble scores. The words are grouped and printed in descending score
 * order.
 *
 * Input continues until the user types "." to quit.
 */
public class WordFinder {
    /**
     * Program entry point. Loads the dictionary file (default: "sowpods.txt")
     * unless another file name is supplied as a command-line argument.
     *
     * Handles:
     *   - Missing dictionary file → prints required error message and exits.
     *   - Dictionary with duplicate words → handled by AnagramDictionary and exits.
     *
     * @param args optional dictionary file name
     */
    public static void main(String args[]){
        String fileName = "sowpods.txt";
        try{
            if(args.length > 0){
                fileName = args[0];
            }
            AnagramDictionary dict = new AnagramDictionary(fileName);
            provideRack(dict);                        
        }catch(FileNotFoundException e){
            System.out.println("ERROR: Dictionary file \"" + fileName + "\" does not exist.");
            System.out.println("Exiting program.");
            System.exit(0);
        }catch(IllegalDictionaryException e){
            System.out.println(e.getMessage());
            System.out.println("Exiting program.");
            System.exit(0);
        }
    }
    /**
     * Repeatedly prompts the user for a Rack input, then:
     *   1. Builds a Rack object
     *   2. Generates all subsets of the rack
     *   3. For each subset, retrieves all dictionary anagrams
     *   4. Computes Scrabble scores
     *   5. Organizes results in a TreeMap sorted by descending score
     *   6. Prints number of words and the sorted list of scored results
     *
     * The loop terminates when the user types ".".
     *
     * @param dict the AnagramDictionary used for anagram lookup
     */
    private static void provideRack(AnagramDictionary dict){
        Scanner in = new Scanner(System.in);
        System.out.println("Type . to quit.");
        ScoreTable scoreTable = new ScoreTable();
        while(true){
            System.out.print("Rack? ");
            String input = in.nextLine();
            if(input.equals(".")){
                break;
            }
            Rack rack = new Rack(input);
            ArrayList<String> subset = rack.getAllSubsets();
            TreeMap<Integer, TreeSet<String>> scoreResult = new TreeMap<>(Collections.reverseOrder());
            int wordCount = 0;
            for(String str : subset){
                int score = scoreTable.getScore(str);
                ArrayList<String> anagrams = dict.getAnagramsOf(str);
                wordCount += anagrams.size();
                scoreResult.computeIfAbsent(score, k -> new TreeSet<>()).addAll(anagrams);
            }
            System.out.println("We can make " + wordCount + " words from \"" + input + "\"");
            if(wordCount > 0){
                System.out.println("All of the words with their scores (sorted by score):");
            }
            for(Map.Entry<Integer, TreeSet<String>> entry : scoreResult.entrySet()){
                int score = entry.getKey();
                for(String str : entry.getValue()){
                    System.out.println(score + ": " + str);
                }
            }
        }
        in.close();
    }
}
