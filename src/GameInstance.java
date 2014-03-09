// Copyright (c) 2013, Junying Chen (Melbourne Student ID: 501770)
// <casperchen91@hotmail.com>

//Class for the GameInstance
public class GameInstance{

	//"turns" this is used track which player is making the move therefore allowing the GameSession method to output own wins. Odd numbers represent player 1 and even numbers represent player 2
	private int			Turns;
	
	//"count" is used for the counting variable in all forloops within this class
	private int			count; 

	private Player[]	competingPlayer	= new Player[2];
	
	private int			InitialnumberOfStones;
	private int			numberOfStones;
	private int			MaxTakeaway;
	private int			takeaway;
		
	
	//********************************************************************
	//Constructor which assigns the values for
	//both players first names and last names
	public GameInstance(int initialnumberofstones,
			int maxtakeaway,
			Player player1,
			Player player2){
		this.InitialnumberOfStones = initialnumberofstones;
		this.MaxTakeaway = maxtakeaway;
		this.competingPlayer[1] = player1;
		this.competingPlayer[0] = player2;
	}
	
	//********************************************************************
	//The method that will contain the game session
	public int GameSession(){
		
		//"whiletest" used to prevent the game from proceeding
		//if an invalid takeaway value is given.
		boolean whiletest;
		
		//Show the Initial number of stones and the maximum stone removal that is set
		//and also the first and last names of the players.
		System.out.println("Initial stone count: " + InitialnumberOfStones);
		System.out.println("Maximum stone removal: " + MaxTakeaway);
		System.out.println("Player 1: " + competingPlayer[1].getFirstName() +
									" " + competingPlayer[1].getLastName());
		System.out.println("Player 2: " + competingPlayer[0].getFirstName() +
									" " + competingPlayer[0].getLastName());
		
		numberOfStones = InitialnumberOfStones;
		Turns = 1;
		while(numberOfStones > 0){
			whiletest = true;
			while(whiletest){
				System.out.printf("\n%d stones left:", numberOfStones);
				displayStones();
				
				System.out.println(competingPlayer[Turns%2].getFirstName() +
						"'s turn" + " - remove how many?");
				
				if(competingPlayer[Turns%2] instanceof HumanPlayer){
					HumanPlayer	currentPlayer
						= (HumanPlayer) competingPlayer[Turns%2];
					takeaway = currentPlayer.move();					
				}else if(competingPlayer[Turns%2] instanceof NimAIPlayer){
					NimAIPlayer currentPlayer
						= (NimAIPlayer) competingPlayer[Turns%2];
					takeaway = currentPlayer.move(MaxTakeaway, numberOfStones);
				}

				try{
					if(takeaway >= 1 && takeaway <=MaxTakeaway && takeaway <= numberOfStones){
						whiletest = false;
					}else if((numberOfStones > MaxTakeaway && takeaway > MaxTakeaway) ||
							(numberOfStones > MaxTakeaway &&  takeaway <= 0)){
						throw new Exception("\nInvalid move. You must remove between " + 1 +
								" and " + MaxTakeaway + " stones");
					}else if(numberOfStones <= MaxTakeaway && takeaway > numberOfStones ||
							(numberOfStones <= MaxTakeaway &&  takeaway <= 0)){
						throw new Exception("\nInvalid move. You must remove between " + 1 +
								" and " + numberOfStones + " stones");
					}
				}catch(Exception f){
					System.out.println(f.getMessage());
				}
			}
			numberOfStones -= takeaway;
			Turns++;
		}
		
		System.out.println("\nGame Over"); //"Game Over prompt"
		
		//The if and else if statement that analyses
		//which player has won and announces that
		System.out.println(competingPlayer[Turns%2].getFirstName() +
				" " + competingPlayer[Turns%2].getLastName() + " wins!");
		
		//if turns is odd, player 1 wins. If turns is even, player 2 wins
		return Turns;
	}
	
	public void displayStones(){
		for(count = numberOfStones; count >= 1; count--){
			System.out.printf(" *");
			if(count==1){
				System.out.printf("\n");
			}
		}
	}

}
