import java.io.File;
import java.io.IOException;
import java.util.*;


public class Markov{
    private static String BEGINS_SENTENCE = "__$";
    private static String PUNCTUATION_MARKS = ".!?$";
    private HashMap<String, ArrayList<String>> words;
    private String prevWord;

    public Markov(){
        words = new HashMap<>();
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    public String getSentence(){
        StringBuilder sentence = new StringBuilder();
        String currentWord = randomWord(BEGINS_SENTENCE);
        sentence.append(currentWord).append(" ");

        do {
            currentWord = randomWord(currentWord);
            sentence.append(currentWord).append(" ");

           // List<String> keysAsList = new ArrayList<>(words.keySet());
           // currentWord = keysAsList.get((int)(Math.random() * keysAsList.size()));
        }while (!endsWithPunctuation(currentWord));

        return sentence.toString().trim();
    }

    public void addFromFile(String s)  {
        try{
            File f = new File(s);
            Scanner in = new Scanner(f);
            while (in.hasNextLine())
                addLine(in.nextLine());
        }catch (IOException e){
            System.out.println("Error in Filename");
        }
    }
    void addWord(String currentWord){
        boolean found  = Markov.endsWithPunctuation(prevWord);

        if (found)
            words.get(BEGINS_SENTENCE).add(currentWord);
        else {
            if (!words.containsKey(prevWord))
                words.put(prevWord, new ArrayList<String>());
            words.get(prevWord).add(currentWord);
        }

        prevWord = currentWord;
    }

    String randomWord(String word){
        List<String> arr = words.get(word);
        if (arr == null || arr.isEmpty()) return "";
        return arr.get((int)(Math.random() * arr.size()));
    }
    public String toString(){
        return words.toString();
    }
    HashMap<String, ArrayList<String>> getWords(){
        return words;
    }

    void addLine(String line){
        if (line.isEmpty()) return;

        String[] split = line.split(" ");

        for (String s : split)
            addWord(s);
    }

    public static boolean endsWithPunctuation(String word){
        for (int i = 0; i < PUNCTUATION_MARKS.length();i++)
            if (word.endsWith(PUNCTUATION_MARKS.charAt(i) + "")) {
               return true;
            }

        return false;
    }

}