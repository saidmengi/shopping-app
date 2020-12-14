package com.sevendev.shoppingapp.product.domain;

public enum  MoneyType {
    USD("Dolar", "$"),
    EURO("Euro", "E"),
    TL("Turk Lirasi", "T");

    private String label;
    private String symbol;
    MoneyType(String label, String symbol){
        this.label = label;
        this.symbol = symbol;
    }
}
