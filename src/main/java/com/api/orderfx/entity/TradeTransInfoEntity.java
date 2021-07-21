package com.api.orderfx.entity;

import api.message.codes.TRADE_OPERATION_CODE;
import api.message.codes.TRADE_TRANSACTION_TYPE;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TRADE_TRANS_INFO")
@Data
public class TradeTransInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Double close_price;
    public Long close_time;
    public Boolean closed;
    public Integer cmd;
    public String comment;
    public Double commission;
    public String customComment;
    public Integer digits;
    public Long expiration;
    public Double margin_rate;
    public Double open_price;
    public Long open_time;

    @Basic
    @Column(name = "`ORDER_ID`")
    public Long order;
    public Long order2;
    public Long position;
    public Double profit;
    public Double sl;
    public Double storage;
    public String symbol;
    public Double tp;
    public Double volume;
}
