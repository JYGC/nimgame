// Copyright (c) 2013, Junying Chen (Melbourne Student ID: 501770)
// <casperchen91@hotmail.com>


public class Player implements Comparable<Player>{
	private String	UserName	= new String();
	private String	FirstName	= new String();
	private String	LastName	= new String();
	private int		NumberOfGamesPlayed;
	private int		NumberOfGamesWon;
	private int		PercentWin;
	
	//Mutator method for UserName
	public void changeUserName(String username){
		this.UserName = username;
	}
	
	//Mutator method for FirstName
	public void changeFirstName(String firstname){
		this.FirstName = firstname;
	}
	
	//Mutator method for LastName
	public void changeLastName(String lastname){
		this.LastName = lastname;
	}
	
	//Mutator method for NumberOfGamesPlayed
	public void changeNumberOfGamesPlayed(int numberofgamesplayed){
		this.NumberOfGamesPlayed = numberofgamesplayed;
	}
	
	//Mutator method for NumberOfGamesWon
	public void changeNumberOfGamesWon(int numberofgameswon){
		this.NumberOfGamesWon = numberofgameswon;
	}
	
	
	//Accessor method for UserName
	public String getUserName(){
		return new String(UserName);
	}
	
	//Accessor method for FirstName
	public String getFirstName(){
		return new String(FirstName);
	}
	
	//Accessor method for LastName
	public String getLastName(){
		return new String(LastName);
	}
	
	//Accessor method for NumberOfgamesPlayed
	public int getNumberOfGamesPlayed(){
		return NumberOfGamesPlayed;
		
	}
	
	//Accessor method for NumberOfgamesWon
	public int getNumberOfGamesWon(){
		return NumberOfGamesWon;
	}
	
	//Accessor method for PercentWin
	public int getPercentWin(){
		return PercentWin;
	}
	
	//Method to assist in ranking the Player objects by PercentageWin
	public int compareTo(Player comparePlayer){
		int comparePercentWin = ((Player) comparePlayer).getPercentWin();
		return comparePercentWin - this.PercentWin;
	}
	
	
	//This method is used to update Pecentwin
	public void updatePercentWin(){
		if(NumberOfGamesPlayed != 0){
			this.PercentWin = ((int)(100*((float) NumberOfGamesWon)/NumberOfGamesPlayed));
		}else{
			this.PercentWin = 0;
		}
	}
	
}