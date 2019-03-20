package ni.jug.exchangerate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import ni.jug.exchangerate.model.Bank;
import ni.jug.exchangerate.model.CommercialBankExchangeRate;

/**
 *
 * @author aalaniz
 */
public final class CommercialBankExchangeRateDTO {

    private String currency;
    private Bank bank;
    private LocalDate date;
    private BigDecimal sell;
    private BigDecimal buy;
    private Boolean bestSellPrice;
    private Boolean bestBuyPrice;

    public CommercialBankExchangeRateDTO() {
    }

    public CommercialBankExchangeRateDTO(CommercialBankExchangeRate entity) {
        this.currency = entity.getCurrency().getIsoStringCode();
        this.bank = entity.getBank();
        this.date = entity.getDate();
        this.sell = entity.getSell();
        this.buy = entity.getBuy();
        this.bestSellPrice = entity.getBestSellPrice();
        this.bestBuyPrice = entity.getBestBuyPrice();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public Boolean getBestSellPrice() {
        return bestSellPrice;
    }

    public void setBestSellPrice(Boolean bestSellPrice) {
        this.bestSellPrice = bestSellPrice;
    }

    public Boolean getBestBuyPrice() {
        return bestBuyPrice;
    }

    public void setBestBuyPrice(Boolean bestBuyPrice) {
        this.bestBuyPrice = bestBuyPrice;
    }

    @Override
    public String toString() {
        return "CommercialBankExchangeRateDTO{" + "currency=" + currency + ", bank=" + bank + ", date=" + date + ", sell=" + sell +
                ", buy=" + buy + ", bestSellPrice=" + bestSellPrice + ", bestBuyPrice=" + bestBuyPrice + '}';
    }

}
