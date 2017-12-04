package services;

import domain.ReferenceRate;

import java.util.*;
import java.util.stream.Stream;

public class BfsCrossReferenceRatesRetriever implements ICrossReferenceRatesRetriever {

    private static final String INITIAL = "INITIAL";

    private final IReferenceRateQueryService referenceRateQueryService;

    public BfsCrossReferenceRatesRetriever(IReferenceRateQueryService referenceRateQueryService) {
        this.referenceRateQueryService = referenceRateQueryService;
    }

    @Override
    public List<ReferenceRate> retrieveCrossReferenceRates(String startCcy, String endCcy) {
        Map<String, String> pathMap = breadthFirstSearch(startCcy, endCcy);
        return traversePath(pathMap, startCcy, endCcy);
    }

    private Map<String, String> breadthFirstSearch(String startCcy, String endCcy) {
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put(startCcy, INITIAL);

        Queue<String> pendingCcys = new LinkedList<>();
        pendingCcys.add(startCcy);

        while (!pendingCcys.isEmpty()) {
            String currentCcy = pendingCcys.poll();
            if (currentCcy.equals(endCcy))
                break;

            referenceRateQueryService
                    .getAllAvailableReferenceRate(currentCcy)
                    .stream()
                    .flatMap(refRate -> Stream.of(refRate.getCcy1(), refRate.getCcy2()))
                    .filter(ccy -> !ccy.equals(currentCcy))
                    .filter(ccy -> !pathMap.containsKey(ccy))
                    .forEach(ccy -> {
                        pathMap.put(ccy, currentCcy);
                        pendingCcys.add(ccy);
                    });
        }

        return pathMap;
    }

    private List<ReferenceRate> traversePath(Map<String, String> pathMap, String startCcy, String endCcy) {
        if (!pathMap.containsKey(endCcy))
            return Collections.emptyList();

        List<ReferenceRate> results = new ArrayList<>();
        String ccyInPath = endCcy;
        while (!ccyInPath.equals(startCcy)) {
            String previousCcyInPath = pathMap.get(ccyInPath);
            results.add(referenceRateQueryService.getReferenceRate(previousCcyInPath, ccyInPath));
            ccyInPath = previousCcyInPath;
        }

        return results;
    }
}
