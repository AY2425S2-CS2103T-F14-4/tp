package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods and constants for testing commands.
 */
public class CommandTestUtil {

    // --------------------------------------------------------------------------
    // Constants for test data
    // --------------------------------------------------------------------------
    public static final String VALID_NAME_AMY = "Amy Pauline";
    public static final String VALID_NAME_BOB = "Bob Brown";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 123, Amy Street";
    public static final String VALID_ADDRESS_BOB = "Block 456, Bob Street";
    public static final String VALID_CATEGORY_AMY = "Client";
    public static final String VALID_CATEGORY_BOB = "Investor";

    public static final String NAME_DESC_AMY = " n/" + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " n/" + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " p/" + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " p/" + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " e/" + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " e/" + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " a/" + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " a/" + VALID_ADDRESS_BOB;
    public static final String CATEGORY_DESC_AMY = " -c " + VALID_CATEGORY_AMY;
    public static final String CATEGORY_DESC_BOB = " -c " + VALID_CATEGORY_BOB;

    public static final String INVALID_NAME_DESC = " n/R@chel"; // '@' not allowed
    public static final String INVALID_PHONE_DESC = " p/+651234"; // '+' not allowed
    public static final String INVALID_EMAIL_DESC = " e/example.com"; // missing '@'
    public static final String INVALID_ADDRESS_DESC = " a/ "; // empty string not allowed for addresses
    public static final String INVALID_CATEGORY_DESC = " -c invalid"; // not in [Client, Investor, Partner, Other]

    public static final String PREAMBLE_WHITESPACE = "   ";
    public static final String PREAMBLE_NON_EMPTY = "nonempty";

    // --------------------------------------------------------------------------
    // Pre-built EditPersonDescriptor for tests
    // --------------------------------------------------------------------------
    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;

    static {
        EditPersonDescriptor descriptorAmy = new EditPersonDescriptor();
        descriptorAmy.setName(new Name(VALID_NAME_AMY));
        descriptorAmy.setPhone(new Phone(VALID_PHONE_AMY));
        descriptorAmy.setEmail(new Email(VALID_EMAIL_AMY));
        descriptorAmy.setAddress(new Address(VALID_ADDRESS_AMY));
        descriptorAmy.setCategory(new Category(VALID_CATEGORY_AMY));
        DESC_AMY = descriptorAmy;

        EditPersonDescriptor descriptorBob = new EditPersonDescriptor();
        descriptorBob.setName(new Name(VALID_NAME_BOB));
        descriptorBob.setPhone(new Phone(VALID_PHONE_BOB));
        descriptorBob.setEmail(new Email(VALID_EMAIL_BOB));
        descriptorBob.setAddress(new Address(VALID_ADDRESS_BOB));
        descriptorBob.setCategory(new Category(VALID_CATEGORY_BOB));
        DESC_BOB = descriptorBob;
    }

    // --------------------------------------------------------------------------
    // Assertion Helpers
    // --------------------------------------------------------------------------

    /**
     * Executes the given {@code command} and confirms that:
     * <ul>
     *   <li>The returned {@link CommandResult} matches {@code expectedCommandResult}</li>
     *   <li>The {@code actualModel} matches {@code expectedModel}</li>
     * </ul>
     *
     * @param command The command to execute.
     * @param actualModel The model to execute the command on.
     * @param expectedCommandResult The expected CommandResult.
     * @param expectedModel The expected state of the model.
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that:
     * <ul>
     *   <li>A {@code CommandException} is thrown.</li>
     *   <li>The CommandException message matches {@code expectedMessage}.</li>
     *   <li>The address book, filtered person list, and selected person in {@code actualModel} remain unchanged.</li>
     * </ul>
     *
     * @param command the command to execute.
     * @param actualModel the model to execute the command on.
     * @param expectedMessage the expected error message.
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // Make defensive copies of the model's state.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute(actualModel);
            throw new AssertionError("Expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }

        // Ensure the model is unchanged.
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }


    // --------------------------------------------------------------------------
    // Utility for commands that rely on showing only one person in the filtered list
    // --------------------------------------------------------------------------

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex}
     * in the {@code model}'s address book.
     * This is typically used in tests such as DeleteCommandTest and EditCommandTest to ensure the
     * model's filtered list only has the single target person visible.
     *
     * @param model The model whose filtered list is to be updated.
     * @param targetIndex The index of the person to show in the filtered list.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");

        // e.g., "NameContainsKeywordsPredicate" from your find command or a minimal predicate
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(
                Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
