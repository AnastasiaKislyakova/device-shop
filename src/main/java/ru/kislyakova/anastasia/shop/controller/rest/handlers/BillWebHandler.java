package ru.kislyakova.anastasia.shop.controller.rest.handlers;

import ru.kislyakova.anastasia.shop.model.Bill;
import ru.kislyakova.anastasia.shop.model.Device;
import ru.kislyakova.anastasia.shop.service.BillService;
import ru.kislyakova.anastasia.shop.service.ClientService;
import ru.kislyakova.anastasia.shop.service.DeviceService;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.utils.ParsingUtil;
import ru.kislyakova.anastasia.shop.utils.QueryValidator;
import ru.kislyakova.anastasia.shop.model.BillItem;
import ru.kislyakova.anastasia.shop.model.Client;
import ru.kislyakova.anastasia.shop.service.BillFilter;
import ru.kislyakova.anastasia.shop.utils.ModelValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillWebHandler {

    private BillService billService;
    private ClientService clientService;
    private DeviceService deviceService;

    @Inject
    public BillWebHandler(BillService billService, ClientService clientService, DeviceService deviceService) {
        this.billService = billService;
        this.clientService = clientService;
        this.deviceService = deviceService;
    }

    public List<Bill> getBills(Long clientId,
                               String deviceIdsStr,
                               String dateTimeStr,
                               String dateTimeFromStr,
                               String dateTimeToStr,
                               BigDecimal totalPrice,
                               BigDecimal totalPriceFrom,
                               BigDecimal totalPriceTo,
                               String sortBy,
                               int count,
                               int pageNumber) {
        List<Long> deviceIds = ParsingUtil.getDeviceIds(deviceIdsStr);
        LocalDateTime dateTimeFrom = ParsingUtil.getLocalDateTime(dateTimeFromStr);
        LocalDateTime dateTimeTo = ParsingUtil.getLocalDateTime(dateTimeToStr);
        LocalDateTime dateTime = ParsingUtil.getLocalDateTime(dateTimeStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        BillFilter filter = new BillFilter()
                .withClientId(clientId)
                .withDateTime(dateTime)
                .withDateTimeFrom(dateTimeFrom)
                .withDateTimeTo(dateTimeTo)
                .withDeviceIds(deviceIds)
                .withTotalPrice(totalPrice)
                .withTotalPriceFrom(totalPriceFrom)
                .withTotalPriceTo(totalPriceTo)
                .withSortParams(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber - 1);
        ModelValidator.validateEntity(filter);
        return billService.getBills(filter);
    }

    public Bill createBill(Bill bill) {
        QueryValidator.checkEmptyRequest(bill);
        ModelValidator.validateEntity(bill);
        Client client = clientService.getClientById(bill.getClientId());
        QueryValidator.checkIfNotFound(client, String.format("Customer with id %s doesn't exist", bill.getClientId()));
        for (BillItem billItem : bill.getBillItems()) {
            ModelValidator.validateEntity(billItem);
            Device device = deviceService.getDeviceById(billItem.getDeviceId());
            QueryValidator.checkIfNotFound(device, String.format("Device with id %s doesn't exist", billItem.getDeviceId()));
        }
        return billService.saveBill(bill);
    }

    public Bill getBill(long id) {
        Bill bill = billService.getBillById(id);
        QueryValidator.checkIfNotFound(bill, String.format("Bill with id %s doesn't exist", id));
        return bill;
    }
}
