package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in the address book whose attributes contain the specified keyword.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds and lists all persons whose name, phone, email,"
            + "or address contain the given keyword.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " John";

    public static final String MESSAGE_SUCCESS = "Listed persons matching: %1$s";

    private final String keyword;

    /**
     * Constructs a SearchCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in person attributes.
     */
    public SearchCommand(String keyword) {
        requireNonNull(keyword);
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Executes the search command by filtering the list of persons whose attributes contain the keyword.
     *
     * @param model The model containing the person list.
     * @return A CommandResult indicating the search results.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Person> matchesKeyword = person ->
                person.getName().fullName.toLowerCase().contains(keyword)
                        || person.getPhone().value.contains(keyword)
                        || person.getEmail().value.toLowerCase().contains(keyword)
                        || person.getAddress().value.toLowerCase().contains(keyword)
                        || person.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "))
                        .toLowerCase().contains(keyword)
                        || (person.getCategory().isPresent() && person.getCategory().get().toString().toLowerCase()
                        .contains(keyword));

        model.updateFilteredPersonList(matchesKeyword);
        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }

    /**
     * Checks if this SearchCommand is equal to another object.
     *
     * @param other The object to compare.
     * @return True if the object is an instance of SearchCommand with the same keyword, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SearchCommand)) {
            return false;
        }
        SearchCommand otherCommand = (SearchCommand) other;
        return keyword.equals(otherCommand.keyword);
    }
}
