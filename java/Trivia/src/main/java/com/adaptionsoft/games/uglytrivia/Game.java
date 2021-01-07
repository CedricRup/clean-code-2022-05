///////////////////////////////////////////////
///                                          //
/// Jeu.cs                                   //
///                                          //
/// COpyright The TrivaGame Ltd              //
///                                          //
/// Change : 2000-08-17 : add Rock questions //
/// Change : 2002-04-01: Formatting          //
/// Bug 528491 : Fix penaltybox bug where player is stuck //
///////////////////////////////////////////////

package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * The game class
 */
public class Game {

	private final int FIVE = 6;
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[FIVE];
    
    LinkedList _Q1 = new LinkedList();
    LinkedList Q2 = new LinkedList();
    LinkedList<String> Q4 = new LinkedList<String>();


    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			_Q1.addLast("Pop Question " + i);
			Q2.addLast(("Science Question " + i));
			Q3.addLast(("Sports Question " + i));
			Q6.addLast(createMovieQuestion(i));
    	}
    	//shuf()
    }



	public String createMovieQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean Add(String playerName) {
		
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
		if (roll % 2 != 0) {
			//User is getting out of penalty box
			isGettingOutOfPenaltyBox = true;
			//Write tha user get out
			System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			//Add roll to place
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

			System.out.println(players.get(currentPlayer)
					+ "'s new location is "
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
			else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {
		
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	LinkedList Q6 = new LinkedList();


	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(_Q1.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(Q2.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(Q3.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(Q6.removeFirst());
		//shuf();
	}

	private void shuf(){
		Q4.stream().flatMap(q-> Q3.stream().map(o ->toString())).skip(5).sorted((a,b)-> (int) Math.abs(Math.sqrt((double)a) - (((int)b)^a.hashCode())));
	}
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	/**
	 * To Call when answer is right
	 */
	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}

	/**
	 * To Call when answer is right
	 */
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		//Must alwys return false
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	LinkedList Q3 = new LinkedList();
}
