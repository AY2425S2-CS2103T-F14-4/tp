package seedu.address.logic;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT =
            "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "The contact index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     *
     * @param duplicatePrefixes The prefixes that are duplicated.
     * @return The error message.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;
        String duplicateFields = Stream.of(duplicatePrefixes)
                .map(Prefix::toString)
                .collect(Collectors.joining(" "));
        return MESSAGE_DUPLICATE_FIELDS + duplicateFields;
    }

    /**
     * Formats the {@code Person} for display to the user.
     *
     * @param person The person to format.
     * @return A string representation of the person.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Category: ")
                .append(person.getCategory());
        return builder.toString();
    }
}
