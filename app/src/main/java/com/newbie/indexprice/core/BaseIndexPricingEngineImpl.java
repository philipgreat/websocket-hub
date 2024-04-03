package com.newbie.indexprice.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BaseIndexPricingEngineImpl implements IndexPricingEngine {

    ConcurrentLinkedDeque<SingleCoinPrice> singleCoinPriceQueue;
    List<PriceProvider> priceProviderList;
    List<PricingInfoCollector> pricingInfoCollectorList;//current price, current all price,

    private static final EventLoopGroup group = new NioEventLoopGroup();

    @Override
    public void init() {
        singleCoinPriceQueue=new ConcurrentLinkedDeque<>();
        priceProviderList=new ArrayList<>();
        pricingInfoCollectorList =new ArrayList<>();
    }

    protected void addPriceProvider(PriceProvider priceProvider) {
        priceProviderList.add(priceProvider);
    }
    protected void addPriceCollector(PricingInfoCollector pricingInfoCollector) {
        pricingInfoCollectorList.add(pricingInfoCollector);
    }


    @Override
    public void start() {
        //collect price info from difference channels, and unified all the price info into
        //one form
        //iterate all the collectors to collect

        new Thread(()->{

            SingleCoinPrice singleCoinPrice=singleCoinPriceQueue.removeFirst();
            pricingInfoCollectorList.forEach(pricingInfoCollector -> {
                pricingInfoCollector.collect(singleCoinPrice);
            });

        });

        priceProviderList.forEach(priceProvider -> {

            try {
                connectToWebSocket(priceProvider,group);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });



        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            group.shutdownGracefully();
        }));

    }

    private void collectMessage(PriceProvider priceProvider, TextWebSocketFrame frame){

        pricingInfoCollectorList.forEach(pricingInfoCollector -> {
            ProviderPriceInfo providerPriceInfo=new ProviderPriceInfo();
            providerPriceInfo.setSource(priceProvider.getWebsocketAddress());
            providerPriceInfo.setMessage(frame.text());
            //SingleCoinPrice singleCoinPrice=priceProvider.decodePrice(providerPriceInfo);
            List<SingleCoinPrice> singleCoinPrices=priceProvider.decodePriceInfo(providerPriceInfo);
            singleCoinPrices.forEach(singleCoinPrice -> {
                singleCoinPriceQueue.add(singleCoinPrice);
                //pricingInfoCollector.collect(singleCoinPrice);
            });

        });
    }

    private  void connectToWebSocket(PriceProvider priceProvider, EventLoopGroup sharedGroup) throws InterruptedException, URISyntaxException, SSLException {


        URI uri=new URI(priceProvider.getWebsocketAddress());
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(sharedGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        applySSLContextIfNeeded(priceProvider,pipeline,ch);

                        pipeline.addLast(new HttpClientCodec(),
                                new HttpObjectAggregator(8192),
                                new WebSocketClientProtocolHandler(uri, WebSocketVersion.V13, null, false, null, 65536),
                                new SimpleUserEventChannelHandler<>() {
                                    @Override
                                    protected void eventReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

                                    }
                                },
                                new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
                                        System.out.println("Received message from " + priceProvider + ": " + frame.text());
                                        collectMessage(priceProvider,frame);

                                    }
                                });
                    }
                });

        Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();

        priceProvider.welcomeMessage().forEach(msg->{
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        });

        //channel.writeAndFlush(new TextWebSocketFrame("Hello, WebSocket"));
        // 不要在这里关闭Future，因为我们想要保持连接开放
        //channel.closeFuture().await();
    }

    private void applySSLContextIfNeeded(PriceProvider priceProvider, ChannelPipeline pipeline, Channel ch) throws SSLException, URISyntaxException {

        if(priceProvider.getWebsocketAddress().startsWith("wss")){
            SslContext sslCtx= SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            URI uri=new URI(priceProvider.getWebsocketAddress());
            pipeline.addLast(sslCtx.newHandler(ch.alloc(), uri.getHost(), uri.getPort()));

        }

    }


}

//final SslContext sslCtx;
//86          if (ssl) {
//        87              sslCtx = SslContextBuilder.forClient()
//88                  .trustManager(InsecureTrustManagerFactory.INSTANCE).build();