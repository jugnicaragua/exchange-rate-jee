package ni.jug.exchangerate.restful;

import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import ni.jug.exchangerate.dto.CentralBankExchangeRateDTO;
import ni.jug.exchangerate.logic.CentralBankExchangeRateProvider;
import ni.jug.exchangerate.logic.CurrencyProvider;
import ni.jug.exchangerate.model.Currency;

/**
 *
 * @author aalaniz
 */
@Path("centralBankExchangeRates")
public class CentralBankExchangeRateResource {

    private static final String DOLLAR_ISO_STRING_CODE = "USD";

    @Inject
    CentralBankExchangeRateProvider centralBankExchangeRateProvider;

    @Inject
    CurrencyProvider currencyProvider;

    private Currency getDollar() {
        return currencyProvider.getCurrencyByIsoStringCode(DOLLAR_ISO_STRING_CODE);
    }

    @Path("{year:\\d{4}}-{month:\\d{2}}")
    @GET
    public List<CentralBankExchangeRateDTO> findByPeriod(@PathParam("year") Integer year, @PathParam("month") Integer month) {
        LocalDate period = LocalDate.of(year, month, 1);
        return centralBankExchangeRateProvider.findByPeriodAndCurrency(period, getDollar());
    }

    @Path("current")
    @GET
    public List<CentralBankExchangeRateDTO> findByCurrentPeriod() {
        return centralBankExchangeRateProvider.findByCurrentPeriodAndCurrency(getDollar());
    }

}
