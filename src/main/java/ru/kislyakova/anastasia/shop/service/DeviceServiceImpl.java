package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.Device;
import ru.kislyakova.anastasia.shop.storage.Storage;
import ru.kislyakova.anastasia.shop.storage.filter.FilterConditional;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;
import ru.kislyakova.anastasia.shop.utils.Identifier;

import java.util.List;

public class DeviceServiceImpl implements DeviceService {
    private Storage<Device> storage;
    private Identifier identifier = new Identifier();

    public DeviceServiceImpl(Storage<Device> storage) {
        this.storage = storage;
    }

    @Override
    public Device saveDevice(Device device) {
        long id = identifier.nextId();
        device.setId(id);
        storage.save(device);
        return device;
    }

    @Override
    public Device getDeviceById(long id) {
        return storage.getById(id);
    }

    @Override
    public List<Device> getDevices(DeviceFilter filter) {
        StorageFilter<Device> storageFilter = new StorageFilter<>();

        if (filter.getManufacturer() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getManufacturer).eq(filter.getManufacturer()));
        }

        if (filter.getModel() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getModel).eq(filter.getModel()));
        }

        if (filter.getColorName() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getColorName).eq(filter.getColorName().toLowerCase()));
        }

        if (filter.getColorRGB() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getColorRGB).eq(filter.getColorRGB()));
        }

        if (filter.getDeviceType() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getDeviceType).eq(filter.getDeviceType()));
        }

        if (filter.getDate() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getDate).eq(filter.getDate()));
        }

        if (filter.getPrice() != null) {
            storageFilter.addCondition(FilterConditional.on(Device::getPrice).eq(filter.getPrice()));
        }

        storageFilter.addCondition(FilterConditional.on(Device::getPrice).inRange(filter.getPriceFrom(), filter.getPriceTo()));
        storageFilter.addCondition(FilterConditional.on(Device::getDate).inRange(filter.getDateFrom(), filter.getDateTo()));

        storageFilter.addAllSorting(Device.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());
        return storage.get(storageFilter);
    }
}
