// Copyright (c) 2013, Junying Chen (Melbourne Student ID: 501770)
// <casperchen91@hotmail.com>

import java.util.Scanner;


public class HumanPlayer extends Player{

	private int 	takeaway;
	private String	takeawayposition;
	
	//Initialize Scanner for inputting number of Stones;
	private Scanner	InputStoneNumber	= new Scanner(System.in); 
	
	public int move(){
		takeaway = InputStoneNumber.nextInt();
		return takeaway;
	}
	
	public String advancedMove(){
		takeawayposition = InputStoneNumber.nextLine();
		return takeawayposition;
	}
}
