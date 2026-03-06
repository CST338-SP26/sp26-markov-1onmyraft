/*
Title: Markov.java
Abstract: Program that randomly generates sentences based off words from an input file.
Author: Tyler Fleming
Date: 03/05/2026
 */
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Markov{
    private static String BEGINS_SENTENCE = "__$";
    private static String PUNCTUATION_MARKS = ".!?$";
    private HashMap<String, ArrayList<String>> words;
    private String prevWord;


    /**
     * Constructor for Markov Class
     *
     * Initializes instance variables.
     *
     */
    public Markov(){
        words = new HashMap<>();
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    /**
     * Builds sentences from our word list.
     *
     * Gets a random word from the beginning sentence list
     * then uses that word to generate more.
     *
     * @return String - the sentence randomly generated
     */
    public String getSentence(){
        StringBuilder sentence = new StringBuilder();
        String currentWord = randomWord(BEGINS_SENTENCE);
        sentence.append(currentWord).append(" ");

        int maxWords = 100;
        int count = 0;

        do {
            currentWord = randomWord(currentWord);
            sentence.append(currentWord).append(" ");
            count++;
           // List<String> keysAsList = new ArrayList<>(words.keySet());
           // currentWord = keysAsList.get((int)(Math.random() * keysAsList.size()));
        }while (!endsWithPunctuation(currentWord) && count < maxWords);

        return sentence.toString().trim();
    }


    /**
     * Fills out word list from a file line by line.
     *
     * Throws IOException for wrong file name.
     *
     * @param file the file used
     */
    public void addFromFile(String file)  {
        try{
            File f = new File(file);
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
        line = line.replaceAll("\n", "").replaceAll("\t", "");
        if (line.isEmpty()) return;
        String[] split = line.split(" ");


        for (String s : split) {
            s = s.replaceAll(" ", "");
            if (s.isEmpty()) continue;
            addWord(s);
        }
    }


    /**
     * Checks if a word ends with punctuation.
     *
     * A word ends with punctuation if the last
     * character is '.', '!', or '?'.
     *
     * @param word the word considered
     * @return true if the word ends with punctuation;
     * false otherwise
     */
    public static boolean endsWithPunctuation(String word){
        for (int i = 0; i < PUNCTUATION_MARKS.length();i++)
            if (word.endsWith(PUNCTUATION_MARKS.charAt(i) + "")) {
               return true;
            }

        return false;
    }

}