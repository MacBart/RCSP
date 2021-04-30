package nl.rabobank.rcsp;

import java.util.HashMap;
import java.util.Map;

/**
 * class DataStore provides access to user data.
 */
public class DataStore {

	private Map<Long, CustomerStatement> customerStatementMap = new HashMap<>();
	
	private static DataStore instance = new DataStore();
	public static DataStore getInstance(){
		return instance;
	}
	
	private DataStore(){
		//dummy data
		customerStatementMap.put(12345L, new CustomerStatement(12345L, "NL45RABO0000012345", 100.00, 25.00, "Test", 125.00));
		customerStatementMap.put(32456L, new CustomerStatement(12345L, "NL45RABO0000032456", 100.00, -25.00, "Test", 75.00));
		customerStatementMap.put(98765L, new CustomerStatement(12345L, "NL45RABO0000098765", 100.00, -125.00, "Test", -25.00));
	}

	public CustomerStatement getCustomerStatement(long transactionReference) {
		return customerStatementMap.get(transactionReference);
	}

	public void putCustomerStatement(CustomerStatement customerStatement) {
		customerStatementMap.put(customerStatement.getTransactionReference(), customerStatement);
	}
}
