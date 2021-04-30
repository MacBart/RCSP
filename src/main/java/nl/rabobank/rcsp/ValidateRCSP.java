package nl.rabobank.rcsp;

public final class ValidateRCSP {
	  
	private ValidateRCSP(){}
	
	public static boolean checkUniqueId(long transactionReference){
		if (DataStore.getInstance().getCustomerStatement(transactionReference) != null) {
			return false;
	    } 
		return true;
	}	
	public static boolean checkBalance(double startBalance, double mutation, double endBalance){
	    return (startBalance + mutation == endBalance);
	  }

}
