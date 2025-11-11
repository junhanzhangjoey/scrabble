import java.util.*;
public class ScoreTable{
    private Map<Character, Integer> letterScore;
    
    public ScoreTable(){
        this.letterScore = new HashMap<>();
        initialTable();
    }
    public int getScore(String word){
        int score = 0;
        for(char c : word.toLowerCase().toCharArray()){
            if(Character.isLetter(c)){
                score+=letterScore.get(c);
            }
        }
        return score;
    }
    private void initialTable(){
        addLetters("AEIOULNSTR",1);
        addLetters("DG", 2);
        addLetters("BCMP", 3);
        addLetters("FHVWY", 4);
        addLetters("K", 5);
        addLetters("JX", 8);
        addLetters("QZ", 10);
    }

    private void addLetters(String letters, int score){
        for(Character c : letters.toCharArray()){
            this.letterScore.put(Character.toLowerCase(c), score);
        }
    }
    // private Map<Character, Integer> getTable(){
    //     return letterScore;
    // }
    // public static void main(String[] args) {
    //     ScoreTable table = new ScoreTable();
    //     System.out.println(table.getTable().get('q'));
    // }
}