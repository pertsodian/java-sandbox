package domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReferenceRate {
    private final String ccy1;
    private final String ccy2;
    private final BigDecimal rate;

    public String getCcyPair() {
        return ccy1 + "/" + ccy2;
    }
}
