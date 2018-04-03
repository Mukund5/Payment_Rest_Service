package com.mukund.paymentGateway.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mukund.paymentGateway.model.Account;

public class AccountService {
static List<Account> myMessageList=new ArrayList<Account>();
static Long maxId=100L;
public AccountService()
{

}
	public Account getAccountDetails(int tranType,Long acctNum)
	{
		
		Long acctBal=null;
		Long acctNumber=null;
		Account act = null;
		
		
		String jdbcUrl="jdbc:postgresql://localhost:5432/postgres";
		String username="postgres";
		String password="postgres";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try
	    {
	    	 // Step 1 - Load driver
	        Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

	         // Step 2 - Open connection
	         conn = DriverManager.getConnection(jdbcUrl, username, password);
	         
	         String acctDetailsQuery="select accountNumber,accountbalance from accountDetails where "; 	
	
	
	         // Step 3 - Execute statement
	         if(tranType==3)
	         {
	        	 pstmt = conn.prepareStatement(acctDetailsQuery+" accountnumber=?");
	        	 
	         }
	         else if(tranType==2)
	         {
	        	 pstmt = conn.prepareStatement(acctDetailsQuery+" creditcardnumber=?");
	        	 
	         }
	         else if(tranType==1)
	         {
	        	 pstmt = conn.prepareStatement(acctDetailsQuery+" debtcardnumber=?");
	        	 
	         }
	         else 
	        	 {
	        	 act=new Account();
	        	 return act;
	        	 
	        	 }
	         
	         pstmt.setLong(1, acctNum);
	        
	         rs = pstmt.executeQuery();

	         // Step 4 - Get result
	         if (rs.next()) {
	        	 acctNumber=Long.valueOf(rs.getString(1));
	        	 acctBal=Long.valueOf(rs.getString(2));
	        	 act=new Account(acctNumber,acctBal);
	    
	         }

	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
		return act;
		
		
	}
/*	public Account getMessage(Long messageId)
	{
		for(Account m:myMessageList)
		if(m.getId()==messageId)
		return m;
		return null;
	}
	public Account addMessage(Account newMessage)
	{

		Account newMsg=new Account(maxId++,newMessage.getMessage(),newMessage.getAuthorId());
		
		myMessageList.add(newMsg);

		return newMsg;
	}
	
	public Account updateMessage(Account newMessage)
	{
		Long messageId=newMessage.getId();
		Account existingMsg=null;
		
		Iterator<Account> msgIterator=myMessageList.iterator();
		while(msgIterator.hasNext())
		{
			Account m=msgIterator.next();
			if(m.getId()==messageId)
			{
				existingMsg=m;
		
				msgIterator.remove();
			}
		}

		if(null!=existingMsg)
		{
			existingMsg.setMessage(newMessage.getMessage());
			myMessageList.add(existingMsg);
		}
				
		
		
		return existingMsg;
			
	}
	
	
	public void deleteMessage(long messageId)
	{

		
		Iterator<Account> msgIterator=myMessageList.iterator();
		while(msgIterator.hasNext())
		{
			Account m=msgIterator.next();
			if(m.getId()==messageId)
			{
		
				msgIterator.remove();
			}
		}


	}
*/	
}
