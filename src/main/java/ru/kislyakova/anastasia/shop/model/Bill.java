package ru.kislyakova.anastasia.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import ru.kislyakova.anastasia.shop.model.date.DateTimeDeserializer;
import ru.kislyakova.anastasia.shop.model.date.DateTimeSerializer;
import ru.kislyakova.anastasia.shop.storage.Unique;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortableFieldProvider;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Bill implements Unique {
    private static final String ID_FIELD = "id";
    private static final String CLIENT_ID_FIELD = "customerId";
    private static final String ITEMS_LIST_FIELD = "items";
    private static final String DATE_TIME_FIELD = "purchaseDateTime";
    private static final String TOTAL_PRICE_FIELD = "totalPrice";

    public static final SortableFieldProvider<Bill> FIELD_PROVIDER = new BillSortableFieldProvider();

    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(CLIENT_ID_FIELD)
    @NotNull(message = "Client's id may not be null")
    private final long clientId;

    @JsonProperty(ITEMS_LIST_FIELD)
    @NotNull(message = "Bill items may not be null")
    @NotEmpty(message = "List of bill items may not be empty")
    private final List<BillItem> billItems;

    @JsonProperty(DATE_TIME_FIELD)
    @NotNull(message = "Purchase date time may not be null")
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private final LocalDateTime dateTime;

    @JsonProperty(TOTAL_PRICE_FIELD)
    private BigDecimal totalPrice;

    @JsonCreator
    public Bill(
            @JsonProperty(value = CLIENT_ID_FIELD, required = true) long clientId,
            @JsonProperty(value = ITEMS_LIST_FIELD, required = true) List<BillItem> billItems,
            @JsonProperty(value = DATE_TIME_FIELD, required = false) LocalDateTime dateTime) {
        this.clientId = clientId;
        this.billItems = billItems;
        this.totalPrice = this.billItems.stream().map(BillItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return id == bill.id &&
                clientId == bill.clientId &&
                billItems.equals(bill.billItems) &&
                dateTime.equals(bill.dateTime) &&
                totalPrice.equals(bill.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, billItems, dateTime, totalPrice);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", billItems=" + billItems +
                ", dateTime=" + dateTime +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public boolean containsDevice(long id) {
        return billItems.stream().anyMatch(billItem -> billItem.getDeviceId() == id);
    }

    public static class BillSortableFieldProvider implements SortableFieldProvider<Bill> {

        private BillSortableFieldProvider() {
        }

        @Override
        public Comparator<Bill> getSortConditional(SortConditional sortConditional) {
            switch (sortConditional.getField()) {
                case CLIENT_ID_FIELD:
                    return Comparator.comparing(Bill::getClientId);
                case DATE_TIME_FIELD:
                    return Comparator.comparing(Bill::getDateTime);
                case TOTAL_PRICE_FIELD:
                    return Comparator.comparing(Bill::getTotalPrice);
                default:
                    throw new IllegalArgumentException("Unexpected sort param " + sortConditional.getField());
            }
        }
    }
}
