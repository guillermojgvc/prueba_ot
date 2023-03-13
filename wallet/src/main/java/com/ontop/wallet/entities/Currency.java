package com.ontop.wallet.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the tbl_currency database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_currency")
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="currency_abbreviate")
	private String currencyAbbreviate;

	@Column(name="currency_name")
	private String currencyName;

	//bi-directional many-to-one association to Wallet
	@OneToMany(mappedBy="tblCurrency")
	private List<Wallet> tblWallets;

}