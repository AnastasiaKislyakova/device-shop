package ru.kislyakova.anastasia.shop.controller.rest;

import ru.kislyakova.anastasia.shop.controller.rest.handlers.DeviceWebHandler;
import ru.kislyakova.anastasia.shop.model.Device;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path("device")
public class DeviceRestController {
    private DeviceWebHandler deviceWebHandler;

    @Inject
    public DeviceRestController(DeviceWebHandler deviceWebHandler) {
        this.deviceWebHandler = deviceWebHandler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@QueryParam("deviceType") String deviceTypeStr,
                                   @QueryParam("colorName") String colorName,
                                   @QueryParam("colorRGB") Integer colorRGB,
                                   @QueryParam("price") BigDecimal price,
                                   @QueryParam("priceFrom") BigDecimal priceFrom,
                                   @QueryParam("priceTo") BigDecimal priceTo,
                                   @QueryParam("manufactureDate") String dateStr,
                                   @QueryParam("manufactureDateFrom") String dateFromStr,
                                   @QueryParam("manufactureDateTo") String dateToStr,
                                   @QueryParam("modelName") String model,
                                   @QueryParam("manufacturer") String manufacturer,
                                   @QueryParam("orderBy") String sortBy,
                                   @DefaultValue("10") @QueryParam("pageItems") int count,
                                   @DefaultValue("1") @QueryParam("page") int pageNumber) {

        return deviceWebHandler.getDevices(deviceTypeStr, colorName, colorRGB, price, priceFrom, priceTo, dateStr,
                dateFromStr, dateToStr, model, manufacturer, sortBy, count, pageNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device) {
        return deviceWebHandler.createDevice(device);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@PathParam("id") long id) {
        return deviceWebHandler.getDevice(id);
    }
}

