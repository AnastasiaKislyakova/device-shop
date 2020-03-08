package ru.kislyakova.anastasia.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kislyakova.anastasia.shop.model.Device;
import ru.kislyakova.anastasia.shop.model.DeviceType;
import ru.kislyakova.anastasia.shop.storage.filter.FilterConditional;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;
import ru.kislyakova.anastasia.shop.utils.MockitoUtils;
import ru.kislyakova.anastasia.shop.utils.ModelUtils;
import ru.kislyakova.anastasia.shop.utils.ParsingUtil;
import ru.kislyakova.anastasia.shop.storage.Storage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;

class DeviceServiceImplTest {

    private Storage<Device> storage = (Storage<Device>) Mockito.mock(Storage.class);
    private DeviceService deviceService = new DeviceServiceImpl(storage);

    @BeforeEach
    void setUp() {
        Mockito.reset(storage);
    }

    @Test
    void should_correctly_save_device_and_set_id() {
        Device device = ModelUtils.device();

        Device saved = deviceService.saveDevice(device);

        assertNotEquals(0, saved.getId());

        Mockito.verify(storage).save(eq(saved));
    }

    @Test
    void should_return_device_by_id() {
        Device device = ModelUtils.device();

        Device saved = deviceService.saveDevice(device);
        Mockito.when(storage.getById(anyLong())).thenReturn(saved);

        Device actual = deviceService.getDeviceById(saved.getId());

        assertEquals(saved, actual);

        Mockito.verify(storage).save(eq(saved));
        Mockito.verify(storage).getById(eq(saved.getId()));
    }

    @Test
    void should_not_return_device_by_id() {
        Device actual = deviceService.getDeviceById(1);

        assertNull(actual);

        Mockito.verify(storage).getById(1);
    }

    @Test
    void should_correctly_convert_filter() {
        DeviceFilter filter = new DeviceFilter()
                .withDeviceType(DeviceType.LAPTOP)
                .withColorName("A")
                .withColorRGB(7)
                .withPrice(BigDecimal.valueOf(12.12))
                .withPriceFrom(BigDecimal.valueOf(12.12))
                .withPriceTo(BigDecimal.valueOf(12.12))
                .withDate(LocalDate.now())
                .withDateFrom(LocalDate.now())
                .withDateTo(LocalDate.now())
                .withModel("A")
                .withManufacturer("A")
                .withSortConditionals(ParsingUtil.getSortParams("deviceType,colorName"))
                .withCount(10)
                .withPageNumber(0);

        List<Device> devices = deviceService.getDevices(filter);

        StorageFilter<Device> storageFilter = new StorageFilter<>();
        storageFilter.addCondition(FilterConditional.on(Device::getManufacturer).eq(filter.getManufacturer()));
        storageFilter.addCondition(FilterConditional.on(Device::getModel).eq(filter.getModel()));
        storageFilter.addCondition(FilterConditional.on(Device::getColorName).eq(filter.getColorName().toLowerCase()));
        storageFilter.addCondition(FilterConditional.on(Device::getColorRGB).eq(filter.getColorRGB()));
        storageFilter.addCondition(FilterConditional.on(Device::getDeviceType).eq(filter.getDeviceType()));
        storageFilter.addCondition(FilterConditional.on(Device::getDate).eq(filter.getDate()));
        storageFilter.addCondition(FilterConditional.on(Device::getPrice).eq(filter.getPrice()));
        storageFilter.addCondition(FilterConditional.on(Device::getPrice).inRange(filter.getPriceFrom(), filter.getPriceTo()));
        storageFilter.addCondition(FilterConditional.on(Device::getDate).inRange(filter.getDateFrom(), filter.getDateTo()));
        storageFilter.addAllSorting(Device.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());

        Mockito.verify(storage).get(eq(storageFilter));
        Mockito.verify(storage).get(MockitoUtils.argThat((it) -> it.getSortings().size() == 2));
    }

}