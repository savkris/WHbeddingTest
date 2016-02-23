package Models;

public class Bet {

	public Boolean IsSettled = false;	
	public Boolean IsRisky = false; 
	public Boolean IsHighRisk = false;
	public long EventID;
	public long ParticipantID; 
	public long StakeAmount;
	public long WinAmount; 	// if no win == 0	
}
