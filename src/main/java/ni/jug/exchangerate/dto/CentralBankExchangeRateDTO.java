package ni.jug.exchangerate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import ni.jug.exchangerate.model.CentralBankExchangeRate;

/**
 *
 * @author aalaniz
 */
public final class CentralBankExchangeRateDTO {

    private String currency;
    private LocalDate date;
    private BigDecimal amount;

    public CentralBankExchangeRateDTO() {
    }

    public CentralBankExchangeRateDTO(CentralBankExchangeRate entity) {
        this.currency = entity.getCurrency().getIsoStringCode();
        this.date = entity.getDate();
        this.amount = entity.getAmount();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CentralBankExchangeRateDTO{" + "currency=" + currency + ", date=" + date + ", amount=" + amount + '}';
    }

}
