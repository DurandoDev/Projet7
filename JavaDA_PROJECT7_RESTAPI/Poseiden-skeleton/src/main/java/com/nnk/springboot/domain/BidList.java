package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "bidlist")
public class BidList {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "bidListId", nullable = false)
	private Integer bidListId;

	@NotBlank(message = "Account is mandatory")
	String account;
	@NotBlank(message = "Type is mandatory")
	String type;

	@NotNull(message = "must not be null")
	double bidQuantity;

	double askQuantity;

	double bid;

	double ask;

	String benchmark;

	Timestamp bidListDate;

	String commentary;

	String status;

	String trader;

	String book;

	String creationName;

	Timestamp creationDate;

	String revisionName;

	Timestamp revisionDate;

	String dealName;

	String dealType;

	String sourceListId;

	String side;

	public BidList(String account, String type, double bidQuantity) {
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	public BidList() {

	}

	public Integer getBidListId() {return bidListId;}

	public void setBidListId(Integer bidListId) {this.bidListId = bidListId;}

}
