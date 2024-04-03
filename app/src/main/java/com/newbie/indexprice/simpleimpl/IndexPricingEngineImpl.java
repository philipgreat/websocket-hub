package com.newbie.indexprice.simpleimpl;

import com.newbie.indexprice.collector.PricePrintCollector;
import com.newbie.indexprice.core.BaseIndexPricingEngineImpl;
import com.newbie.indexprice.provider.BianProvider;

public class IndexPricingEngineImpl extends BaseIndexPricingEngineImpl {

    @Override
    public void init() {
        super.init();
        addPriceProvider(new BianProvider());
        addPriceCollector(new PricePrintCollector());
    }


}
