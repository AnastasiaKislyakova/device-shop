package ru.kislyakova.anastasia.shop.controller.rest;

import ru.kislyakova.anastasia.shop.model.Bill;
import ru.kislyakova.anastasia.shop.controller.rest.handlers.BillWebHandler;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path("bill")
public class BillRestController {
    private BillWebHandler billWebHandler;

    @Inject
    public BillRestController(BillWebHandler billWebHandler) {
        this.billWebHandler = billWebHandler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> getBills(@QueryParam("customerId") Long clientId,
                               @QueryParam("deviceIds") String deviceIdsStr,
                               @QueryParam("purchaseDateTime") String dateTimeStr,
                               @QueryParam("purchaseDateTimeFrom") String dateTimeFromStr,
                               @QueryParam("purchaseDateTimeTo") String dateTimeToStr,
                               @QueryParam("totalPrice") BigDecimal totalPrice,
                               @QueryParam("totalPriceFrom") BigDecimal totalPriceFrom,
                               @QueryParam("totalPriceTo") BigDecimal totalPriceTo,
                               @QueryParam("orderBy") String sortBy,
                               @DefaultValue("10") @QueryParam("pageItems") int count,
                               @DefaultValue("1") @QueryParam("page") int pageNumber) {

        return billWebHandler.getBills(clientId, deviceIdsStr, dateTimeStr, dateTimeFromStr, dateTimeToStr,
                totalPrice, totalPriceFrom, totalPriceTo, sortBy, count, pageNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(Bill bill) {
        return billWebHandler.createBill(bill);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getBill(@PathParam("id") long id) {
        return billWebHandler.getBill(id);
    }

}
