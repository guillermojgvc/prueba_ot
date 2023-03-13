package com.ontop.wallet.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the tbl_wallet database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_wallet")
public class Wallet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="account_number")
	private String accountNumber;

	private Double balance;

	private Boolean prefered;

	@Column(name="routing_number")
	private String routingNumber;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="tblWallet")
	private List<Transaction> tblTransactions;

	//bi-directional many-to-one association to Currency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tbl_currency")
	private Currency tblCurrency;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tbl_user")
	private User tblUser;

}