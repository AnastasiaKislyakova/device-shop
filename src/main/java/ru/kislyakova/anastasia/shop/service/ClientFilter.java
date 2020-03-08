package ru.kislyakova.anastasia.shop.service;

import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientFilter {

    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private LocalDate birthDate;
    private List<SortConditional> sortConditionals = new ArrayList<>();
    @Max(value = 1000, message = "Too many page items, max value = 1000")
    @Min(value = 1, message = "Too few page items, min value = 1")
    private int count;
    @Min(value = 0, message = "Page min value = 1")
    @Max(value = 1000000, message = "Page max value = 1000000")
    private int pageNumber;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getBirthDateFrom() {
        return birthDateFrom;
    }

    public LocalDate getBirthDateTo() {
        return birthDateTo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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

    public ClientFilter withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ClientFilter withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ClientFilter withPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public ClientFilter withBirthDateFrom(LocalDate birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
        return this;
    }

    public ClientFilter withBirthDateTo(LocalDate birthDateTo) {
        this.birthDateTo = birthDateTo;
        return this;
    }

    public ClientFilter withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public ClientFilter withSortParams(List<SortConditional> sortConditionals) {
        this.sortConditionals = sortConditionals;
        return this;
    }

    public ClientFilter withCount(int count) {
        this.count = count;
        return this;
    }

    public ClientFilter withPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }
}
