package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "trade")
public class Trade {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "trade_id", nullable = false)
	private Integer tradeId;

	@NotBlank(message = "Account is mandatory")
	String account;

	@NotBlank(message = "Type is mandatory")
	String type;

	@NotNull(message = "must not be null")
	double buyQuantity;

	double sellQuantity;

	double buyPrice;

	double sellPrice;

	Timestamp tradeDate;

	String security;

	String status;

	String trader;

	String benchmark;

	String book;

	String creationName;

	Timestamp creationDate;

	String revisionName;

	Timestamp revisionDate;

	String dealName;

	String dealType;

	String sourceListId;

	String side;

	public Trade(String account, String type, double buyQuantity) {
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

	public Trade() {

	}

	public Integer getTradeId() {return tradeId;}

	public void setTradeId(Integer tradeId) {this.tradeId = tradeId;}
}
