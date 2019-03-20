package ni.jug.exchangerate.restful;

import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import ni.jug.exchangerate.dto.CommercialBankExchangeRateDTO;
import ni.jug.exchangerate.logic.CommercialBankExchangeRateProvider;
import ni.jug.exchangerate.model.Bank;

/**
 *
 * @author aalaniz
 */
@Path("commercialBankExchangeRates")
public class CommercialBankExchangeRateResource {

    @Inject
    CommercialBankExchangeRateProvider commercialBankExchangeRateProvider;

    @Path("{year:\\d{4}}-{month:\\d{2}}-{day:\\d{2}}")
    @GET
    public List<CommercialBankExchangeRateDTO> findByDate(@PathParam("year") Integer year, @PathParam("month") Integer month,
            @PathParam("day") Integer day) {
        LocalDate date = LocalDate.of(year, month, day);
        return commercialBankExchangeRateProvider.findByDate(date);
    }

    @Path("today")
    @GET
    public List<CommercialBankExchangeRateDTO> findByCurrentDate() {
        return commercialBankExchangeRateProvider.findByDate(LocalDate.now());
    }

    @Path("{year:\\d{4}}-{month:\\d{2}}")
    @GET
    public List<CommercialBankExchangeRateDTO> findByPeriod(@PathParam("year") Integer year, @PathParam("month") Integer month) {
        LocalDate period = LocalDate.of(year, month, 1);
        return commercialBankExchangeRateProvider.findByPeriod(period);
    }

    @Path("current-period")
    @GET
    public List<CommercialBankExchangeRateDTO> findByCurrentPeriod() {
        return commercialBankExchangeRateProvider.findByCurrentPeriod();
    }

    @Path("{bank}")
    @GET
    public List<CommercialBankExchangeRateDTO> findByBankAndCurrentPeriod(@PathParam("bank") String bank) {
        Bank bankDomain = Bank.valueOf(bank.toUpperCase());
        LocalDate now = LocalDate.now();
        return commercialBankExchangeRateProvider.findByBankAndPeriod(bankDomain, now);
    }

    @Path("{bank}/{year:\\d{4}}-{month:\\d{2}}")
    @GET
    public List<CommercialBankExchangeRateDTO> findByBankAndPeriod(@PathParam("bank") String bank, @PathParam("year") Integer year,
            @PathParam("month") Integer month) {
        Bank bankDomain = Bank.valueOf(bank.toUpperCase());
        LocalDate now = LocalDate.of(year, month, 1);
        return commercialBankExchangeRateProvider.findByBankAndPeriod(bankDomain, now);
    }

}
