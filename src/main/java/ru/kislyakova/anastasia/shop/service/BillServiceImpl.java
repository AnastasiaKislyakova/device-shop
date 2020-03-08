package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.Bill;
import ru.kislyakova.anastasia.shop.storage.Storage;
import ru.kislyakova.anastasia.shop.storage.filter.FilterConditional;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;
import ru.kislyakova.anastasia.shop.utils.Identifier;

import java.util.List;

public class BillServiceImpl implements BillService {
    private Storage<Bill> storage;
    private Identifier identifier = new Identifier();

    public BillServiceImpl(Storage<Bill> storage) {
        this.storage = storage;
    }

    @Override
    public Bill saveBill(Bill bill) {
        long id = identifier.nextId();
        bill.setId(id);
        storage.save(bill);
        return bill;
    }

    @Override
    public Bill getBillById(long id) {
        return storage.getById(id);
    }

    @Override
    public List<Bill> getBills(BillFilter filter) {
        StorageFilter<Bill> storageFilter = new StorageFilter<>();

        if (filter.getClientId() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getClientId).eq(filter.getClientId()));
        }

        if (filter.getDateTime() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getDateTime).eq(filter.getDateTime()));
        }

        if (filter.getTotalPrice() != null){
            storageFilter.addCondition(FilterConditional.on(Bill::getTotalPrice).eq(filter.getTotalPrice()));
        }

        if (!filter.getDeviceIds().isEmpty()){
            for (long deviceId : filter.getDeviceIds()){
                storageFilter.addCondition(FilterConditional.on((Bill it) -> it.containsDevice(deviceId)).eq(true));
            }
        }

        storageFilter.addCondition(FilterConditional.on(Bill::getDateTime).inRange(filter.getDateTimeFrom(), filter.getDateTimeTo()));
        storageFilter.addCondition(FilterConditional.on(Bill::getTotalPrice).inRange(filter.getTotalPriceFrom(), filter.getTotalPriceTo()));

        storageFilter.addAllSorting(Bill.FIELD_PROVIDER, filter.getSortConditionals());
        storageFilter.setCount(filter.getCount());
        storageFilter.setPageNumber(filter.getPageNumber());
        return storage.get(storageFilter);
    }
}
