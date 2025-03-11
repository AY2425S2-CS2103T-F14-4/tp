package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Remark for a person in the address book.
 * A Remark is an optional additional note associated with a person.
 * It is immutable once created.
 */
public class Remark {
    public final String value;

    public Remark(String remark) {
        this.value = remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Remark
                && value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
