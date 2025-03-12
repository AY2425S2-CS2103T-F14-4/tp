package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new {@code AddCommand} object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and
     * returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_CATEGORY);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Phone phone = argMultimap.getValue(PREFIX_PHONE).isPresent()
                ? ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get())
                : new Phone("");

        Email email = argMultimap.getValue(PREFIX_EMAIL).isPresent()
                ? ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get())
                : new Email("");

        Address address = argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                ? ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get())
                : new Address("");

        Category category = argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                ? ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get())
                : new Category("Other");

        Person person = new Person(name, phone, email, address, category);
        return new AddCommand(person);
    }

    /**
     * Returns true if all the specified prefixes have non-empty values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The map of argument prefixes to their values.
     * @param prefixes The prefixes to check.
     * @return true if every prefix is present.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
