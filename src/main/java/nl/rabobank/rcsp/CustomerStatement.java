package nl.rabobank.rcsp;

public class CustomerStatement {
	private long transactionReference;
	private String accountNumber;
	private double startBalance;
	private double mutation;
	private String description;
	private double endBalance;
	
	public CustomerStatement(long transactionReference, String accountNumber, double startBalance, 
			double mutation, String description, double endBalance) {
		this.transactionReference = transactionReference;
		this.accountNumber = accountNumber;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.description = description;
		this.endBalance = endBalance;
	}

	public long getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(long transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}

}