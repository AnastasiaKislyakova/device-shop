package ru.kislyakova.anastasia.shop.controller.rest.handlers;

import ru.kislyakova.anastasia.shop.model.Device;
import ru.kislyakova.anastasia.shop.model.DeviceType;
import ru.kislyakova.anastasia.shop.service.ColorService;
import ru.kislyakova.anastasia.shop.service.DeviceFilter;
import ru.kislyakova.anastasia.shop.service.DeviceService;
import ru.kislyakova.anastasia.shop.service.preprocessing.ColorDevicePreprocessor;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.utils.ModelValidator;
import ru.kislyakova.anastasia.shop.utils.ParsingUtil;
import ru.kislyakova.anastasia.shop.utils.QueryValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DeviceWebHandler {
    private DeviceService deviceService;
    private ColorService colorService;

    @Inject
    public DeviceWebHandler(DeviceService deviceService, ColorService colorService) {
        this.deviceService = deviceService;
        this.colorService = colorService;
    }

    public List<Device> getDevices(String deviceTypeStr,
                                   String colorName,
                                   Integer colorRGB,
                                   BigDecimal price,
                                   BigDecimal priceFrom,
                                   BigDecimal priceTo,
                                   String dateStr,
                                   String dateFromStr,
                                   String dateToStr,
                                   String model,
                                   String manufacturer,
                                   String sortBy,
                                   int count,
                                   int pageNumber) {

        LocalDate dateFrom = ParsingUtil.getLocalDate(dateFromStr);
        LocalDate dateTo = ParsingUtil.getLocalDate(dateToStr);
        LocalDate date = ParsingUtil.getLocalDate(dateStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        DeviceFilter filter = new DeviceFilter()
                .withDeviceType(DeviceType.forValue(deviceTypeStr))
                .withColorName(colorName)
                .withColorRGB(colorRGB)
                .withPrice(price)
                .withPriceFrom(priceFrom)
                .withPriceTo(priceTo)
                .withDate(date)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withModel(model)
                .withManufacturer(manufacturer)
                .withSortConditionals(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber - 1);
        ModelValidator.validateEntity(filter);
        return deviceService.getDevices(filter);
    }

    public Device createDevice(Device device) {
        QueryValidator.checkEmptyRequest(device);
        ModelValidator.validateEntity(device);
        ColorDevicePreprocessor colorDevicePreprocessor = new ColorDevicePreprocessor(colorService);
        device = colorDevicePreprocessor.process(device);
        return deviceService.saveDevice(device);
    }

    public Device getDevice(long id) {
        Device device = deviceService.getDeviceById(id);
        QueryValidator.checkIfNotFound(device, String.format("Device with id %s doesn't exist", id));
        return device;
    }
}
