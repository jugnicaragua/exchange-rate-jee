 package ni.jug.exchangerate.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import ni.jug.exchangerate.util.Dates;

/**
 *
 * @author aalaniz
 */
@Entity
@Table(name =  "ncb_exchange_rate")
@NamedQueries({
    @NamedQuery(name = "countByCurrentPeriodAndCurrency", query = "select count(cber) from CentralBankExchangeRate cber where " +
            "cber.date >= :firstDay and cber.date <= :lastDay and cber.currency = :currency"),
    @NamedQuery(name = "findByPeriodAndCurrency", query = "select cber from CentralBankExchangeRate cber join fetch cber.currency " +
            "where cber.date >= :firstDay and cber.date <= :lastDay and cber.currency = :currency")
})
public class CentralBankExchangeRate extends OnlyCreationTime {

    private Integer id;
    private Currency currency;
    private LocalDate date;
    private BigDecimal amount;

    public CentralBankExchangeRate() {
    }

    public CentralBankExchangeRate(Currency currency, LocalDate date, BigDecimal amount) {
        this.currency = currency;
        this.date = date;
        this.amount = amount;
        this.id = Dates.toIsoNumericFormat(this.date);
    }

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Column(name = "exchange_rate_date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "exchange_rate_amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CentralBankExchangeRate)) {
            return false;
        }
        final CentralBankExchangeRate other = (CentralBankExchangeRate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
