package BusinessRules;

public class BusinessRulesManager {

	private int sixtyPercent = 60;
	
public BusinessRulesManager Instance;

public BusinessRulesManager()
{
	Instance = this;
}

public Boolean IsUnusualWinning(long totalSettledBets, long totalNumberOfWins)
{
	return true;
}

}
