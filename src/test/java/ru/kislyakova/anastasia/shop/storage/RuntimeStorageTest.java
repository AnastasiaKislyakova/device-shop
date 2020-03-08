package ru.kislyakova.anastasia.shop.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kislyakova.anastasia.shop.storage.filter.FilterConditional;
import ru.kislyakova.anastasia.shop.storage.filter.StorageFilter;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortConditional;
import ru.kislyakova.anastasia.shop.storage.filter.sorting.SortableFieldProvider;
import ru.kislyakova.anastasia.shop.utils.ParsingUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuntimeStorageTest {

    private Storage<Person> storage;

    @BeforeEach
    void setUp() {
        storage = new RuntimeStorage<>();
    }

    @Test
    void should_return_person_by_id() {
        Person person = new Person(1, "ivan", "ivanov", 20);

        storage.save(person);

        Person actual = storage.getById(1);
        assertEquals(person, actual);
    }

    @Test
    void should_return_persons_by_filter_count() {
        StorageFilter<Person> filter = new StorageFilter<>();
        int count = 10;
        filter.setCount(count);
        filter.setPageNumber(0);
        for (int i = 0; i < count + 1; i++){
            storage.save(new Person(i + 1, "iv", "iv", 12));
        }

        List<Person> persons = storage.get(filter);

        assertEquals(count, persons.size());
    }

    @Test
    void should_return_persons_by_filter_page_number(){
        StorageFilter<Person> filter = new StorageFilter<>();
        int count = 5;
        filter.setCount(count);
        filter.setPageNumber(2);
        for (int i = 0; i < count*2 + 1; i++){
            storage.save(new Person(i + 1, "iv", "iv", 12));
        }

        List<Person> persons = storage.get(filter);

        assertEquals(1, persons.size());

        filter.setPageNumber(1);

        persons = storage.get(filter);

        assertEquals(count, persons.size());
    }

    @Test
    void should_correctly_sort_persons(){
        StorageFilter<Person> filter = new StorageFilter<>();
        int count = 10;
        filter.setCount(count);
        filter.setPageNumber(0);
        filter.addAllSorting(Person.FIELD_PROVIDER, ParsingUtil.getSortParams("secondName,firstName,-age"));
        storage.save(new Person(1, "Ivan", "Ivanov", 12));
        storage.save(new Person(2, "Ivan", "Ivanov", 13));
        storage.save(new Person(3, "Igor", "Ivanov", 12));
        storage.save(new Person(4, "Ivan", "Petrov", 11));
        storage.save(new Person(5, "Petr", "Ivanov", 12));
        storage.save(new Person(6, "Ivan", "Petrov", 12));

        List<Person> persons = storage.get(filter);

        assertEquals(3, persons.get(0).getId());
        assertEquals(2, persons.get(1).getId());
        assertEquals(1, persons.get(2).getId());
        assertEquals(5, persons.get(3).getId());
        assertEquals(6, persons.get(4).getId());
        assertEquals(4, persons.get(5).getId());
    }

    @Test
    void should_correctly_filter_persons_by_first_name_and_age(){
        StorageFilter<Person> filter = new StorageFilter<>();
        int count = 10;
        filter.setCount(count);
        filter.setPageNumber(0);
        filter.addCondition(FilterConditional.on(Person::getFirstName).eq("Ivan"));
        filter.addCondition(FilterConditional.on(Person::getAge).inRange(9, 12));
        storage.save(new Person(1, "Ivan", "Ivanov", 12));
        storage.save(new Person(2, "Ivan", "Ivanov", 13));
        storage.save(new Person(3, "Igor", "Ivanov", 12));
        storage.save(new Person(4, "Ivan", "Petrov", 11));
        storage.save(new Person(5, "Ivan", "Petrov", 10));
        storage.save(new Person(6, "Ivan", "Petrov", 1));

        List<Person> persons = storage.get(filter);

        assertEquals(3, persons.size());
        assertEquals(1, persons.get(0).getId());
        assertEquals(4, persons.get(1).getId());
        assertEquals(5, persons.get(2).getId());
    }

    @Test
    void should_correctly_filter_persons_by_second_name_and_age(){
        StorageFilter<Person> filter = new StorageFilter<>();
        int count = 10;
        filter.setCount(count);
        filter.setPageNumber(0);
        filter.addCondition(FilterConditional.on(Person::getSecondName).eq("Ivanov"));
        filter.addCondition(FilterConditional.on(Person::getAge).inRange(9, 12));
        storage.save(new Person(1, "Ivan", "Ivanov", 12));
        storage.save(new Person(2, "Ivan", "Ivanov", 13));
        storage.save(new Person(3, "Igor", "Ivanov", 12));
        storage.save(new Person(4, "Ivan", "Petrov", 11));
        storage.save(new Person(5, "Ivan", "Petrov", 10));
        storage.save(new Person(6, "Ivan", "Petrov", 1));

        List<Person> persons = storage.get(filter);

        assertEquals(2, persons.size());
        assertEquals(1, persons.get(0).getId());
        assertEquals(3, persons.get(1).getId());
    }

    @Test
    void should_correctly_filter_persons_by_all_fields(){
        StorageFilter<Person> filter = new StorageFilter<>();
        int count = 10;
        filter.setCount(count);
        filter.setPageNumber(0);
        filter.addCondition(FilterConditional.on(Person::getSecondName).eq("Ivanov"));
        filter.addCondition(FilterConditional.on(Person::getFirstName).eq("Ivan"));
        filter.addCondition(FilterConditional.on(Person::getAge).inRange(9, 12));
        storage.save(new Person(1, "Ivan", "Ivanov", 14));
        storage.save(new Person(2, "Ivan", "Ivanov", 11));
        storage.save(new Person(3, "Igor", "Ivanov", 12));
        storage.save(new Person(4, "Ivan", "Petrov", 10));
        storage.save(new Person(5, "Ivan", "Ivanov", 1));

        List<Person> persons = storage.get(filter);

        assertEquals(1, persons.size());
        assertEquals(2, persons.get(0).getId());
    }

    private static class Person implements Unique {
        private static final String FIRST_NAME_FIELD = "firstName";
        private static final String SECOND_NAME_FIELD = "secondName";
        private static final String AGE_FIELD = "age";

        public static final SortableFieldProvider<Person> FIELD_PROVIDER = new PersonSortableFieldProvider();

        private final long id;
        private final String firstName;
        private final String secondName;
        private final int age;


        public Person(long id, String firstName, String secondName, int age) {
            this.id = id;
            this.firstName = firstName;
            this.secondName = secondName;
            this.age = age;
        }

        @Override
        public long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public int getAge(){
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person that = (Person) o;
            return id == that.id &&
                    Objects.equals(firstName, that.firstName) &&
                    Objects.equals(secondName, that.secondName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, firstName, secondName);
        }

        public static class PersonSortableFieldProvider implements SortableFieldProvider<Person> {

            private PersonSortableFieldProvider() {
            }

            @Override
            public Comparator<Person> getSortConditional(SortConditional sortConditional) {
                switch (sortConditional.getField()) {
                    case FIRST_NAME_FIELD:
                        return Comparator.comparing(Person::getFirstName);
                    case SECOND_NAME_FIELD:
                        return Comparator.comparing(Person::getSecondName);
                    case AGE_FIELD:
                        return Comparator.comparing(Person::getAge);
                    default:
                        throw new IllegalArgumentException("Unexpected sort param " + sortConditional.getField());
                }
            }
        }
    }

}