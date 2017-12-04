package services;

import domain.ReferenceRate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BfsCrossReferenceRatesRetrieverTest {

    @Mock
    private IReferenceRateQueryService referenceRateQueryService;

    @InjectMocks
    private BfsCrossReferenceRatesRetriever crossReferenceRatesRetriever;

    @Before
    public void setUp() {
        mockRefRate("EUR/GBP");
        mockRefRate("EUR/USD");
        mockRefRate("USD/JPY");
        mockRefRate("GBP/JPY");
        mockRefRate("CHF/RUB");
        mockRefRate("USD/CHF");

        mockRefRates("EUR", "EUR/GBP", "EUR/USD");
        mockRefRates("USD", "EUR/USD", "USD/JPY", "USD/CHF");
        mockRefRates("JPY", "USD/JPY", "GBP/JPY");
        mockRefRates("GBP", "EUR/GBP", "GBP/JPY");
        mockRefRates("CHF", "CHF/RUB", "USD/CHF");
    }

    private void mockRefRate(String ccyPair) {
        when(referenceRateQueryService.getReferenceRate(getFirstCcy(ccyPair), getSecondCcy(ccyPair)))
                .thenReturn(createRefRate(ccyPair));
    }

    private void mockRefRates(String fromCcy, String... ccyPairs) {
        when(referenceRateQueryService.getAllAvailableReferenceRate(fromCcy))
                .thenReturn(Stream
                                .of(ccyPairs)
                                .map(this::createRefRate)
                                .collect(Collectors.toList()));
    }

    private ReferenceRate createRefRate(String ccyPair) {
        return new ReferenceRate(getFirstCcy(ccyPair), getSecondCcy(ccyPair), BigDecimal.ONE);
    }

    private String getFirstCcy(String ccyPair) {
        return ccyPair.substring(0, 3);
    }

    private String getSecondCcy(String ccyPair) {
        return ccyPair.substring(ccyPair.length() - 3, ccyPair.length());
    }

    @Test
    public void testRetrieveCrossReferenceRates_NotFound() {
        List<String> expectedResults = Collections.emptyList();
        verifyResults(expectedResults, crossReferenceRatesRetriever.retrieveCrossReferenceRates("EUR", "CAD"));
    }

    @Test
    public void testRetrieveCrossReferenceRates_NoCrossesRequired() {
        List<String> expectedResults = Collections.singletonList("EUR/GBP");
        verifyResults(expectedResults, crossReferenceRatesRetriever.retrieveCrossReferenceRates("EUR", "GBP"));
    }

    @Test
    public void testRetrieveCrossReferenceRates_ShorterDistanceReturned() {
        List<String> expectedResults = Arrays.asList("USD/CHF", "EUR/USD");
        verifyResults(expectedResults, crossReferenceRatesRetriever.retrieveCrossReferenceRates("EUR", "CHF"));
    }

    @Test
    public void testRetrieveCrossReferenceRates_LongestPath() {
        List<String> expectedResults = Arrays.asList("CHF/RUB", "USD/CHF", "EUR/USD");
        verifyResults(expectedResults, crossReferenceRatesRetriever.retrieveCrossReferenceRates("EUR", "RUB"));
    }

    private void verifyResults(List<String> expectedResults, List<ReferenceRate> results) {
        System.out.println("Expected: " + expectedResults);
        System.out.println("Actual: " + results);

        assertEquals(expectedResults.size(), results.size());
        IntStream
                .range(0, expectedResults.size())
                .forEach(index -> assertEquals(expectedResults.get(index), results.get(index).getCcyPair()));
    }
}
