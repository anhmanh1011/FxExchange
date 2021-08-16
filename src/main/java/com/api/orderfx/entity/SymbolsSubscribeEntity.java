package com.api.orderfx.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SYMBOLS_SUBSCRIBE")
public class SymbolsSubscribeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHANNEL_ID")
    private Long channelId;
    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "SYMBOL_BROKER")
    private String symbolBroker;

    @Column(name = "SYMBOL_SUBSCRIBE")
    private String symbolSubscribe;
}
