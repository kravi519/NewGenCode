package com.example.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/** New-gen pricing tests. Reads the same shared input files as the legacy suite. */
class PricingTest {

    private final InvoiceService service = new InvoiceService();

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/subtotal.csv", numLinesToSkip = 1)
    void subtotal(String caseId, String items, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.subtotal(Recorder.items(items))));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/discount.csv", numLinesToSkip = 1)
    void discount(String caseId, BigDecimal amount, int percent, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.applyDiscount(amount, percent)));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/tax.csv", numLinesToSkip = 1)
    void tax(String caseId, BigDecimal amount, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.tax(amount)));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/shipping.csv", numLinesToSkip = 1)
    void shipping(String caseId, String region, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.shippingCost(region)));
    }
}
