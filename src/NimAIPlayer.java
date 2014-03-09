// Copyright (c) 2013, Junying Chen (Melbourne Student ID: 501770)
// <casperchen91@hotmail.com>

public class NimAIPlayer extends Player implements Testable{
	
	private int	count;
	private int	takeaway;
	
	public NimAIPlayer(){}
	
	int move(int MaxTakeaway, int numberOfStones){
		takeaway = 0;
		for(count=0; takeaway < 1 || takeaway > 3;count++){
			takeaway = numberOfStones - count*(MaxTakeaway + 1) - 1;
		}
		if(takeaway > 0){
			return takeaway;
		}
		
		return 1;
	}
	
	public String advancedMove(boolean[] available, String lastMove){
		return "yet to be def";
	}
}