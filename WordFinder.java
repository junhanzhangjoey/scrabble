import java.util.*;
import java.io.*;
public class WordFinder {
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
        }catch(IllegalDictionaryException e){
            System.out.println(e.getMessage());
        }
    }

    private static void provideRack(AnagramDictionary dict){
        Scanner in = new Scanner(System.in);
        System.out.println("Type . to quit.");
        ScoreTable scoreTable = new ScoreTable();
        while(true){
            System.out.print("Rack?: ");
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
            System.out.println("All of the words with their scores (sorted by score):");
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
