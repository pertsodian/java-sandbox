package services;

import domain.ReferenceRate;

public interface IReferenceRateHelper {
    ReferenceRate mergeCrossReferenceRates(ReferenceRate first, ReferenceRate second);
}
