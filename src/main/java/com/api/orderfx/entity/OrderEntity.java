package com.api.orderfx.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "FxOrder")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "T")
    public String t;
    @Basic
    @Column(name = "RATE_PRECISION")
    public String ratePrecision;
    @Basic
    @Column(name = "ORDER_ID")
    public String orderId;
    @Basic
    @Column(name = "TRADE_ID")
    public String tradeId;
    @Basic
    @Column(name = "TIME")
    public String time;
    @Basic
    @Column(name = "ACCOUNT_NAME")
    public String accountName;
    @Basic
    @Column(name = "ACCOUNT_ID")
    public String accountId;
    @Basic
    @Column(name = "TIME_INFORCE")
    public String timeInForce;
    @Basic
    @Column(name = "EXPIRE_DATE")
    public String expireDate;
    @Basic
    @Column(name = "CURRENCY")
    public String currency;
    @Basic
    @Column(name = "IS_BUY")
    public Boolean isBuy;
    @Basic
    @Column(name = "BUY")
    public Double buy;
    @Basic
    @Column(name = "SELL")
    public String sell;
    @Basic
    @Column(name = "TYPE")
    public String type;
    @Basic
    @Column(name = "STATUS")
    public String status;
    @Basic
    @Column(name = "AMOUNTK")
    public String amountK;
    @Basic
    @Column(name = "CURRENCY_POString")
    public Double currencyPoString;

    @Basic
    @Column(name = "STOP_MOVE")
    public String stopMove;

    @Basic
    @Column(name = "STOP")
    public String stop;

    @Basic
    @Column(name = "STOPRATE")
    public String stopRate;
    @Basic
    @Column(name = "`LIMIT`")
    public String limit;
    @Basic
    @Column(name = "LIMIT_RATE")
    public String limitRate;
    @Basic
    @Column(name = "IS_ENTRY_ORDER")
    public Boolean isEntryOrder;
    @Basic
    @Column(name = "O_CO_BULK_ID")
    public String ocoBulkId;
    @Basic
    @Column(name = "IS_NET_QUANTITY")
    public Boolean isNetQuantity;
    @Basic
    @Column(name = "IS_LIMIT_ORDER")
    public Boolean isLimitOrder;
    @Basic
    @Column(name = "IS_STOP_ORDER")
    public Boolean isStopOrder;
    @Basic
    @Column(name = "IS_ELSE_ORDER")
    public Boolean isELSOrder;
    @Basic
    @Column(name = "STOP_PEGBASE_TYPE")
    public String stopPegBaseType;
    @Basic
    @Column(name = "LIMIT_PEGBASE_TYPE")
    public String limitPegBaseType;
    @Basic
    @Column(name = "CHILD_TRAILING_STOP")
    public String child_trailingStop;
    @Basic
    @Column(name = "CHILD_TRAILING")
    public String child_trailing;
    @Basic
    @Column(name = "TRAILING_STOP")
    public String trailingStop;
    @Basic
    @Column(name = "`TRAILING`")
    public String trailing;
    @Basic
    @Column(name = "`RANGE`")
    public String range;

    @Basic
    @Column(name = "ACTION")
    public String action;


}
