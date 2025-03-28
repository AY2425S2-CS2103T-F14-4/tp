package trackup.model.person;

import java.util.Comparator;
import java.util.function.Function;

/**
 * A utility class that provides {@link Comparator} implementations for {@link Person} objects
 * in ascending order. Each comparator compares persons based on different attributes.
 */
public class Comparators {

    public static final Comparator<Person> NAME_COMPARATOR = comparing(Person::getName);
    public static final Comparator<Person> PHONE_COMPARATOR = comparing(Person::getPhone);
    public static final Comparator<Person> ADDRESS_COMPARATOR = comparing(Person::getAddress);
    public static final Comparator<Person> EMAIL_COMPARATOR = comparing(Person::getEmail);
    public static final Comparator<Person> TAG_COMPARATOR = comparing(p -> p.getTags().toString());
    public static final Comparator<Person> CATEGORY_COMPARATOR = comparing(p -> p.getCategory().toString());

    private static <T extends Comparable<T>> Comparator<Person> comparing(Function<Person, T> keyExtractor) {
        return Comparator.comparing(keyExtractor);
    }

}
