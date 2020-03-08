package ru.kislyakova.anastasia.shop.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Length;
import ru.kislyakova.anastasia.shop.model.date.DateDeserializer;
import ru.kislyakova.anastasia.shop.model.date.DateSerializer;
import ru.kislyakova.anastasia.shop.storage.Unique;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortableFieldProvider;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Client implements Unique {
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String PATRONYMIC_FIELD = "middleName";
    private static final String BIRTH_DATE_FIELD = "birthdate";

    public static final SortableFieldProvider<Client> FIELD_PROVIDER = new ClientSortableFieldProvider();


    @JsonProperty(ID_FIELD)
    private long id;

    @JsonProperty(FIRST_NAME_FIELD)
    @NotNull(message = "First name may not be null")
    @Pattern(regexp = "[A-ZА-Яa-zа-я]*", message = "Invalid first name")
    @Length(min = 1, max = 100,  message = "Invalid first name: length must be between 1 and 100")
    private final String firstName;

    @JsonProperty(LAST_NAME_FIELD)
    @NotNull(message = "Last name may not be null")
    @Pattern(regexp = "[A-ZА-Яa-zа-я]*", message = "Invalid last name")
    @Length(min = 1, max = 100,  message = "Invalid last name: length must be between 1 and 100")
    private final String lastName;

    @JsonProperty(PATRONYMIC_FIELD)
    @NotNull(message = "Patronymic may not be null")
    @Pattern(regexp = "[A-ZА-Яa-zа-я]*", message = "Invalid patronymic")
    @Length(min = 1, max = 100, message = "Invalid patronymic: length must be between 1 and 100")
    private final String patronymic;

    @JsonProperty(BIRTH_DATE_FIELD)
    @NotNull(message = "Birth date may not be null")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate birthDate;

    @JsonCreator
    public Client(
            @JsonProperty(value = FIRST_NAME_FIELD, required = true) String firstName,
            @JsonProperty(value = LAST_NAME_FIELD, required = true) String lastName,
            @JsonProperty(value = PATRONYMIC_FIELD, required = true) String patronymic,
            @JsonProperty(value = BIRTH_DATE_FIELD, required = true) LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id &&
                firstName.equals(client.firstName) &&
                lastName.equals(client.lastName) &&
                patronymic.equals(client.patronymic) &&
                birthDate.equals(client.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic, birthDate);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public static class ClientSortableFieldProvider implements SortableFieldProvider<Client> {

        private ClientSortableFieldProvider() {
        }

        @Override
        public Comparator<Client> getSortConditional(SortConditional sortConditional) {
            switch (sortConditional.getField()) {
                case FIRST_NAME_FIELD:
                    return Comparator.comparing(Client::getFirstName);
                case LAST_NAME_FIELD:
                    return Comparator.comparing(Client::getLastName);
                case PATRONYMIC_FIELD:
                    return Comparator.comparing(Client::getPatronymic);
                case BIRTH_DATE_FIELD:
                    return Comparator.comparing(Client::getBirthDate);
                default:
                    throw new IllegalArgumentException("Unexpected sort param " + sortConditional.getField());
            }
        }
    }
}





