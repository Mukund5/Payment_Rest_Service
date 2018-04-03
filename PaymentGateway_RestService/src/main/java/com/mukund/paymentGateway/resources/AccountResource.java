package com.mukund.paymentGateway.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mukund.paymentGateway.model.Account;
import com.mukund.paymentGateway.service.AccountService;

/*Handles requests that have /accounts*/
@Path("/accounts")
public class AccountResource {

	AccountService accountService=new AccountService();
	
	
	/*Handles GET request*/
	@GET
	/*The request has two parameters in the url. Tran type mentions whether
	 * it is debt card transaction (1) or credit card transaction(2) or net banking transaction(3).
	 * acctNum denoted the number of the card or the account*/
	@Path("/balance/{tranType}/{acctNum}")
/*	Example for this is:
	http://localhost:8080/PaymentGateway_RestService/mypaymentService/accounts/balance/1/011243445
		*/
	@Produces(MediaType.APPLICATION_JSON)
	/*The parameter is got through path param annotation*/
	public Account getAccountDetails(@PathParam("tranType") int transactionType,@PathParam("acctNum") Long accountNumber)
	{
		
		return accountService.getAccountDetails(transactionType,accountNumber);
		
	}

	
	
	/*Below is a sub-resource. So we return an instance of
	 *  the sub-resource and the control flows to the handler of the sub-resource class
	 * */
	@Path("{acctNum}/transaction")
	public TransactionResource getTransactionResource()
	{
		return new TransactionResource();
	}
	
/*	Handles POST request
	@POST
	@Path("/")
	This method expects a Message instance being sent from the POST request, 
	thus we give a "Consumes" annotation of type JSON 
	@Consumes(MediaType.APPLICATION_JSON)
	Produces JSON response of the newly added message
	@Produces(MediaType.APPLICATION_JSON)
	Here Jersey knows that JSON being consumed is to be converted to below 
	argument type	i.e. Message type
	public Account addMessage(Account newMessage)
	{
		
		//return "Hello World";
		return msgService.addMessage(newMessage);
		
	}

	
	
	
	Handles PUT request
	@PUT
	Unlike a POST, the PUT specifies message of which id needs to be modified.
	This id is to be mentioned in the request url as path param instead of mentioning
	in request body
	
	@Path("/{param1}")
	This method expects a Message instance being sent from the PUT request, 
	thus we give a "Consumes" annotation of type JSON 
	@Consumes(MediaType.APPLICATION_JSON)
	Produces JSON response of the updated message
	@Produces(MediaType.APPLICATION_JSON)
	Here Jersey knows that JSON being consumed is to be converted to below 
	argument type	i.e. Message type
	public Account updateMessage(@PathParam("param1") Long msgId, Account newMessage)
	{
		Thus we are getting the message id as well as the message with new content.
		Thus we need to set the id before sending it for modification
		newMessage.setId(msgId);
		//return "Hello World";
		return msgService.updateMessage(newMessage);
		
	}
	
	
	
	
	
	
	
	Handles DELETE request
	@DELETE
	Deletes the message of the id given in the path param.
	Thus This request doesnt accept any content in the request body and so
	consumes annotation is not required
	
	@Path("/{param1}")
	
	Produces JSON response of the newly deleted message
	@Produces(MediaType.APPLICATION_JSON)
	Here Jersey knows that JSON being consumed is to be converted to below 
	argument type	i.e. Message type
	public void deleteMessage(@PathParam("param1") Long msgId)
	{
		msgService.deleteMessage(msgId);
		
	}
*/
}
