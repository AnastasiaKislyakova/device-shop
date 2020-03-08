package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillFilter {
    private Long clientId;
    private LocalDateTime dateTime;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceFrom;
    private BigDecimal totalPriceTo;
    private List<Long> deviceIds = new ArrayList<>();
    private List<SortConditional> sortConditionals = new ArrayList<>();
    @Max(value = 1000, message = "Too many page items, max value = 1000")
    @Min(value = 1, message = "Too few page items, min value = 1")
    private int count;
    @Min(value = 0, message = "Page min value = 1")
    @Max(value = 1000000, message = "Page max value = 1000000")
    private int pageNumber;

    public Long  getClientId() {
        return clientId;
    }

    public LocalDateTime getDateTime() { return dateTime; }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public BigDecimal getTotalPriceFrom() {
        return totalPriceFrom;
    }

    public BigDecimal getTotalPriceTo() {
        return totalPriceTo;
    }

    public List<Long> getDeviceIds() {
        return deviceIds;
    }

    public List<SortConditional> getSortConditionals() {
        return sortConditionals;
    }

    public int getCount() {
        return count;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public BillFilter withClientId(Long clientId){
        this.clientId = clientId;
        return this;
    }

    public BillFilter withDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
        return this;
    }

    public BillFilter withDateTimeFrom(LocalDateTime dateTimeFrom){
        this.dateTimeFrom = dateTimeFrom;
        return this;
    }

    public BillFilter withDateTimeTo(LocalDateTime dateTimeTo){
        this.dateTimeTo = dateTimeTo;
        return this;
    }

    public BillFilter withTotalPrice(BigDecimal totalPrice){
        this.totalPrice = totalPrice;
        return this;
    }

    public BillFilter withTotalPriceFrom(BigDecimal totalPriceFrom){
        this.totalPriceFrom = totalPriceFrom;
        return this;
    }

    public BillFilter withTotalPriceTo(BigDecimal totalPriceTo){
        this.totalPriceTo = totalPriceTo;
        return this;
    }

    public BillFilter withDeviceIds(List<Long> deviceIds){
        this.deviceIds = deviceIds;
        return this;
    }

    public BillFilter withSortParams(List<SortConditional> sortConditionals){
        this.sortConditionals = sortConditionals;
        return this;
    }

    public BillFilter withCount(int count){
        this.count = count;
        return this;
    }

    public BillFilter withPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        return this;
    }

}
