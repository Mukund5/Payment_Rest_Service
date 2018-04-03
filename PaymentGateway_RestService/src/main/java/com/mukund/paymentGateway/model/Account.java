package com.mukund.paymentGateway.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/*JAX-B does the type conversion from generic types to types(like a list of Messages) like XML or JSON. 
For that we need to provide info like which is the root element 
like giving the class as the XMLRootElement*/

@XmlRootElement
public class Account {

	private long accountNumber;
	private String bankName;
	private long accountBalance;
	private long minimumBalance;
	private long dailyTranLimit;
	private long tranLimitReachedToday;
	private long debtCardNumber;
	private long creditCardNumber;
	private long monthlyCreditLimit;
	private long monthlyCreditLimitReached;

	public Account()
	{

	}
	
	public Account(long actNum,long actBalance)
	{
		this.accountNumber=actNum;
		this.accountBalance=actBalance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(long accountBalance) {
		this.accountBalance = accountBalance;
	}

	public long getMinimumBalance() {
		return minimumBalance;
	}
	public void setMinimumBalance(long minimumBalance) {
		this.minimumBalance = minimumBalance;
	}
	public long getDailyTranLimit() {
		return dailyTranLimit;
	}

	public void setDailyTranLimit(long dailyTranLimit) {
		this.dailyTranLimit = dailyTranLimit;
	}

	public long getTranLimitReachedToday() {
		return tranLimitReachedToday;
	}

	public void setTranLimitReachedToday(long tranLimitReachedToday) {
		this.tranLimitReachedToday = tranLimitReachedToday;
	}

	public long getDebtCardNumber() {
		return debtCardNumber;
	}

	public void setDebtCardNumber(long debtCardNumber) {
		this.debtCardNumber = debtCardNumber;
	}

	public long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public long getMonthlyCreditLimit() {
		return monthlyCreditLimit;
	}

	public void setMonthlyCreditLimit(long monthlyCreditLimit) {
		this.monthlyCreditLimit = monthlyCreditLimit;
	}

	public long getMonthlyCreditLimitReached() {
		return monthlyCreditLimitReached;
	}

	public void setMonthlyCreditLimitReached(long monthlyCreditLimitReached) {
		this.monthlyCreditLimitReached = monthlyCreditLimitReached;
	}





}
