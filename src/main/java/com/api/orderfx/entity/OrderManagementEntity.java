package com.api.orderfx.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "OrderManagementEntity")
@Data
public class OrderManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "IS_BUY")
    public Boolean isBuy;

    @Basic
    @Column(name = "`LIMIT`")
    public String limit;

    @Basic
    @Column(name = "`STOP`")
    public String stop;

    @Basic
    @Column(name = "`SYMBOLS`")
    public String symbols;

    @Basic
    @Column(name = "PRICE")
    public String price;

    @Basic
    @Column(name = "STATUS")
    public String status;

    @Basic
    @Column(name = "ORDER_ID")
    public String orderId;

    @Basic
    @Column(name = "TRADE_ID")
    public String tradeId;


}
