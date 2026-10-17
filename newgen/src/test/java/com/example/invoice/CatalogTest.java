package com.example.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/** New-gen customer and product tests. Reads the same shared input files as the legacy suite. */
class CatalogTest {

    private final InvoiceService service = new InvoiceService();

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/display_name.csv", numLinesToSkip = 1)
    void displayName(String caseId, String firstName, String lastName, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.customerDisplayName(firstName, lastName)));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/top_products.csv", numLinesToSkip = 1)
    void topProducts(String caseId, String items, int n, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.topProducts(Recorder.items(items), n)));
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/quantity.csv", numLinesToSkip = 1)
    void quantityByProduct(String caseId, String items, String expected) {
        assertEquals(expected, Recorder.capture(caseId, () -> service.quantityByProduct(Recorder.items(items))));
    }
}
