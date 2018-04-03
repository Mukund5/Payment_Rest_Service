package com.mukund.paymentGateway.model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {

	private long transactionNumber;
	private long bankAccountNumber;
	private String tranType;
	private long paymentAmount;
	private long processingFee;
	private long totalTranAmount;
	private String tranStatus;
	private String tranDesc;
	private Timestamp transactionTime;

	public Transaction()
	{

	}

	public long getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public long getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(long bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public long getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(long processingFee) {
		this.processingFee = processingFee;
	}

	public long getTotalTranAmount() {
		return totalTranAmount;
	}

	public void setTotalTranAmount(long totalTranAmount) {
		this.totalTranAmount = totalTranAmount;
	}

	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	public String getTranDesc() {
		return tranDesc;
	}
	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	public Timestamp getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}


}
