package com.mukund.paymentGateway.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mukund.paymentGateway.model.Account;
import com.mukund.paymentGateway.model.Transaction;

public class TransactionService {
	static List<Account> myMessageList=new ArrayList<Account>();
	static Long maxId=100L;
	public TransactionService()
	{

	}
	public Long getProcessingFee(Long acctNum,int tranType,Long tranAmount)
	{



		String jdbcUrl="jdbc:postgresql://localhost:5432/postgres";
		String username="postgres";
		String password="postgres";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long processingFee=new Long(0);

		try
		{
			// Step 1 - Load driver
			Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0

			// Step 2 - Open connection
			conn = DriverManager.getConnection(jdbcUrl, username, password);

			// Step 3 - Execute statement
			if(tranType==3)
			{
				pstmt = conn.prepareStatement("select bankname from accountDetails where accountnumber=?");

			}
			else if(tranType==2)
			{
				pstmt = conn.prepareStatement("select bankname from accountDetails where creditcardnumber=?");

			}
			else if(tranType==1)
			{
				pstmt = conn.prepareStatement("select bankname from accountDetails where debtcardnumber=?");

			}
			else 
				return processingFee;

			pstmt.setLong(1, acctNum);

			rs = pstmt.executeQuery();

			// Step 4 - Get result
			if (rs.next()) {
				
				String bankName=rs.getString(1);
				//System.out.println("bal:"+acctBal+"\tBank:"+bankName);


				Double procPercentage=new Double(0);
				if(bankName.contains("ICICI") || bankName.contains("HDFC") || bankName.contains("Axis"))
				{
					if(tranType==3)
						procPercentage=0.5;
					else if(tranType==1)
						procPercentage=0.75;
					else if(tranType==2)
						procPercentage=1.0;
				}
				else
				{
					if(tranType==3)
						procPercentage=0.25;
					else if(tranType==1)
						procPercentage=0.5;
					else if(tranType==2)
						procPercentage=0.75;

				}

				Double processFeeInterim=new Double(0);
				processFeeInterim=tranAmount*procPercentage/100;
				processingFee=Math.round(processFeeInterim);
				//       System.out.println("per:"+procPercentage+"\tdouble:"+processFeeInterim+"final:"+processingFee);

			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return processingFee;


	}


	public Transaction performTransaction(Account acct,int tranType,Long payAmount,Long processFee)
	{

		System.out.println("acco:"+acct.getAccountNumber());
		Transaction tran=new Transaction();

		String jdbcUrl="jdbc:postgresql://localhost:5432/postgres";
		String username="postgres";
		String password="postgres";

		Connection conn = null;
		Statement stmt=null;
		PreparedStatement pstmt = null,pstmt1 = null,pstmt2 = null;

		ResultSet rs = null,rs1=null;
		Long tranNum=new Long(0);
		String tranTypeName="";
		Long totalTranAmount=new Long(payAmount+processFee);
		try
		{

			Class.forName("org.postgresql.Driver"); // Class.forName() is not needed since JDBC 4.0


			conn = DriverManager.getConnection(jdbcUrl, username, password);

			stmt=conn.createStatement();
			rs1=stmt.executeQuery("select nextVal('transactionNumSeq') as trannumber");


			if (rs1.next()) {
				tranNum=rs1.getLong(1);
			}


			if(tranType==3)
			{
				pstmt = conn.prepareStatement("select accountbalance,minimumbalance,dailyTranLimit,tranLimitReachedToday from accountDetails where accountnumber=?");
				tranTypeName="Net Banking"; 
			}
			else if(tranType==2)
			{
				pstmt = conn.prepareStatement("select accountbalance,minimumbalance,dailyTranLimit,tranLimitReachedToday,monthlyCreditLimit,monthlyCreditLimitReached from accountDetails where creditcardnumber=?");
				tranTypeName="Credit Card Payment";
			}
			else if(tranType==1)
			{
				pstmt = conn.prepareStatement("select accountbalance,minimumbalance,dailyTranLimit,tranLimitReachedToday from accountDetails where debtcardnumber=?");
				tranTypeName="Debit Card Payment";
			}
			else 
			{
				tran.setBankAccountNumber(acct.getAccountNumber());
				tran.setPaymentAmount(payAmount);
				tran.setProcessingFee(processFee);
				tran.setTotalTranAmount(totalTranAmount);
				tran.setTranDesc("Transaction Failed due to incorrect transaction mode");
				tran.setTranStatus("Failed");
				tran.setTransactionNumber(tranNum);
				tran.setTranType("Invalid");
				return tran;
			}

			pstmt.setLong(1, acct.getAccountNumber());

			rs = pstmt.executeQuery();

			Long acctBal,minAcctBal,dailyTranLimit,tranLimitReachedToday,monthlyCreditLimit,monthlyCreditLimitReached;

			monthlyCreditLimitReached=new Long(0);
			if (rs.next()) {



				acctBal=Long.valueOf(rs.getString("accountbalance"));
				minAcctBal=Long.valueOf(rs.getString("minimumbalance"));
				dailyTranLimit=Long.valueOf(rs.getString("dailyTranLimit"));
				tranLimitReachedToday=Long.valueOf(rs.getString("tranLimitReachedToday"));


				tran.setBankAccountNumber(acct.getAccountNumber());
				tran.setPaymentAmount(payAmount);
				tran.setProcessingFee(processFee);
				tran.setTotalTranAmount(totalTranAmount);
				tran.setTransactionNumber(tranNum);
				tran.setTranType(tranTypeName);

				if((acctBal-totalTranAmount)<minAcctBal)
				{
					tran.setTranDesc("Transaction Failed since Account Balance would go below Minimum Balance");
					tran.setTranStatus("Failed");
				}
				else if((acctBal-totalTranAmount)<0)
				{
					tran.setTranDesc("Transaction Failed due to insufficient Balance in the Account");
					tran.setTranStatus("Failed");
				}
				else if((tranLimitReachedToday+payAmount)>dailyTranLimit)
				{
					tran.setTranDesc("Transaction Failed since you have reached today's transaction limit");
					tran.setTranStatus("Failed");
				}

				if(tranType==2)
				{

					monthlyCreditLimit=Long.valueOf(rs.getString("monthlyCreditLimit"));
					monthlyCreditLimitReached=Long.valueOf(rs.getString("monthlyCreditLimitReached"));

					if((monthlyCreditLimitReached+payAmount)>monthlyCreditLimit)
					{
						tran.setTranDesc("Transaction Failed since you have reached this month's credit limit");
						tran.setTranStatus("Failed");
					}
				}


				if(null== tran.getTranStatus() || !tran.getTranStatus().equalsIgnoreCase("Failed"))
				{

					tran.setTranStatus("Success");
					tran.setTranDesc("Payment Made successfully");

					//Update Account details
					Long newActBal=acctBal-totalTranAmount;
					Long newLimitReached=tranLimitReachedToday+payAmount;

					String updateQuery="update accountDetails set accountbalance=?,tranLimitReachedToday=?";
					if(tranType==2)
					{
						Long newCreditLimitReached=monthlyCreditLimitReached+payAmount;

						updateQuery+=",monthlyCreditLimitReached=? where creditcardnumber=?";

						pstmt1 = conn.prepareStatement(updateQuery);
						pstmt1.setLong(1,newActBal);
						pstmt1.setLong(2,newLimitReached);
						pstmt1.setLong(3,newCreditLimitReached);
						pstmt1.setLong(4,acct.getAccountNumber());

					}
					else if(tranType==1)
					{
						updateQuery+=" where debtcardnumber=?";

						pstmt1 = conn.prepareStatement(updateQuery);
						pstmt1.setLong(1,newActBal);
						pstmt1.setLong(2,newLimitReached);
						pstmt1.setLong(3,acct.getAccountNumber());
					}

					else
					{
						updateQuery+=" where accountnumber=?";

						pstmt1 = conn.prepareStatement(updateQuery);
						pstmt1.setLong(1,newActBal);
						pstmt1.setLong(2,newLimitReached);
						pstmt1.setLong(3,acct.getAccountNumber());
					}

					System.out.println("stmt:"+pstmt1);
					int a=pstmt1.executeUpdate();
					System.out.println("update:"+a);



				}

				//Insert transaction details

				String insertTranQuery="insert into transactiondetails values(?,?,?,?,?,?,?)";

				pstmt2=conn.prepareStatement(insertTranQuery);
				pstmt2.setString(1, tranNum.toString());
				pstmt2.setLong(2, tran.getBankAccountNumber());
				pstmt2.setString(3, tran.getTranType());
				pstmt2.setInt(4, (int) tran.getTotalTranAmount());
				pstmt2.setString(5, tran.getTranStatus());
				pstmt2.setString(6, tran.getTranDesc());
				java.sql.Timestamp currentTime=new Timestamp(System.currentTimeMillis());
				tran.setTransactionTime(currentTime);
				pstmt2.setTimestamp(7, currentTime);
				//System.out.println("stsmt2:"+pstmt2);
				pstmt2.execute();

				

			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return tran;


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
