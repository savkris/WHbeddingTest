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
	
	private String content = "";
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
			    
			    //ReadAndStore only once for performance,  instead of querying every time when info needed
			    extractLine(splitLineArray, true);		   
			}
			//just for testing and output 
			String eol = System.getProperty("line.separator");
			for (Customer cust : customersList) 
			{
			 content  += eol+"Customer ID = " + Long.toString(cust.CustomerID) + eol+ "settledBetsTotal = "+ cust.SettledBetsList.size() + eol+ 
					 "unsettledBetsTotal = " + cust.UnSettledBetsList.size() + eol;
			}

	        Writer wr = new Writer();
	        //wr.writeStringToFile("customer and their bets totals ", content);
	        System.out.println(content);	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	private void extractLine(String[] cols, Boolean isSettledBet) {	
		//0 = custID
		//1 = ventID
		//2 = partID 
		//3 = stake
		//4 = win
				
		//check first if customer already exists, else return existing
		Customer customer  = CheckIfCustExist(customersList, Long.parseLong(cols[0]));
		if(customer == null)
		{
			customer = CreateNewCustomer(cols);
		}
		
		//add bet for each and add to corresponding customer
		Bet bet = createNewbet(cols, isSettledBet);
		if(bet !=null && customer !=null)
		{
			if(isSettledBet)
			{
				customer.SettledBetsList.add(bet);
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
	
	public static void main(String[] args) {
		CSVReader reader = new CSVReader();
		reader.readFile(settledCSVfilePath, true); 
		//reader.readFile(settledCSVfilePath, true); 
	}

}
