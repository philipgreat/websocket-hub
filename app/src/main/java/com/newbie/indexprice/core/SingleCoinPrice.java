package com.newbie.indexprice.core;

import java.math.BigDecimal;

public class SingleCoinPrice {
    private String sourceSymbol; // like USDT, ETH, BTC
    private String targetSymbol; // like USDT, ETH, BTC

    private BigDecimal currentPrice;
    private long timestamp;
    private String source; //url like wss

    @Override
    public String toString() {
        return "SingleCoinPrice{" +
                "sourceSymbol='" + sourceSymbol + '\'' +
                ", targetSymbol='" + targetSymbol + '\'' +
                ", currentPrice=" + currentPrice +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                '}';
    }
}
