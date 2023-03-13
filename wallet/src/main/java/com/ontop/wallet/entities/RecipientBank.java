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
 * The persistent class for the tbl_recipient_bank database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_recipient_bank")
public class RecipientBank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="account_number")
	private String accountNumber;

	@Column(name="bank_name")
	private String bankName;

	@Column(name="dni_number")
	private String dniNumber;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private Boolean prefered;

	@Column(name="routing_number")
	private String routingNumber;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tbl_user")
	private User tblUser;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="tblRecipientBank")
	private List<Transaction> tblTransactions;

}