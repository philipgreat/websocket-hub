package com.newbie.indexprice.core;

import java.util.List;

public class PriceProvider {
    private String name;
    private String websocketAddress;
    transient String extraInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsocketAddress() {
        return websocketAddress;
    }

    public void setWebsocketAddress(String websocketAddress) {
        this.websocketAddress = websocketAddress;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    protected SingleCoinPrice decodePrice(ProviderPriceInfo providerPriceInfo){
        throw new IndexPriceException("Calling method decodePrice is not allowed in class PriceProvider");
    }
    protected List<SingleCoinPrice> decodePriceInfo(ProviderPriceInfo providerPriceInfo){
        throw new IndexPriceException("Calling method decodePrice is not allowed in class PriceProvider");
    }
    public List<String> welcomeMessage(){

        return null;

    }
    @Override
    public String toString() {
        return "PriceProvider{" +
                "name='" + name + '\'' +
                ", websocketAddress='" + websocketAddress + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                '}';
    }
}


