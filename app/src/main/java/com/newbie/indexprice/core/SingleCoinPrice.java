package com.newbie.indexprice.core;

import java.math.BigDecimal;

public class SingleCoinPrice {
    private String symbol; // like USDT, ETH, BTC
    private BigDecimal currentPrice;
    private long timestamp;
    private String source; //url like wss

    @Override
    public String toString() {
        return "SingleCoinPrice{" +
                "symbol='" + symbol + '\'' +
                ", currentPrice=" + currentPrice +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                '}';
    }
}
