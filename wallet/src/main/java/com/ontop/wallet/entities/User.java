package com.ontop.wallet.entities;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the tbl_user database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String identification;

	private String name;

	//bi-directional many-to-one association to RecipientBank
	@OneToMany(mappedBy="tblUser")
	private List<RecipientBank> tblRecipientBanks;

	//bi-directional many-to-one association to Wallet
	@OneToMany(mappedBy="tblUser")
	private List<Wallet> tblWallets;

}