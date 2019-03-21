package ni.jug.exchangerate.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author aalaniz
 */
@Entity
@Table(name = "cb_exchange_rate")
@SequenceGenerator(name = "seq", sequenceName = "cb_exchange_rate_id_seq", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "findByBankAndDate", query = "select cb from CommercialBankExchangeRate cb where bank = :bank and date = :date"),
    @NamedQuery(name = "findByDate", query = "select cb from CommercialBankExchangeRate cb join fetch cb.currency where cb.date = :date"),
    @NamedQuery(name = "findByBankAndRangeOfDates", query = "select cb from CommercialBankExchangeRate cb join fetch cb.currency " +
            "where cb.bank = :bank and cb.date >= :firstDay and cb.date <= :lastDay"),
    @NamedQuery(name = "findByRangeOfDates", query = "select cb from CommercialBankExchangeRate cb join fetch cb.currency " +
            "where cb.date >= :firstDay and cb.date <= :lastDay"),
    @NamedQuery(name = "findBankByDate", query = "select cb.bank from CommercialBankExchangeRate cb where date = :date")
})
public class CommercialBankExchangeRate extends OnlyCreationTimeAndSythenticIdentifier<Integer> {

    private Currency currency;
    private Bank bank;
    private LocalDate date;
    private BigDecimal sell;
    private BigDecimal buy;
    private Boolean bestSellPrice;
    private Boolean bestBuyPrice;

    public CommercialBankExchangeRate() {
    }

    public CommercialBankExchangeRate(Currency currency, Bank bank, LocalDate date, BigDecimal sell, BigDecimal buy,
            Boolean bestSellPrice, Boolean bestBuyPrice) {
        this.currency = currency;
        this.bank = bank;
        this.date = date;
        this.sell = sell;
        this.buy = buy;
        this.bestSellPrice = bestSellPrice;
        this.bestBuyPrice = bestBuyPrice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Column(name = "bank")
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Column(name = "exchange_rate_date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "sell")
    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    @Column(name = "buy")
    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    @Column(name = "is_best_sell_price")
    public Boolean getBestSellPrice() {
        return bestSellPrice;
    }

    public void setBestSellPrice(Boolean bestSellPrice) {
        this.bestSellPrice = bestSellPrice;
    }

    @Column(name = "is_best_buy_price")
    public Boolean getBestBuyPrice() {
        return bestBuyPrice;
    }

    public void setBestBuyPrice(Boolean bestBuyPrice) {
        this.bestBuyPrice = bestBuyPrice;
    }

    @Override
    public String toString() {
        return "CommercialBankExchangeRate{" + "currency=" + currency + ", bank=" + bank + ", date=" + date + ", sell=" + sell +
                ", buy=" + buy + ", bestSellPrice=" + bestSellPrice + ", bestBuyPrice=" + bestBuyPrice + '}';
    }

}
