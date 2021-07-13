package com.api.orderfx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class OrderFxApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderFxApplication.class, args);
    }

//    public static void main(String[] args) {
//        String input1 = "XAUUSD Buy 1798\n" +
//                "TP 1804\n" +
//                "TP 1807\n" +
//                "TP 1812\n" +
//                "SL 1790\n";
//
//        String input2 = "GBPJY BUY\n" +
//                "153.000\n" +
//                "Tp1 153.250\n" +
//                "Tp2 153.500\n" +
//                "SL@152.400";
//
//
//        String input3 = "GBPNZD BUY  1.98000\n" +
//                "\n" +
//                "Tp1.    1.98400\n" +
//                "Tp2.    1.99000\n" +
//                "Sl       1.97500";
//
//        String input4 = "EURGBP SELL 0.86000\n" +
//                "\n" +
//                "Tp1.   0.85700\n" +
//                "Tp2.   0.85400\n" +
//                "Sl.      0.86400";
//
//        List<String> listInput = new ArrayList<>();
//        listInput.add(input1);
//        listInput.add(input2);
//        listInput.add(input3);
//        listInput.add(input4);
//
//        listInput.forEach(System.out::println);
//
//        List<String> collect = listInput.stream().map(s -> s.replaceAll("[^a-zA-Z0-9.]", " ").toUpperCase().replaceAll("\n", " ").replaceAll("\r", " ").replaceAll(" +", " ").trim()).collect(Collectors.toList());
//
//        System.out.println("=====================");
//        collect.forEach(System.out::println);
//
////        collect.stream().map(s -> s.split())
//
//
//    }


}
