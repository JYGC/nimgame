/*
 *  Copyright (c) 2013, Junying Chen (Melbourne Student ID: 501770)
 *  <casperchen91@hotmail.com>
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Arrays;

/*
 * The class for the game system "Nimsys"
 */
public class Nimsys{
	
	//Boolean variable used to determine if the while loop within the
	//method "MainSession" is to run (true) or stop (false).
	private boolean			ProgramRunning;	 
	
	private String			SaveFileName	= new String("players.dat");
	private Scanner			KeyboardInput	= new Scanner(System.in);
	
	//Counting variable for all forloops in this class
	private int				count;
	
	private StringTokenizer	UserInput;
	private int 			UserInputTokenCount;
	private String			Args			= new String();
	private String			InputCommand;
	private String[]		ArgsParts;
	private int				ArgsPartsCount;
	
	private GameInstance	GameSession;
	private Player[]		playerArray		= new Player[30];
	
	//The main function
	public static void main(String[] args){
		Nimsys StartNim = new Nimsys();
		StartNim.MainSession();
	}
	
	//The method which contains the code for game's main menu
	public void MainSession(){
		
		System.out.println("Welcome to Nim");
		
		this.ProgramRunning = true;
		
		for(count = 0; count <= playerArray.length - 1; count++){
			playerArray[count]	=	new Player();
			playerArray[count].changeUserName("0");
			playerArray[count].changeFirstName("0");
			playerArray[count].changeLastName("0");
			playerArray[count].changeNumberOfGamesPlayed(0);
			playerArray[count].changeNumberOfGamesWon(0);
			playerArray[count].updatePercentWin();
		}
		
		loadplayerdata();
		
		while(ProgramRunning){
			System.out.printf("\n>");
			
			//Assign userinput to StringToken
			this.UserInput = new StringTokenizer(KeyboardInput.nextLine()); 
			this.UserInputTokenCount = UserInput.countTokens();
			this.InputCommand = UserInput.nextToken();
			if(UserInputTokenCount == 1);
			else if(UserInputTokenCount >= 2){
				this.Args = UserInput.nextToken();
				
				this.ArgsParts = Args.split(",");
				this.ArgsPartsCount = ArgsParts.length;
				
			}
			
			/*Analysis user input*/
			try{
				//If exit is entered
				if(("exit").equals(InputCommand) == true){
					this.ProgramRunning = false;
					
				//If addplayer command is entered
				}else if(("addplayer").equals(InputCommand) == true){
					try{
						if(ArgsPartsCount == 3){
							addplayer(ArgsParts[0],ArgsParts[1],ArgsParts[2]);
						}else{
							throw new Exception("Incorrect number of arguments supplied to command.");
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					
				//If addaiplayer command is entered
				}else if(("addaiplayer").equals(InputCommand) == true){
					try{
						if(ArgsPartsCount == 3){
							addaiplayer(ArgsParts[0],ArgsParts[1],ArgsParts[2]);
						}else{
							throw new Exception("Incorrect number of arguments supplied to command.");
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}					
					
				//If removeplayer command is entered
				}else if(("removeplayer").equals(InputCommand) == true){
					if(UserInputTokenCount == 1){
						removeplayer();
					}else if(UserInputTokenCount >= 2){
						try{
							if(ArgsPartsCount == 1){
								removeplayer(ArgsParts[0]);
							}else{
								throw new Exception("Incorrect number of arguments supplied to command.");
							}
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				
				//If editplayer command is entered
				}else if(("editplayer").equals(InputCommand) == true){
					try{
						if(ArgsPartsCount == 3){
							editplayer(ArgsParts[0], ArgsParts[1], ArgsParts[2]);
						}else{
							throw new Exception("Incorrect number of arguments supplied to command.");
						}						
					}catch(Exception e){
						throw new Exception("Incorrect number of arguments supplied to command.");
					}
				
				//If resetstats command is entered
				}else if(("resetstats").equals(InputCommand) == true){
					if(UserInputTokenCount == 1){
						resetstats();
					}else if(UserInputTokenCount >= 2){
						try{
							if(ArgsPartsCount == 1){
								resetstats(ArgsParts[0]);
							}else{
								throw new Exception("Incorrect number of arguments supplied to command.");
							}
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				
				//If displayplayer command is entered
				}else if(("displayplayer").equals(InputCommand) == true){
					if(UserInputTokenCount == 1){
						displayplayer();
					}else if(UserInputTokenCount >= 2){
						try{
							if(ArgsPartsCount == 1){
								displayplayer(ArgsParts[0]);
							}else{
								throw new Exception("Incorrect number of arguments supplied to command.");
							}
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				
				//If rankings command is entered
				}else if(("rankings").equals(InputCommand) == true){
					rankings();
			
				//If startgame command is entered
				}else if(("startgame").equals(InputCommand) == true){
					try{
						if(ArgsPartsCount == 4){
							startgame(
									Integer.parseInt(ArgsParts[0]),
									Integer.parseInt(ArgsParts[1]),
									ArgsParts[2],
									ArgsParts[3]);
						}else{
							throw new Exception("Incorrect number of arguments supplied to command.");
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				//If startadvancedgame command is entered
				}else if(("startadvancedgame").equals(InputCommand)){
					try{
						if(ArgsPartsCount == 3){
							startadvancedgame(
									Integer.parseInt(ArgsParts[0]),
									ArgsParts[1],
									ArgsParts[2]);
						}else{
							throw new Exception("Incorrect number of arguments supplied to command.");
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}else{
					throw new Exception("'" + InputCommand + "'" +
							" is not a valid command.");
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		saveplayerdata();
	}
	
	
	//addplayer function
	public void addplayer(String username, String lastname, String firstname){
		
		boolean playerExists = false;
		
		for(count=0;count<=playerArray.length-1;count++){
			if(username.equals(playerArray[count].getUserName()) == true){
				playerExists = true;
			}
		}
		
		if(playerExists == false){
			int targetindex=-1;
			for(count=0; ("0").equals(playerArray[count].getUserName()) == false; count++){
				targetindex = count;
			}
			
			playerArray[targetindex + 1]	=	new HumanPlayer();
			
			playerArray[targetindex + 1].changeUserName(username);
			playerArray[targetindex + 1].changeFirstName(firstname);
			playerArray[targetindex + 1].changeLastName(lastname);
			playerArray[targetindex + 1].changeNumberOfGamesPlayed(0);
			playerArray[targetindex + 1].changeNumberOfGamesWon(0);
			playerArray[targetindex = 1].updatePercentWin();
			
		}else if(playerExists == true){
			System.out.println("The player already exists.");
		}
	}
	
	
	//addaiplayer function
	public void addaiplayer(String username, String lastname, String firstname){
		
		boolean playerExists = false;
		
		for(count=0;count<=playerArray.length-1;count++){
			if(username.equals(playerArray[count].getUserName()) == true){
				playerExists = true;
			}
		}
		
		if(playerExists == false){
			int targetindex=-1;
			for(count=0; ("0").equals(playerArray[count].getUserName()) == false; count++){
				targetindex = count;
			}
			
			playerArray[targetindex + 1]	=	new NimAIPlayer();
			
			playerArray[targetindex + 1].changeUserName(username);
			playerArray[targetindex + 1].changeFirstName(firstname);
			playerArray[targetindex + 1].changeLastName(lastname);
			playerArray[targetindex + 1].changeNumberOfGamesPlayed(0);
			playerArray[targetindex + 1].changeNumberOfGamesWon(0);
			playerArray[targetindex = 1].updatePercentWin();
			
		}
	}
	
	
	//removeplayer function (overloaded)
	public void removeplayer(){
		char yesno;
		System.out.println("Are you sure you want to remove all players? (y/n)");
		yesno = KeyboardInput.next().charAt(0);
		
		if(yesno == 'y'){
			for(count = 0; count <= playerArray.length - 1; count++){
				playerArray[count]	=	new Player();
				playerArray[count].changeUserName("0");
				playerArray[count].changeFirstName("0");
				playerArray[count].changeLastName("0");
				playerArray[count].changeNumberOfGamesPlayed(0);
				playerArray[count].changeNumberOfGamesWon(0);
				playerArray[count].updatePercentWin();
			}
		}
		
		/*
		 * Absorb an unneeded next line that is introducted in
		 * the previous calling of KeyboardInput Scanner
		 */
		KeyboardInput.nextLine();
	}
	public void removeplayer(String SpecifiedUserName){
		
		boolean playerExists = false;
		
		for(count = 0; count <= playerArray.length - 1; count++){
			if(SpecifiedUserName.equals(playerArray[count].getUserName()) == true){
				playerArray[count]	=	new Player();
				playerArray[count].changeUserName("0");
				playerArray[count].changeFirstName("0");
				playerArray[count].changeLastName("0");
				playerArray[count].changeNumberOfGamesPlayed(0);
				playerArray[count].changeNumberOfGamesWon(0);
				playerArray[count].updatePercentWin();
				playerExists = true;
			}
		}
		
		if(playerExists == false){
			System.out.println("The player does not exists.");
		}
	}
	
	
	//editplayer function
	public void editplayer(String username, String newlastname, String newfirstname){

		boolean playerExists = false;
		
		for(count = 0; count <= playerArray.length - 1; count++){
			if(username.equals(playerArray[count].getUserName()) == true){
				playerArray[count].changeFirstName(newfirstname);
				playerArray[count].changeLastName(newlastname);
				playerExists = true;
			}
		}
		
		if(playerExists == false){
			System.out.println("The player does not exists.");
		}
	}
	
	
	/*
	 * resetstats function
	 */
	public void resetstats(){
		char yesno;
		System.out.println("All you sure you want to reset all player statistics? (y/n)");
		yesno = KeyboardInput.next().charAt(0);
		
		if(yesno == 'y'){
			for(count = 0; count <= playerArray.length - 1; count++){
				playerArray[count].changeNumberOfGamesPlayed(0);
				playerArray[count].changeNumberOfGamesWon(0);
			}
			KeyboardInput.nextLine(); //This is used to absorb an unneeded next line that is introducted in the previous calling of KeyboardInput Scanner
		}
	}
	public void resetstats(String username){
		for(count = 0; count <= playerArray.length - 1; count++){
			if(username.equals(playerArray[count].getUserName()) == true){
				playerArray[count].changeNumberOfGamesPlayed(0);
				playerArray[count].changeNumberOfGamesWon(0);
			}
		}
	}
	
	
	/*
	 * displayplayer function (overloaded) - 
	 */
	public void displayplayer(){
		for(count = 0; count <= playerArray.length - 1; count++){
			if(("0").equals(playerArray[count].getUserName()) == false){
				System.out.printf("%s,%s,%s,%d games,%d wins\n",
						playerArray[count].getUserName(),
						playerArray[count].getFirstName(),
						playerArray[count].getLastName(),
						playerArray[count].getNumberOfGamesPlayed(),
						playerArray[count].getNumberOfGamesWon()
						);
			}
		}
		
	}
	public void displayplayer(String username){
		for(count = 0; count <= playerArray.length - 1; count++){
			if(username.equals(playerArray[count].getUserName()) == true){
				System.out.printf("%s,%s,%s,%d games,%d wins\n",
						playerArray[count].getUserName(),
						playerArray[count].getFirstName(),
						playerArray[count].getLastName(),
						playerArray[count].getNumberOfGamesPlayed(),
						playerArray[count].getNumberOfGamesWon()
						);
			}
		}
	}
	
	
	/*
	 * rankings function - take player data and rank
	 * them in descending order according to the ratio
	 * of number games win over number of games played
	 */
	public void rankings(){
		
		String percent = "%";		
		Arrays.sort(playerArray);
		
		for(Player temp: playerArray){
			if(("0").equals(temp.getUserName()) == false){
				System.out.printf("%d%s | %s games | %s %s\n",
						temp.getPercentWin(),
						percent,
						String.format("%02d", temp.getNumberOfGamesPlayed()),
						temp.getFirstName(),
						temp.getLastName()
						);
			}
		}
	}
	
	
	/* 
	 * startgame function - function to start a game session.
	 */
	public void startgame(
			int		initialnumberofstones,
			int		maxtakeaway,
			String	player1_username,
			String	player2_username){
		
		int		playerNumbers		= 0;
		int		player1index		= -1;
		int		player2index		= -1;
		int		winindex;
		
		for(count=0; count<=playerArray.length-1; count++){
			if(player1_username.equals(playerArray[count].getUserName()) == true){
				playerNumbers++;
				player1index = count;				
			}
			if(player2_username.equals(playerArray[count].getUserName()) == true){
				playerNumbers++;
				player2index = count;
			}
		}
		
		if(playerNumbers == 2){
			GameSession = new GameInstance(initialnumberofstones,
					maxtakeaway,
					playerArray[player1index],
					playerArray[player2index]);
			winindex = GameSession.GameSession();
			
			playerArray[player1index].changeNumberOfGamesPlayed(
					playerArray[player1index].getNumberOfGamesPlayed() + 1);
			playerArray[player2index].changeNumberOfGamesPlayed(
					playerArray[player2index].getNumberOfGamesPlayed() + 1);
			
			if(winindex%2 == 1){
				playerArray[player1index].changeNumberOfGamesWon(
						playerArray[player1index].getNumberOfGamesWon() + 1);
			}else if(winindex%2 == 0){
				playerArray[player2index].changeNumberOfGamesWon(
						playerArray[player2index].getNumberOfGamesWon() + 1);
			}
			
			playerArray[player1index].updatePercentWin();
			playerArray[player2index].updatePercentWin();
			
		}else if(playerNumbers == 1){
			System.out.println("One of the players does not exist.");
		}else if(playerNumbers == 0){
			System.out.println("All of the players do not exists.");
		}
	}
	
	
	/*
	 * startadvancedgame function - function
	 * to start an advanced game session.
	 */
	public void startadvancedgame(
			int		initialnumberofstones,
			String	player1_username,
			String	player2_username){
		
		int		playerNumbers		= 0;
		int		player1index		= -1;
		int		player2index		= -1;
		int		winindex;
		
		for(count=0; count<=playerArray.length-1; count++){
			if(player1_username.equals(playerArray[count].getUserName()) == true){
				playerNumbers++;
				player1index = count;				
			}
			if(player2_username.equals(playerArray[count].getUserName()) == true){
				playerNumbers++;
				player2index = count;
			}
		}
		
		if(playerNumbers == 2){
			GameSession = new AdvanceNimGame(
					initialnumberofstones,
					playerArray[player1index],
					playerArray[player2index]);
			
			winindex = GameSession.GameSession();
			
			playerArray[player1index].changeNumberOfGamesPlayed(
					playerArray[player1index].getNumberOfGamesPlayed() + 1);
			playerArray[player2index].changeNumberOfGamesPlayed(
					playerArray[player2index].getNumberOfGamesPlayed() + 1);
			
			if(winindex%2 == 1){
				playerArray[player1index].changeNumberOfGamesWon(
						playerArray[player1index].getNumberOfGamesWon() + 1);
			}else if(winindex%2 == 0){
				playerArray[player2index].changeNumberOfGamesWon(
						playerArray[player2index].getNumberOfGamesWon() + 1);
			}
			
			playerArray[player1index].updatePercentWin();
			playerArray[player2index].updatePercentWin();
			
		}else if(playerNumbers == 1){
			System.out.println("One of the players does not exist.");
		}else if(playerNumbers == 0){
			System.out.println("All of the players do not exists.");
		}
	}
	
	
	/*
	 * saveplayerdata function - saves player's username, first and last name
	 * no. of games played and number games won and also the type of player
	 * into text file player.dat
	 */
	public void saveplayerdata(){
		
		PrintWriter OutputFileStream;
		
		OutputFileStream = null;
		
		try{
			OutputFileStream = new PrintWriter(
					new FileOutputStream(SaveFileName));			
		}catch(FileNotFoundException x){
			System.out.println("Error opening file: " + SaveFileName);
			System.exit(0);
		}
		
		for(count=0; count<=playerArray.length-1; count++){
			if((playerArray[count].getUserName()).equals("0") == false){
				if(playerArray[count] instanceof HumanPlayer){
					OutputFileStream.println(
							playerArray[count].getUserName() +
							" " +
							playerArray[count].getFirstName() +
							" " +
							playerArray[count].getLastName() +
							" " +
							playerArray[count].getNumberOfGamesPlayed() +
							" " +
							playerArray[count].getNumberOfGamesWon() +
							" " +
							"HUMAN");
				}else if(playerArray[count] instanceof NimAIPlayer){
					OutputFileStream.println(
							playerArray[count].getUserName() +
							" " +
							playerArray[count].getFirstName() +
							" " +
							playerArray[count].getLastName() +
							" " +
							playerArray[count].getNumberOfGamesPlayed() +
							" " + 
							playerArray[count].getNumberOfGamesWon() +
							" " + 
							"NimAI");
				}
			}
		}
		
		OutputFileStream.flush();
		OutputFileStream.close();

	}
	
	
	/*
	 * loadplayerdata function - load player data stored in text file plyaer.dat
	 * and assign them to variables
	 */
	public void loadplayerdata(){
		
		Scanner		InputFileStream;
		String[]	SaveFileLineSegments;
		
		InputFileStream = null;
		try{
			InputFileStream = new Scanner(new FileInputStream(SaveFileName));
			for(count=0;
					count<=playerArray.length-1 && InputFileStream.hasNextLine() == true;
					count++){
				
				SaveFileLineSegments = (InputFileStream.nextLine()).split(" ");
				
				if(("HUMAN").equals(SaveFileLineSegments[5])){
					playerArray[count]	=	new HumanPlayer();
				}else if(("NIMAI").equals(SaveFileLineSegments[5])){
					playerArray[count]	=	new NimAIPlayer();
				}
				
				playerArray[count].changeUserName(
						SaveFileLineSegments[0]);
				playerArray[count].changeFirstName(
						SaveFileLineSegments[1]);
				playerArray[count].changeLastName(
						SaveFileLineSegments[2]);
				playerArray[count].changeNumberOfGamesPlayed(
						Integer.parseInt(SaveFileLineSegments[3]));
				playerArray[count].changeNumberOfGamesWon(
						Integer.parseInt(SaveFileLineSegments[4]));
				playerArray[count].updatePercentWin();
			}
		}catch(FileNotFoundException x){
			//Do nothing if no file is found
		}
		
	}
}