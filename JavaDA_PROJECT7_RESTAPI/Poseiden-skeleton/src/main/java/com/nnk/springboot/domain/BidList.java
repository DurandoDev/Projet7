package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "bidlist")
public class BidList {

	@Id
	@Column(name = "bidListId", nullable = false)
	private Integer bidListId;

	@NotBlank(message = "Account is mandatory")
	String account;
	@NotBlank(message = "Type is mandatory")
	String type;

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

	public BidList(String accountTest, String typeTest, double bid) {
	}

	public BidList() {

	}

	public Integer getBidListId() {return bidListId;}

	public void setBidListId(Integer bidListId) {this.bidListId = bidListId;}

}
