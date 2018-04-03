package com.mukund.paymentGateway.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mukund.paymentGateway.model.Account;
import com.mukund.paymentGateway.model.Transaction;
import com.mukund.paymentGateway.service.TransactionService;


@Path("/transaction")
public class TransactionResource {
	
	TransactionService tranService=new TransactionService();

	@GET
	@Path("/processingFee/{tranType}/{tranAmt}")
	public Long getProcessingFee(@PathParam("acctNum") Long accountNum,@PathParam("tranType") int transactionType,@PathParam("tranAmt") Long transactionAmount)
	{
		Long processingFee=new Long(0);
		
		processingFee=tranService.getProcessingFee(accountNum,transactionType,transactionAmount);
		return processingFee;
	}

	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{tranType}/payment/{payAmt}/{processFee}")
	public Transaction performTransaction(Account acct,@PathParam("tranType") int transactionType,@PathParam("payAmt") Long payAmount,@PathParam("processFee") Long processFee)
	{
		Transaction tran=new Transaction();
		tran=tranService.performTransaction(acct,transactionType,payAmount,processFee);
		return tran;
	}

}
