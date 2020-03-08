package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.Device;

import java.util.List;

public interface DeviceService {
    Device saveDevice(Device device);

    Device getDeviceById(long id);

    List<Device> getDevices(DeviceFilter filter);
}
