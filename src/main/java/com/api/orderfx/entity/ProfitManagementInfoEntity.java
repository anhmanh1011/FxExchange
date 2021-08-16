package com.api.orderfx.entity;

import com.api.orderfx.common.EStatusTrade;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PROFIT_MANAGEMENT_INFO")
@Data
public class ProfitManagementInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BORKER_NAME")
    private String brokerName;

    @Column(name = "SYMBOLS")
    private String symbols;

    @Column(name = "POSITION_ID", unique = true)
    private Long positionId;

    @Basic
    @Column(name = "`ORDER_ID`",unique = true)
    private Long orderId;

    @Column(name = "LST_PROFIT")
    private String lstProfit;

    @Column(name = "IS_BỤY")
    private Boolean isBuy;

    @Column(name = "CLOSED")
    private Boolean closed= false;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EStatusTrade status;
}
