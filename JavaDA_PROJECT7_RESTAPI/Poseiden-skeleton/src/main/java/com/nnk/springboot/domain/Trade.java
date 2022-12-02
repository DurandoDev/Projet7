package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "trade")
public class Trade {

	@Id
	@Column(name = "trade_id", nullable = false)
	private Integer tradeId;

	String account;

	String type;

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

	public Trade(String tradeAccount, String type) {

	}

	public Trade() {

	}

	public Integer getTradeId() {return tradeId;}

	public void setTradeId(Integer tradeId) {this.tradeId = tradeId;}
}
