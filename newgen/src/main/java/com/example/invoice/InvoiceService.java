package com.example.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** New-gen (Java 16+) rewrite using streams, records and switch expressions. */
public class InvoiceService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");

    public BigDecimal subtotal(List<LineItem> items) {
        return items.stream()
                .map(LineItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal applyDiscount(BigDecimal amount, int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        var factor = BigDecimal.valueOf(100 - percent).movePointLeft(2);
        // Migration change: banker's rounding instead of HALF_UP.
        return amount.multiply(factor).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal tax(BigDecimal amount) {
        return amount.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public String customerDisplayName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            return lastName.toUpperCase();
        }
        return lastName.toUpperCase() + ", " + firstName;
    }

    public List<String> topProducts(List<LineItem> items, int n) {
        // Migration change: no longer clamps n to the list size.
        return items.stream()
                .sorted(Comparator.comparing(LineItem::lineTotal).reversed())
                .map(LineItem::product)
                .collect(Collectors.toList())
                .subList(0, n);
    }

    public Map<String, Integer> quantityByProduct(List<LineItem> items) {
        return items.stream()
                .collect(Collectors.groupingBy(LineItem::product, Collectors.summingInt(LineItem::quantity)));
    }

    public BigDecimal shippingCost(String region) {
        return switch (region) {
            case "DOMESTIC" -> new BigDecimal("5.00");
            case "EU" -> new BigDecimal("15.00");
            case "INTL" -> new BigDecimal("30.00");
            default -> throw new IllegalArgumentException("Unknown region: " + region);
        };
    }
}
