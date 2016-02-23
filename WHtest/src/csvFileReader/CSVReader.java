/**
 * 
 */
package csvFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import BusinessRules.BusinessRulesManager;
import Models.Bet;
import Models.Customer;

/**
 * @author kristinasavitskaya
 *
 */



public class CSVReader {
	/**
	 * @param args
	 */
	
	private static String settledCSVfilePath = "CSVfiles/Settled.csv";	
	private static String unsettledCSVfilePath = "CSVfiles/Unsettled.csv";
	
	private String eol = System.getProperty("line.separator");
	private ArrayList<Customer> customersList;
	
	public CSVReader()
	{
		customersList = new ArrayList<Customer>();
	}
	
	//TODO- parse the cols and rows earlier for performance
	public void readFile(String csvFile, Boolean isSettled) {		
        String line = "";
		BufferedReader br;		
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) 
			{
			    // use comma as separator
			    String[] splitLineArray = line.split(",");
			    
			    //ReadAndStore only once for performance,  instead of reading every time when info needed
			    extractLine(splitLineArray, isSettled);		   
			}	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	private void extractLine(String[] cols, Boolean isSettledBet) {	
		//0 = custID
		//1 = eventID
		//2 = partID 
		//3 = stake
		//4 = win
				
		//check first if customer already exists, else return existing
		Customer customer  = CheckIfCustExist(customersList, Long.parseLong(cols[0]));
		if(customer == null)
		{
			customer = CreateNewCustomer(cols);
		}
		
		//create and add bet to corresponding customer
		Bet bet = createNewbet(cols, isSettledBet);
		if(bet != null && customer != null)
		{
			if(isSettledBet)
			{
				customer.SettledBetsList.add(bet);
				if(bet.WinAmount >0)
				{
					customer.TotalWonBets ++;
				}
			}
			else
			{
				customer.UnSettledBetsList.add(bet);
			}
		}		
	}

	private Bet createNewbet(String[] cols, Boolean isSettledBet) {
		Bet bet = new Bet();
		bet.IsSettled = isSettledBet;
		bet.EventID = Long.parseLong(cols[1]);
		bet.ParticipantID = Long.parseLong(cols[2]);
		bet.StakeAmount = Long.parseLong(cols[3]);
		bet.WinAmount = Long.parseLong(cols[4]);
		return bet;
	}

	private Customer CreateNewCustomer(String[] cols) {
		Customer cust = new Customer();
		cust.CustomerID = Long.parseLong(cols[0]);
		customersList.add(cust);
		//System.out.println("new customer created with ID" + cols[0]);		
		return cust;
	}

	private Customer CheckIfCustExist(ArrayList<Customer> customers, long colsID) {
		for (Customer existingCustomer : customersList) 
		{
			if(existingCustomer.CustomerID == colsID )
			{
				//System.out.println("customer already exists with ID" + existingCustomer.CustomerID);
				return existingCustomer;
			}
		}
		return null;
	}
	
	private void printTaskOne()
	{
		String content = "TASK 1 " ;
		String betsContent = "BETS HISTORY :" ;
		//task 1
		
		for (Customer cust : customersList) 
		{
			cust.IsUnusualWinner = BusinessRulesManager.Instance.IsUnusualWinning(cust.SettledBetsList, cust.TotalWonBets);
			if(cust.IsUnusualWinner)
			{
				content  += eol+"Customer ID = " + Long.toString(cust.CustomerID) + 
						 eol+ "won bets Total = "+ cust.TotalWonBets + 
						  eol+ "settled Bets Total = "+ cust.SettledBetsList.size() + eol+ 
						 "unsettledBetsTotal = " + cust.UnSettledBetsList.size() + eol ;
				for (Bet bet : cust.SettledBetsList) 
				{ 
					if(bet.WinAmount > 0)
					//TODO - print all settled bets for that customer
					betsContent  += 
							eol+"Bet event ID = " + Long.toString(bet.EventID) + 
							" Skate Amount = " + Long.toString(bet.StakeAmount) +
							" Amount Won = " + Long.toString(bet.WinAmount) ;	
				}
				}
			}		
        //Writer wr = new Writer();
        //wr.writeStringToFile("customer and their bets totals ", content);
        System.out.println(content + betsContent);
	}
	
	private void printTaskTwo()
	{
		String content = "TASK 2 " ;
		String betsContentOne = "RISKY UNSETTLED BETS BY CUSTUMERS WINNING HISTORY:" ; // task 2.1
		String betsContentTwo = "RISKY BETS BY AVERAGE :" ; //task 2.2
	
		for (Customer cust : customersList) 
		{
			if(cust.IsUnusualWinner)
			{
				for (Bet bet : cust.UnSettledBetsList) 
				{
					betsContentOne  += eol + "Bet ID = " + bet.EventID + " Stake Amount = " + bet.StakeAmount + " ToWin Amount = " + bet.WinAmount;
				}
				
				}
			}		
        //Writer wr = new Writer();
        //wr.writeStringToFile("customer and their bets totals ", content);
        System.out.println(content + betsContentOne);
	}
	public static void main(String[] args) {
		new BusinessRulesManager();
		
		CSVReader reader = new CSVReader();
		reader.readFile(settledCSVfilePath, true); 
		reader.readFile(unsettledCSVfilePath, false); 
		reader.printTaskOne();
		reader.printTaskTwo();
	}

}
