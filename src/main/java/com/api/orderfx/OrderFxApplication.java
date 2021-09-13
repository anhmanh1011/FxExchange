package com.api.orderfx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class OrderFxApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderFxApplication.class, args);
    }

    //    private static String symbol = getEnvOrDefault("SYMBOL", "EURUSD");
//    private static String token = getEnvOrDefault("TOKEN", "gHevelEGc2mk4As6FzSvBp4aiPPEtEcHQzoJlunpqPi0blK26B5sHUaTun3ToTQZ");
//    private static String accountId = getEnvOrDefault("ACCOUNT_ID", "ac738a46-c3c8-4c43-a65b-d34f66f2d3ba");
//
//    public static void main2(String[] args) {
//        long start = new Date().getTime();
//
//        try {
//            MetaApi api = new MetaApi(token);
//            // Add test MetaTrader account
//            MetatraderAccount account = api.getMetatraderAccountApi().getAccount(accountId).get();
//
//
//            System.out.println("Waiting for API server to connect to broker (may take couple of minutes)");
//            account.waitConnected().get();
//
//            // connect to MetaApi API
//            MetaApiConnection connection = account.connect().get();
//
//            SynchronizationListener quoteListener = new QuoteListener();
//            connection.addSynchronizationListener(quoteListener);
//
//            System.out.println("Waiting for SDK to synchronize to terminal state "
//                    + "(may take some time depending on your history size)");
//            connection.waitSynchronized().get();
//            // trade
//            System.out.println("Submitting pending order");
//            try {
//                MetatraderTradeResponse result = connection
//                        .createLimitBuyOrder("GBPUSD", 0.07, 1.0, 0.9, 2.0, new PendingTradeOptions() {{
//                            comment = "comm";
//                            clientId = "TE_GBPUSD_7hyINWqAlE";
//                        }}).get();
//                System.out.println("Trade successful, result code is " + result.stringCode);
//            } catch (ExecutionException err) {
//                System.out.println("Trade failed with result code " + ((TradeException) err.getCause()).stringCode);
//            }
//
//
//            while (true)
//                Thread.sleep(1000);
//
//
//        } catch (Exception err) {
//            System.err.println(err);
//        }
//
//        System.exit(0);
//    }
//
//    private static String getEnvOrDefault(String name, String defaultValue) {
//        String result = System.getenv(name);
//        return (result != null ? result : defaultValue);
//    }
//
//    private static class QuoteListener extends SynchronizationListener {
//
//        @Override
//        public CompletableFuture<Void> onAccountInformationUpdated(String instanceIndex, MetatraderAccountInformation accountInformation) {
//            try {
//                System.out.println("  accountInformation " + asJson(accountInformation));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            return CompletableFuture.completedFuture(null);
//        }
//
//        @Override
//        public CompletableFuture<Void> onSymbolPriceUpdated(String instanceIndex, MetatraderSymbolPrice price) {
//            try {
//                System.out.println(price.symbol + " price updated " + asJson(price));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            return CompletableFuture.completedFuture(null);
//        }
//
//        @Override
//        public CompletableFuture<Void> onCandlesUpdated(String instanceIndex, List<MetatraderCandle> candles,
//                                                        Double equity, Double margin, Double freeMargin, Double marginLevel, Double accountCurrencyExchangeRate) {
//            for (MetatraderCandle candle : candles) {
//                try {
//                    System.out.println(" candle updated " + asJson(candle));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//            }
//            return CompletableFuture.completedFuture(null);
//        }
//
//        @Override
//        public CompletableFuture<Void> onTicksUpdated(String instanceIndex, List<MetatraderTick> ticks,
//                                                      Double equity, Double margin, Double freeMargin, Double marginLevel, Double accountCurrencyExchangeRate) {
//            for (MetatraderTick tick : ticks) {
//                try {
//                    System.out.println(" tick updated " + asJson(tick));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//            }
//            return CompletableFuture.completedFuture(null);
//        }
//
//        @Override
//        public CompletableFuture<Void> onBooksUpdated(String instanceIndex, List<MetatraderBook> books,
//                                                      Double equity, Double margin, Double freeMargin, Double marginLevel, Double accountCurrencyExchangeRate) {
//            for (MetatraderBook book : books) {
//                try {
//                    System.out.println(" order book updated " + asJson(book));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//            }
//            return CompletableFuture.completedFuture(null);
//        }
//
//        @Override
//        public CompletableFuture<Void> onSubscriptionDowngraded(String instanceIndex, String symbol,
//                                                                List<MarketDataSubscription> updates, List<MarketDataUnsubscription> unsubscriptions) {
//            System.out.println("Market data subscriptions for " + symbol
//                    + " were downgraded by the server due to rate limits");
//            return CompletableFuture.completedFuture(null);
//        }
//    }
//
//    private static String asJson(Object object) throws JsonProcessingException {
//        return JsonMapper.getInstance().writeValueAsString(object);
//    }
    @PostConstruct
    public void init() {

//		String[] id = TimeZone.getAvailableIDs();
//		System.out.println("Danh sach id co san la: ");
//		for (int i = 0; i < id.length; i++) {
//			System.out.println(id[i]);
//		}

        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.out.println(new Date());
        System.out.println(LocalDateTime.now());
    }

}
