package ru.kislyakova.anastasia.shop;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.kislyakova.anastasia.shop.controller.rest.handlers.DeviceWebHandler;
import ru.kislyakova.anastasia.shop.controller.rest.handlers.BillWebHandler;
import ru.kislyakova.anastasia.shop.controller.rest.handlers.ClientWebHandler;
import ru.kislyakova.anastasia.shop.controller.rest.handlers.ColorWebHandler;
import ru.kislyakova.anastasia.shop.service.*;
import ru.kislyakova.anastasia.shop.storage.RuntimeStorage;
import ru.kislyakova.anastasia.shop.storage.Storage;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    public ShopApplication() {
        packages("ru.kislyakova.anastasia.shop;com.fasterxml.jackson.jaxrs");

        Storage storageColor = new RuntimeStorage();
        ColorService colorService = colorService(storageColor);
        registerInstances(colorService);
        Storage storageDevice = new RuntimeStorage();
        DeviceService deviceService = deviceService(storageDevice);
        Storage storageClient = new RuntimeStorage();
        ClientService clientService = clientService(storageClient);
        Storage storageBill = new RuntimeStorage();
        BillService billService = billService(storageBill);

        register(new AbstractBinder() {
            @Override
            protected void configure() {

                bind(storageDevice).to(Storage.class);
                bind(storageClient).to(Storage.class);
                bind(storageBill).to(Storage.class);

                bind(deviceService(storageDevice)).to(DeviceService.class);
                bind(clientService(storageClient)).to(ClientService.class);
                bind(billService(storageBill)).to(BillService.class);

                bind(new ClientWebHandler(clientService)).to(ClientWebHandler.class);
                bind(new DeviceWebHandler(deviceService, colorService)).to(DeviceWebHandler.class);
                bind(new BillWebHandler(billService, clientService, deviceService)).to(BillWebHandler.class);
                bind(new ColorWebHandler(colorService)).to(ColorWebHandler.class);
            }
        });

    }

    private DeviceService deviceService(Storage storage) {
        return new DeviceServiceImpl(storage);
    }

    private ClientService clientService(Storage storage) { return new ClientServiceImpl(storage); }

    private BillService billService(Storage storage) { return new BillServiceImpl(storage); }

    private ColorService colorService(Storage storage) { return new ColorServiceImpl(storage); }

}