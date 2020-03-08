package ru.kislyakova.anastasia.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Length;
import ru.kislyakova.anastasia.shop.model.date.DateDeserializer;
import ru.kislyakova.anastasia.shop.model.date.DateSerializer;
import ru.kislyakova.anastasia.shop.storage.Unique;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortableFieldProvider;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Device implements Unique {
    private static final String ID_FIELD = "id";
    private static final String DEVICE_TYPE_FIELD = "deviceType";
    private static final String PRICE_FIELD = "price";
    private static final String MODEL_FIELD = "modelName";
    private static final String DATE_FIELD = "manufactureDate";
    private static final String MANUFACTURER_FIELD = "manufacturer";
    private static final String COLOR_NAME_FIELD = "colorName";
    private static final String COLOR_RGB_FIELD = "colorRGB";

    public static final SortableFieldProvider<Device> FIELD_PROVIDER = new DeviceSortableFieldProvider();

    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(DEVICE_TYPE_FIELD)
    @NotNull(message = "Received wrong device type")
    private DeviceType deviceType;

    @JsonProperty(PRICE_FIELD)
    @NotNull(message = "Price may not be null")
    @Digits(integer=6, fraction=2)
    private final BigDecimal price;

    @JsonProperty(MODEL_FIELD)
    @NotNull(message = "Model may not be null")
    @Length(min = 1, max = 100,  message = "Invalid model: length must be between 1 and 100")
    private final String model;

    @JsonIgnore
    private Color color;

    @JsonProperty(COLOR_NAME_FIELD)
    @NotNull(message = "Color name may not be null")
    private String colorName;

    @JsonProperty(COLOR_RGB_FIELD)
    @NotNull(message = "Color rgb may not be null")
    @Min(value = 0, message = "Color RGB min value = 0")
    @Max(value = 16777215, message = "Color RGB max value = 16777215")
    private int colorRGB;

    @JsonProperty(DATE_FIELD)
    @NotNull(message = "Date may not be null")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate date;

    @JsonProperty(MANUFACTURER_FIELD)
    @NotNull(message = "Manufacturer may not be null")
    @Length(min = 1, max = 100,  message = "Invalid manufacturer: length must be between 1 and 100")
    private final String manufacturer;

    @JsonCreator
    public Device(
            @JsonProperty(value = DEVICE_TYPE_FIELD, required = true) DeviceType deviceType,
            @JsonProperty(value = PRICE_FIELD, required = true) BigDecimal price,
            @JsonProperty(value = MODEL_FIELD, required = true) String model,
            @JsonProperty(value = COLOR_NAME_FIELD, required = true) String colorName,
            @JsonProperty(value = COLOR_RGB_FIELD, required = true) Integer colorRGB,
            @JsonProperty(value = DATE_FIELD, required = true) LocalDate date,
            @JsonProperty(value = MANUFACTURER_FIELD, required = true) String manufacturer) {
        this.deviceType = deviceType;
        this.price = price;
        this.model = model;
        this.colorName = colorName;
        this.colorRGB = colorRGB;
        this.color = new Color(colorName, colorRGB);
        this.manufacturer = manufacturer;
        this.date = date;

    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    public DeviceType getDeviceType() { return deviceType; }

    public BigDecimal getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public String getColorName() {
        return colorName;
    }

    public int getColorRGB() {
        return colorRGB;
    }

    public void setColor(Color color) {
        this.color = color;
        this.colorRGB = color.getRgb();
        this.colorName = color.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return id == device.id &&
                deviceType == device.deviceType &&
                price.equals(device.price) &&
                model.equals(device.model) &&
                color == device.color &&
                date.equals(device.date) &&
                manufacturer.equals(device.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceType, price, model, color, date, manufacturer);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", price=" + price +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", date=" + date +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }

    public static class DeviceSortableFieldProvider implements SortableFieldProvider<Device> {

        private DeviceSortableFieldProvider() {
        }

        @Override
        public Comparator<Device> getSortConditional(SortConditional sortConditional) {
            switch (sortConditional.getField()) {
                case DEVICE_TYPE_FIELD:
                    return Comparator.comparing(Device::getDeviceType);
                case PRICE_FIELD:
                    return Comparator.comparing(Device::getPrice);
                case MODEL_FIELD:
                    return Comparator.comparing(Device::getModel);
                case DATE_FIELD:
                    return Comparator.comparing(Device::getDate);
                case MANUFACTURER_FIELD:
                    return Comparator.comparing(Device::getManufacturer);
                case COLOR_NAME_FIELD:
                    return Comparator.comparing(Device::getColorName);
                case COLOR_RGB_FIELD:
                    return  Comparator.comparing(Device::getColorRGB);
                default:
                    throw new IllegalArgumentException("Unexpected sort param " + sortConditional.getField());
            }
        }
    }
}
