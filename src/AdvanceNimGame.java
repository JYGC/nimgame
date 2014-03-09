
public class AdvanceNimGame extends GameInstance{
	
	private int			Turns;
	private int			count;
	private String		lastMove;
	private String[]	lastMoveParts	= new String[2];
	private Player[]	competingPlayer	= new Player[2];
	private int			InitialnumberOfStones;
	private int			numberOfStones;

	//This boolean array represents stones
	//avaliable and their positions	
	private boolean[]	available;	

	//Constructor
	public AdvanceNimGame(
			int		initialnumberofstones,
			Player	player1,
			Player	player2){
		
		super(
				initialnumberofstones,
				0,
				player1,
				player2);
		
		this.InitialnumberOfStones	= initialnumberofstones;
		this.competingPlayer[1]		= player1;
		this.competingPlayer[0]		= player2;
		
	}
	
	public int GameSession(){
		
		boolean	whiletest;
		
		available		= new boolean[InitialnumberOfStones];
		numberOfStones	= InitialnumberOfStones;
		
		for(count = 0; count <= available.length-1; count++){
			available[count] = true;
		}

		//Show the Initial number of stones and the maximum stone removal that is set
		//and also the first and last names of the players.
		System.out.println("Initial stone count: " + InitialnumberOfStones);
		System.out.printf("Maximum stone removal:");
		displayStones();
		System.out.println("Player 1: " + competingPlayer[1].getFirstName() +
									" " + competingPlayer[1].getLastName());
		System.out.println("Player 2: " + competingPlayer[0].getFirstName() +
									" " + competingPlayer[0].getLastName());
		
		Turns = 1;
		while(testAllStonesGone(available) == false){
			whiletest = true;
			while(whiletest){
				System.out.printf("\n%d stones left:", numberOfStones);
				displayStones();
				
				System.out.println(competingPlayer[Turns%2].getFirstName() +
						"'s turn" + " - remove how many?");
				
				if(competingPlayer[Turns%2] instanceof HumanPlayer){
					HumanPlayer currentPlayer	= (HumanPlayer) competingPlayer[Turns%2];
					lastMove = currentPlayer.advancedMove();
				}
				if(competingPlayer[Turns%2] instanceof NimAIPlayer){
					NimAIPlayer currentPlayer	= (NimAIPlayer) competingPlayer[Turns%2];
					lastMove = currentPlayer.advancedMove(available, lastMove);
				}
				lastMoveParts = lastMove.split(" ");
				
				try{
					if(lastMoveParts.length == 2){
						if(Integer.parseInt(lastMoveParts[1]) == 1){
							if(available[Integer.parseInt(lastMoveParts[0]) - 1] == true){
								whiletest = false;
								available[Integer.parseInt(lastMoveParts[0]) - 1] = false;
								this.numberOfStones -= 1;
							}else{
								throw new Exception("Invalid move.");
							}
						}else if(Integer.parseInt(lastMoveParts[1]) == 2){
							if(available[Integer.parseInt(lastMoveParts[0]) - 1] == true &&
									available[Integer.parseInt(lastMoveParts[0])] == true){
								whiletest = false;
								available[Integer.parseInt(lastMoveParts[0]) - 1]	= false;
								available[Integer.parseInt(lastMoveParts[0])]		= false;
								this.numberOfStones -= 2;
							}else{
								throw new Exception("Invalid move.");
							}
						}else{
							throw new Exception("Invalid move.");
						}
					}else{
						throw new Exception("Invalid move.");
					}
				}catch(Exception e){
					System.out.println("\n" + e.getMessage());
				}
			}
			Turns++;
		}
		
		System.out.println("\nGame Over"); //"Game Over prompt"
		
		//The if and else if statement that analyses
		//which player has won and announces that
		System.out.println(competingPlayer[(Turns + 1)%2].getFirstName() +
				" " + competingPlayer[(Turns + 1)%2].getLastName() + " wins!");
		
		//if turns + 1 is odd, player 1 wins. If turns is even, player 2 wins
		return (Turns + 1);
	}
	
	public void displayStones(){
		for(count = 0; count <= available.length - 1; count++){
			System.out.printf(" <%d,",count+1);
			if(available[count] == true){
				System.out.printf("*");
			}else if(available[count] == false){
				System.out.printf("X");
			}
			System.out.printf(">");
			if(count == available.length - 1){
				System.out.printf("\n");
			}
		}
	}
	
	public boolean testAllStonesGone(boolean array[]){
		for(boolean b : array){
			if(b == true) return false;
		}
		return true;
	}
	
}
