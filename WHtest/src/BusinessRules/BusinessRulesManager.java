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
public void checkBetForRisk(Bet bet, Customer cust)
{
	cust.AverageBetAmount = AverageBetAmount(cust.SettledBetsList);
		if(IsTenTimesHigherThanAvrg(cust, bet) | IsOverThousandBet(bet, cust) )
		{
			bet.IsRisky = true;
		}
		if(IsThirtyTimesHigherThanAvrg(cust, bet))
		{
			bet.IsHighRisk = true;
		}

}
public Boolean IsUnusualWinning(List<Bet> totalSettledBets, double totalNumberOfWins) // task 1
{
	//TODO - define the round up or down rule? business rule?
	double sixtyP = (sixtyPercent * totalSettledBets.size()) ;
	if(totalNumberOfWins > sixtyP)
	{
		return true;	
	}
	return false;
}

public Boolean IsTenTimesHigherThanAvrg(Customer customer, Bet bet) //task 2.2
{
	long tenTimesHigherAmount = customer.AverageBetAmount * 10;
		if(bet.StakeAmount > tenTimesHigherAmount)
		{
			return true;
		}
	return false;
}
public Boolean IsThirtyTimesHigherThanAvrg(Customer customer, Bet bet) //task 2.2
{
	long tenTimesHigherAmount = customer.AverageBetAmount * 30;

		if(bet.StakeAmount > tenTimesHigherAmount)
		{
			return true;
		}
	return false;
}

public Boolean IsOverThousandBet(Bet bet, Customer customer) //task 2.2
{
		if(bet.StakeAmount > 1000)
		{
			return true;
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
