package services;

import domain.ReferenceRate;

import java.util.List;

public interface IReferenceRateQueryService {
    ReferenceRate getReferenceRate(String ccy1, String ccy2);
    List<ReferenceRate> getAllAvailableReferenceRate(String ccy);
}
