package com.newbie.indexprice.provider;

import com.newbie.indexprice.core.IndexPriceException;
import com.newbie.indexprice.core.PriceProvider;
import com.newbie.indexprice.core.ProviderPriceInfo;
import com.newbie.indexprice.core.SingleCoinPrice;

import java.util.ArrayList;
import java.util.List;

public class BianProvider extends PriceProvider {

    @Override
    public String getWebsocketAddress() {
        //return "ws://iotlog.doublechaintech.com:80/message-center/public";
        return "wss://stream.binance.com:9443/stream?streams=bnbbtc@ticker";
        //return "wss://demo.piesocket.com:443/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self";

    }

    public List<String> welcomeMessage(){

        String message= "{\"method\": \"SUBSCRIBE\",\"params\": [\"btcusd_perp@bookTicker\"],\"id\": 1}";
        String message2= "{\"method\": \"SUBSCRIBE\",\"params\": [\"ethusd_perp@bookTicker\"],\"id\": 1}";
        String message3= "{\"method\": \"SUBSCRIBE\",\"params\": [\"htcusd_perp@bookTicker\"],\"id\": 1}";

        List<String > messages=new ArrayList<>();
//        messages.add(message);
//        messages.add(message2);
//        messages.add(message3);

        return messages;

    }
    @Override
    protected List<SingleCoinPrice> decodePriceInfo(ProviderPriceInfo providerPriceInfo){

        List<SingleCoinPrice> singleCoinPrices=new ArrayList<>();
        singleCoinPrices.add(new SingleCoinPrice());
        return singleCoinPrices;
    }
}
