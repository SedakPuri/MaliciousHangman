/**
 * Sedak Puri
 * Intro to CS 3
 * Professor Albow
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Welcome to Hangman!");
		Scanner keyboard = new Scanner(System.in);
		boolean gameState = true;

		//User input and initial setup
		System.out.println("Enter the word length (above 1-28): ");
		int letterslength = (keyboard.nextInt());
		System.out.println("Enter the ammount of guesses (1+): ");
		int guesses = keyboard.nextInt();
		Dictionary d = new Dictionary(letterslength, guesses);
		System.out.println(d.getPartialWord());

		//Game Loop
		GameLoop:
			while(true) {
				System.out.println("Enter a letter to guess: ");
				String guess = keyboard.next();
				gameState = d.guess(guess);
				if (!gameState) {
					System.out.println("Game over! Would you like to keep playing? Y/N");
					while(true) {
						String response = keyboard.next();
						if(response.trim().equalsIgnoreCase("n")) {
							break GameLoop;
						} else if (response.trim().equalsIgnoreCase("y")){
							System.out.println("Enter the word length (above 1-28): ");
							letterslength = keyboard.nextInt();
							System.out.println("Enter the ammount of guesses (1+): ");
							guesses = keyboard.nextInt();
							d = new Dictionary(letterslength, guesses);
							System.out.println(d.getPartialWord());
							break;
						} else {
							System.out.println("Invalid response! Please try again! Would you like to keep playing? Y/N");
						}
					}
				}
			}
		System.out.print("\n\nThanks for playing Hangman!");
	}
}
