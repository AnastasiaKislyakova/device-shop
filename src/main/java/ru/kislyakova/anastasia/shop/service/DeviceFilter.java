package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.DeviceType;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeviceFilter {

    private DeviceType deviceType;
    private BigDecimal price;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private String model;
    private String colorName;
    private Integer colorRGB;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDate date;
    private String manufacturer;
    private List<SortConditional> sortConditionals = new ArrayList<>();
    @Max(value = 1000, message = "Too many page items, max value = 1000")
    @Min(value = 1, message = "Too few page items, min value = 1")
    private int count;
    @Min(value = 0, message = "Page min value = 1")
    @Max(value = 1000000, message = "Page max value = 1000000")
    private int pageNumber;

    public DeviceType getDeviceType() { return deviceType; }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getPriceFrom() {
        return priceFrom;
    }

    public BigDecimal getPriceTo() {
        return priceTo;
    }

    public String getModel() {
        return model;
    }

    public String getColorName() { return colorName; }

    public Integer getColorRGB() { return colorRGB; }

    public LocalDate getDate() { return date; }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() { return dateTo; }

    public String getManufacturer() {
        return manufacturer;
    }

    public List<SortConditional> getSortConditionals() { return sortConditionals; }

    public int getCount() { return count; }

    public int getPageNumber() { return pageNumber; }

    public DeviceFilter withDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public DeviceFilter withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public DeviceFilter withPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public DeviceFilter withPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
        return this;
    }

    public DeviceFilter withModel(String model) {
        this.model = model;
        return this;
    }

    public DeviceFilter withColorName(String colorName) {
        this.colorName = colorName;
        return this;
    }

    public DeviceFilter withColorRGB(Integer colorRGB) {
        this.colorRGB = colorRGB;
        return this;
    }

    public DeviceFilter withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public DeviceFilter withDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public DeviceFilter withDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public DeviceFilter withManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public DeviceFilter withSortConditionals(List<SortConditional> sortConditionals){
        this.sortConditionals = sortConditionals;
        return this;
    }

    public DeviceFilter withCount(int count){
        this.count = count;
        return this;
    }

    public DeviceFilter withPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        return this;
    }
}
