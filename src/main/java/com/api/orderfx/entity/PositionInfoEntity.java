package com.api.orderfx.entity;

import com.api.orderfx.common.EStatusTrade;
import com.api.orderfx.common.ETransactionType;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "POSITION_INFO")
@Data
public class PositionInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHANNEL_ID")
    private Long channelId;

    @Basic
    @Column(name = "`POSITION_ID`",unique = true)
    private Long positionId;

    @Basic
    @Column(name = "`ORDER_ID`",unique = true)
    private Long orderId;

    @Column(name = "`SYMBOL`")
    private String symbol;

    @Column(name = "VOLUME")
    private Double volume;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ETransactionType type;

    @Column(name = "BROKER_NAME")
    private String brokerName;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "OPEN_PRICE")
    private Double openPrice;

    @Column(name = "CLOSE_PRICE")
    private Double closePrice;

    @Basic
    @Column(name = "`PROFIT`")
    private Double profit;

    @Column(name = "TAKE_PROFIT")
    private Double takeProfit;

    @Column(name = "STOP_LOSS")
    private Double stopLoss;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "COMMISSION")
    private Double commission;

    @Column(name = "SWAP")
    private Double swap;

    @Column(name = "OPEN_TIME")
    private java.sql.Timestamp openTime;

    @Column(name = "CLOSE_TIME")
    private java.sql.Timestamp closeTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_TIME")
    private java.sql.Timestamp updateTime;

    @Column(name = "CLOSED")
    private Boolean closed = false;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EStatusTrade status;

}
