package services;

import domain.ReferenceRate;

import java.math.BigDecimal;
import java.util.Optional;

public class ReferenceRateCalculator {

    private final IReferenceRateQueryService referenceRateQueryService;
    private final IReferenceRateHelper referenceRateHelper;
    private final ICrossReferenceRatesRetriever crossReferenceRatesRetriever;

    public ReferenceRateCalculator(
            IReferenceRateQueryService referenceRateQueryService,
            IReferenceRateHelper referenceRateHelper,
            ICrossReferenceRatesRetriever crossReferenceRatesRetriever) {
        this.referenceRateQueryService = referenceRateQueryService;
        this.referenceRateHelper = referenceRateHelper;
        this.crossReferenceRatesRetriever = crossReferenceRatesRetriever;
    }

    public final ReferenceRate retrieveReferenceRate(String ccy1, String ccy2) {
        return Optional
                    .ofNullable(referenceRateQueryService.getReferenceRate(ccy1, ccy2))
                    .orElseGet(() -> calculateReferenceRate(ccy1, ccy2));
    }

    private ReferenceRate calculateReferenceRate(String ccy1, String ccy2) {
        return crossReferenceRatesRetriever
                    .retrieveCrossReferenceRates(ccy1, ccy2)
                    .stream()
                    .reduce(
                            new ReferenceRate(ccy1, ccy2, BigDecimal.ONE),
                            referenceRateHelper::mergeCrossReferenceRates);
    }
}
