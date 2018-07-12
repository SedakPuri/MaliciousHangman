/**
 * Sedak Puri
 * Intro to CS 3
 * Professor Albow
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Dictionary {
	private HashSet<String> dictionary, words;
	private ArrayList<String> guessedLetters;
	private String finalWord, partialWord;
	private boolean regularHangman;
	private int length, guesses;

	/**
	 * Constructor for the dictionary class
	 * @param length is the length of the word
	 * @param guesses is the number of guesses
	 */
	public Dictionary(int length, int guesses) {
		this.dictionary = new HashSet<>();
		this.words = new HashSet<>();
		this.guessedLetters = new ArrayList<>();
		this.regularHangman = false;
		this.length = length;
		this.guesses = guesses;
		this.partialWord = "";
		this.finalWord = "";

		//Initializing partialWord
		for (int i = 0; i < length; i++) {
			partialWord += "_";
		}

		//Importing the file and reading in all words into a Set
		Scanner file = null;
		try {
			file = new Scanner(new FileInputStream("dictionary.txt"));
			while(file.hasNext()) {
				String currentWord = file.next();
				dictionary.add(currentWord);	
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not find dictionary file. Exiting system.");
			System.exit(0);
		}

		//Adding only words that have the target length to the set of words
		for(String s: dictionary) {
			if (s.length() == length) {
				words.add(s);
			}
		}
	}

	/**
	 * Method to execute a guess
	 * @param s is the string representation of the guessed letter
	 * @return whether the game continues or not
	 */
	public boolean guess(String s) {
		guesses--;
		//Case: The guess was invalid
		if (s.length() > 1 || guessedLetters.contains(s)) {
			System.out.println("Invalid Guess, Try again!");
			guesses++;
			return true;
		} 
		guessedLetters.add(s);
		
		//Case: Out of guesses
		if (guesses == 0) {
			System.out.println("You lost! The correct word was: " + randomWord(words));										
			return false;
		}

		//The very first iteration of constantly re-deciding the word 
		if (!regularHangman) {
			finalWord = removeWords(s);		//finalWord is an empty string until the game becomes regular hang-man
		} 

		//Case: Playing hang-man and reformatting if the user enters a character that fills a "_"
		if (regularHangman && (finalWord != "")) {
			reformat(s.charAt(0));
		}

		//Case: Game was won!
		if (partialWord.equals(finalWord)) {																						
			System.out.println("\nCongratulations! You won! \nFinal word: \"" + finalWord + "\"");
			return false;
		}
		
		System.out.println(words);
		System.out.println("\n" + partialWord);
		return true;
	}

	/**
	 * Method that finds words that contain the letter and removes them
	 * @param s is the string letter that is trying to be removed
	 * @return the string of the word if it is chosen or an empty string if not
	 */
	private String removeWords(String s) {
		HashSet<String> group1 = new HashSet<>();		//Words containing guessed letter
		HashSet<String> group2 = new HashSet<>();		//Words not containing guessed letter
		for(String k: words) {
			if ((k.length() == length) && k.contains(s)) {
				group1.add(k);
			} else if ((k.length() == length) && !k.contains(s.trim())){
				group2.add(k);
			}
		}

		//Case: The guess would remove all words so we revert to regular hang-man so the user doesn't find out
		if (group2.isEmpty()) {
			regularHangman = true;																				
			return randomWord(group1);
		}

		//Case: The set of all possible words is readjusted to words that haven't been guessed yet
		words = group2;
		return "";
	}

	/**
	 * Method to return a random word from the input set
	 * @param s is the set of which you want a random string
	 * @return a string from this set
	 */
	private String randomWord(HashSet<String> s) {
		Random rng = new Random();
		int index = rng.nextInt(s.size());														

		//Making an arraylist to find the random index of
		List<String> list = new ArrayList<>(s.size());
		for (String e : s)
			list.add(e);

		return list.get(index);
	}

	/**
	 * Method to add letters
	 * @param s is the letter you wish to add
	 * @return the new word with the letter removed
	 */
	private void reformat(char c) {
		String newWord = "";
		for (int i = 0; i < finalWord.length(); i++) {
			if (finalWord.charAt(i) == c) {
				newWord += c;
			} else if (partialWord.charAt(i) != '_'){
				newWord += partialWord.charAt(i);
			} else {
				newWord += "_";
			}
		}
		partialWord = newWord;
	}
                                                                                          
	/**
	 * Getter method for the partialWord variable
	 * @return the partial word
	 */
	public String getPartialWord() {
		return partialWord;
	}
}
