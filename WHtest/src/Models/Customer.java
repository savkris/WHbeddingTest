package Models;

import java.util.ArrayList;
import java.util.List;

public class Customer 
{
	public long CustomerID;
	public Boolean IsUnusualWinner;
	public long TotalWonBets;
	public long AverageBetAmount;
	public List<Bet> SettledBetsList = new ArrayList<Bet>();
	public List<Bet> UnSettledBetsList = new ArrayList<Bet>();
	
}
