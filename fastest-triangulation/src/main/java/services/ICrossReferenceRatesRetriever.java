package services;

import domain.ReferenceRate;

import java.util.List;

public interface ICrossReferenceRatesRetriever {
    List<ReferenceRate> retrieveCrossReferenceRates(String startCcy, String endCcy);
}
