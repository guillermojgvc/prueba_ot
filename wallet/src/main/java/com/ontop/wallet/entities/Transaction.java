package com.ontop.wallet.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


/**
 * The persistent class for the tbl_transaction database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_transaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Double amount;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="current_balance")
	private Double currentBalance;

	@Column(name="des_account")
	private String desAccount;

	@Column(name="des_currency")
	private Integer desCurrency;

	@Column(name="des_routing_number")
	private String desRoutingNumber;

	private Double fee;

	private Boolean payment;

	@Column(name="src_account")
	private String srcAccount;

	@Column(name="src_currency")
	private Integer srcCurrency;

	@Column(name="src_routing_number")
	private String srcRoutingNumber;

	@Column(name="transaction_id")
	private UUID transactionId;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@Transient
	private String srcCurrencyAbreviate;
	
	@Transient
	private String desCurrencyAbreviate;

	//bi-directional many-to-one association to RecipientBank
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tbl_recipient_bank")
	private RecipientBank tblRecipientBank;

	//bi-directional many-to-one association to Status
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tbl_status")
	private Status tblStatus;

	//bi-directional many-to-one association to Wallet
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tbl_wallet")
	private Wallet tblWallet;

}