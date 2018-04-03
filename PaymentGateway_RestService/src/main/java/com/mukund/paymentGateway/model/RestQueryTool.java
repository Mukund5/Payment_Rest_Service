package com.mukund.paymentGateway.model;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestQueryTool {

	public static void main(String[] args) {

		String restMainUri="http://localhost:8080/PaymentGateway_RestService/mypaymentService";
		Client client=ClientBuilder.newClient();
		Response res=client.target(restMainUri).path("accounts").path("balance").path("3").path("486556492").request(MediaType.APPLICATION_JSON).get();

		Account x=res.readEntity(Account.class);
		//System.out.println(client.target(restMainUri).path("accounts").path("balance").path("3").path("486556492").request(MediaType.APPLICATION_JSON).get());
		System.out.println(x.getAccountBalance());
		System.out.println(x.getAccountNumber());



		Client client1=ClientBuilder.newClient();
		Response res1=client1.target(restMainUri).path("accounts").path("5494070242766165").path("transaction").path("processingFee").path("2").path("1500").request(MediaType.TEXT_PLAIN).get();

		String processFee=res1.readEntity(String.class);
		//System.out.println(client.target(restMainUri).path("accounts").path("balance").path("3").path("486556492").request(MediaType.APPLICATION_JSON).get());
		System.out.println(processFee);


		Account newAcc=new Account(x.getAccountNumber(), x.getAccountBalance());
		Client client2=ClientBuilder.newClient();
		Response res2=client2.target(restMainUri).path("transaction").path("3").path("payment").path("500").path("5").request(MediaType.APPLICATION_JSON).put(Entity.entity(newAcc, MediaType.APPLICATION_JSON));
		String x1=res2.readEntity(String.class);

		System.out.println("Tran:"+x1);



		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		//convert json string to object
		try {
			Transaction t1= objectMapper.readValue(x1, Transaction.class);
			System.out.println("t1:"+t1.getBankAccountNumber());
			System.out.println("t2:"+t1.getPaymentAmount());
			System.out.println("t3:"+t1.getTransactionTime());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
