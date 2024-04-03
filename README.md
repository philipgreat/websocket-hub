# websocket-hub


```
https://docs.rs/crate/crypto-ws-client/latest/source/src/clients/bitmex.rs



https://piehost.com/websocket-tester


测试 coinbase 是通过的

wss://ws-feed.exchange.coinbase.com:443/

{     "type": "subscribe",     "product_ids": [         "ETH-USD",         "ETH-EUR"     ],     "channels": [         "level2",         "heartbeat",         {             "name": "ticker",             "product_ids": [                 "ETH-BTC",                 "ETH-USD"             ]         }     ] }


wss://ws.kraken.com:443/

{"event":"subscribe","pair":["XBT/USD"],"subscription":{"name":"trade"}}


wss://www.bitmex.com/realtime

{"op":"subscribe","args":["trade:XBTUSD"]}


okx
wss://ws.okx.com:8443/ws/v5/public

{"op":"subscribe","args":[{"channel":"trades","instId":"BTC-USDT"}]}
```
