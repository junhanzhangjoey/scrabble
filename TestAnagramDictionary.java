import java.io.FileNotFoundException;

public class TestAnagramDictionary {
    public static void main(String[] args){
        try{
            String file = "testFiles/duplicate.txt";
            AnagramDictionary dic = new AnagramDictionary(file);
            System.out.println(dic.getAnagramsOf("apple"));
        }
        catch(FileNotFoundException e){
            System.out.println("fileNotFound");
        }
        catch(IllegalDictionaryException e){
            System.out.println(e.getMessage());
        }
     }
}
