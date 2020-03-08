package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.model.Bill;

import java.util.List;

public interface BillService {
    Bill saveBill(Bill bill);

    Bill getBillById(long id);

    List<Bill> getBills(BillFilter filter);

}
