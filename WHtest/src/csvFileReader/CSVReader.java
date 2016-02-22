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
	private String unsettledCSVfilePath = "CSVfiles/Unsettled.csv";
	
	private ArrayList<Customer> customersList;
	
	public CSVReader()
	{
		customersList = new ArrayList<Customer>();
	}
	
	//TODO- parse the cols and rows earlier for performance
	public void readFile(String csvFile) {		
        String line = "";
		BufferedReader br;		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) 
			{
			    // use comma as separator
			    String[] splitLineArray = line.split(",");
			    
			    //ReadAndStore 
			    extractLine(splitLineArray);
			  			   
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	private Boolean custExists;
	private void extractLine(String[] cols) {	
		//0 = custID
		//1 = ventID
		//2 = partID 
		//3 = stake
		//4 = win
		
		//for each line reset bool, assuming its a new customer
		custExists = false;
		CheckIfCustExist(customersList, Long.parseLong(cols[0]) );
		
		if(!custExists)
		{
			Customer cust = new Customer();
			cust.CustomerID = Long.parseLong(cols[0]);
			customersList.add(cust);
			System.out.println("new customer created with ID" + cols[0]);
		}
		else
		{
			System.out.println("customer already exists with ID" + cols[0]);
		}
		
		
	}

	private void CheckIfCustExist(ArrayList<Customer> customers, long colsID) {
		//TODO- check if customer exists in the list already, if not create new and add bet		
		for (Customer existingCustomer : customersList) 
		{
			if(existingCustomer.CustomerID == colsID )
			{
				custExists = true;
			}
		}
	}
	public static void main(String[] args) {
		CSVReader reader = new CSVReader();
		reader.readFile(settledCSVfilePath);
	}

}
