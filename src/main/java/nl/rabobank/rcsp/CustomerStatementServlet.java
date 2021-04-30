package nl.rabobank.rcsp;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerStatementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String SUCCESFULL = "SUCCESFULL";
	private static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";
	private static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";
	private static final String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
	private static final String BAD_REQUEST = "BAD_REQUEST";
	
	private static final String RESULT = "result";
	private static final String ERRORRECORDS = "errorRecords";
	
	private static final String REFERENCE = "reference";
	private static final String ACCOUNTNUMBER = "accountNumber";
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		int status = 0;
		JSONObject jsonObject;
		long transactionReference = 0L;
		String accountNumber = null;
		double startBalance = 100.00;
		double mutation = -25.00; 
		String description = null;
		double endBalance = 75.00;
		
		JSONObject jsonObject_returnMessage = new JSONObject();
		JSONObject jsonObject_errorRecord_DUPLICATE_REFERENCE = new JSONObject();
		JSONObject jsonObject_errorRecord_INCORRECT_END_BALANCE = new JSONObject();
		
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			jsonObject =  new JSONObject(jb.toString());
			transactionReference = Long.parseLong(jsonObject.getString("Transaction reference"));
			accountNumber = jsonObject.getString("Account number");
			startBalance = Double.parseDouble(jsonObject.getString("Start Balance"));
			mutation = Double.parseDouble(jsonObject.getString("Mutation"));
			description = jsonObject.getString("Description");
			endBalance = Double.parseDouble(jsonObject.getString("End Balance"));
			
			if(ValidateRCSP.checkUniqueId(transactionReference) && ValidateRCSP.checkBalance(startBalance, mutation, endBalance)) {
				DataStore.getInstance().putCustomerStatement(new CustomerStatement(transactionReference, accountNumber, startBalance, mutation, description, endBalance));	
				status = HttpServletResponse.SC_OK;
				JSONArray jsonArray = new JSONArray();
				
				jsonObject_returnMessage.append(RESULT, SUCCESFULL);
				jsonObject_returnMessage.append(ERRORRECORDS, jsonArray);
			} else if(!ValidateRCSP.checkUniqueId(transactionReference) && ValidateRCSP.checkBalance(startBalance, mutation, endBalance)) {
				status = HttpServletResponse.SC_OK;
				JSONArray jsonArray = new JSONArray();
				
				jsonObject_errorRecord_DUPLICATE_REFERENCE.append(REFERENCE, transactionReference);
				jsonObject_errorRecord_DUPLICATE_REFERENCE.append(ACCOUNTNUMBER, accountNumber);			
				jsonArray.put(jsonObject_errorRecord_DUPLICATE_REFERENCE);
				
				jsonObject_returnMessage.append(RESULT, DUPLICATE_REFERENCE);
				jsonObject_returnMessage.append(ERRORRECORDS, jsonArray);
			} else if(ValidateRCSP.checkUniqueId(transactionReference) && !ValidateRCSP.checkBalance(startBalance, mutation, endBalance)) {
				status = HttpServletResponse.SC_OK;
				JSONArray jsonArray = new JSONArray();
				
				jsonObject_errorRecord_INCORRECT_END_BALANCE.append(REFERENCE, transactionReference);
				jsonObject_errorRecord_INCORRECT_END_BALANCE.append(ACCOUNTNUMBER, accountNumber);			
				jsonArray.put(jsonObject_errorRecord_INCORRECT_END_BALANCE);
				
				jsonObject_returnMessage.append(RESULT, INCORRECT_END_BALANCE);
				jsonObject_returnMessage.append(ERRORRECORDS, jsonArray);
			} else if(!ValidateRCSP.checkUniqueId(transactionReference) && !ValidateRCSP.checkBalance(startBalance, mutation, endBalance)) {
				status = HttpServletResponse.SC_OK;
				JSONArray jsonArray = new JSONArray();
				
				jsonObject_errorRecord_DUPLICATE_REFERENCE.append(REFERENCE, transactionReference);
				jsonObject_errorRecord_DUPLICATE_REFERENCE.append(ACCOUNTNUMBER, accountNumber);				
				jsonArray.put(jsonObject_errorRecord_DUPLICATE_REFERENCE);
				
				jsonObject_errorRecord_INCORRECT_END_BALANCE.append(REFERENCE, transactionReference);
				jsonObject_errorRecord_INCORRECT_END_BALANCE.append(ACCOUNTNUMBER, accountNumber);				
				jsonArray.put(jsonObject_errorRecord_INCORRECT_END_BALANCE);

				jsonObject_returnMessage.append(RESULT, DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
				jsonObject_returnMessage.append(ERRORRECORDS, jsonArray);
			}
			
			response.sendError(status, jsonObject_returnMessage.toString());
			response.getWriter().append(jsonObject_returnMessage.toString());
			
		} catch (JSONException e) {
			status = HttpServletResponse.SC_BAD_REQUEST;
			JSONArray jsonArray = new JSONArray();
			try {
				jsonObject_returnMessage.append(RESULT, BAD_REQUEST);
				jsonObject_returnMessage.append(ERRORRECORDS, jsonArray);
			} catch (JSONException e1) {
				// TODO Throw exception 
			}
			response.sendError(status, jsonObject_returnMessage.toString());
			response.getWriter().append(jsonObject_returnMessage.toString());
		} catch (Exception e) { 
			// TODO Throw exception
		}
		

		
	}
	
}
