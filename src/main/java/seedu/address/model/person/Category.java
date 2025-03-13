package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a contact's category in the address book.
 * <p>
 * A {@code Category} guarantees that its value is valid as defined by {@link #isValidCategory(String)}.
 */
public class Category {
    public static final String MESSAGE_CONSTRAINTS =
            "Category should be one of: Client, Investor, Partner, Other";

    public final String value;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid category string.
     * @throws IllegalArgumentException if {@code category} is not a valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = category;
    }
    /**
     * Returns true if a given string is a valid category.
     *
     * @param test The category string to validate.
     * @return {@code true} if {@code test} is one of "Client", "Investor", "Partner", or "Other" (case-insensitive).
     */
    public static boolean isValidCategory(String test) {
        return test.equalsIgnoreCase("Client")
                || test.equalsIgnoreCase("Investor")
                || test.equalsIgnoreCase("Partner")
                || test.equalsIgnoreCase("Other");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Category && value.equalsIgnoreCase(((Category) other).value));
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
