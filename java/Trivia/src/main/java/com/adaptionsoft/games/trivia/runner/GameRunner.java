
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean gameMustContinue;

	public static void main(String[] args) {
		Game game = new Game();
		
		game.AddPlayer("Chet");
		game.AddPlayer("Pat");
		game.AddPlayer("Sue");
		
		Random rand = new Random();
	
		do {
			
			game.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				gameMustContinue = game.wrongAnswer();
			} else {
				gameMustContinue = game.wasCorrectlyAnswered();
			}
			
			
			
		} while (gameMustContinue);
		
	}
}
