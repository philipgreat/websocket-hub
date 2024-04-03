package com.newbie.indexprice.collector;

import com.newbie.indexprice.core.PricingInfoCollector;
import com.newbie.indexprice.core.SingleCoinPrice;

public class PricePrintCollector implements PricingInfoCollector {


    @Override
    public void collect(SingleCoinPrice singleCoinPrice) {
        System.out.println("collected "+singleCoinPrice);
    }
}
