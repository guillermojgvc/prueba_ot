package com.ontop.wallet.entities.provider;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the tbl_provider_transaction database table.
 * 
 */
@Data
@Entity
@Table(name="tbl_provider_transaction",schema = "provider")
public class ProviderTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="updated_date")
	private Timestamp updatedDate;

	private String status;

}