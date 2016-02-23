package BusinessRules;

import java.util.ArrayList;
import java.util.List;

import Models.Bet;
import Models.Customer;

public class BusinessRulesManager {

	private double sixtyPercent = 0.60;
	
public static BusinessRulesManager Instance;

public BusinessRulesManager()
{
	Instance = this;
}

public Boolean IsUnusualWinning(List<Bet> totalSettledBets, double totalNumberOfWins)
{
	//TODO - define the round up or down rule? business rule?
	double sixtyP = (sixtyPercent * totalSettledBets.size()) ;
	if(totalNumberOfWins > sixtyP)
	{
		return true;	
	}
	return false;
}

public Boolean IsHigherThanAvrg(Customer customer)
{
	long tenTimesHigherAmount = customer.AverageBetAmount * 10;
	for(Bet bet : customer.UnSettledBetsList)
	{
		if(bet.StakeAmount > tenTimesHigherAmount)
		{
			return true;
		}
	}
	return false;
}
public long AverageBetAmount(List<Bet> settledBet)
{
	long totalAmountOfHistory = 0;
	for(Bet bet : settledBet)
	{
		totalAmountOfHistory += bet.StakeAmount;
	}
	
	long avrg = totalAmountOfHistory/ settledBet.size();
	
	return avrg;
}
}
