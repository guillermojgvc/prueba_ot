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
 * The persistent class for the tbl_status database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_status")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="status_name")
	private String statusName;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="tblStatus")
	private List<Transaction> tblTransactions;

}