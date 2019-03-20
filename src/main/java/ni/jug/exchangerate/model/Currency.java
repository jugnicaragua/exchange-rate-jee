package ni.jug.exchangerate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author aalaniz
 */
@Entity
@Table(name = "currency")
@SequenceGenerator(name = "seq", sequenceName = "currency_id_seq", allocationSize = 1)
@NamedQueries(
    @NamedQuery(name = "byIsoStringCode", query = "select c from Currency c where isoStringCode = :isoStringCode")
)
public class Currency extends SyntheticIdentifier<Integer> {

    private String isoNumericCode;
    private String isoStringCode;
    private String symbol;
    private String shortDescription;
    private String description;
    private Boolean domestic;
    private Boolean active;

    public Currency() {
    }

    @Column(name = "iso_numeric_code")
    public String getIsoNumericCode() {
        return isoNumericCode;
    }

    public void setIsoNumericCode(String isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    @Column(name = "iso_string_code")
    public String getIsoStringCode() {
        return isoStringCode;
    }

    public void setIsoStringCode(String isoStringCode) {
        this.isoStringCode = isoStringCode;
    }

    @Column(name = "symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "short_description")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "is_domestic")
    public Boolean getDomestic() {
        return domestic;
    }

    public void setDomestic(Boolean domestic) {
        this.domestic = domestic;
    }

    @Column(name = "is_active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Currency{" + "isoStringCode=" + isoStringCode + ", symbol=" + symbol + ", shortDescription=" + shortDescription +
                ", domestic=" + domestic + ", active=" + active + '}';
    }

}
