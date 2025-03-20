package trackup.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackup.logic.commands.CommandTestUtil.showPersonAtIndex;
import static trackup.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static trackup.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trackup.model.Model;
import trackup.model.ModelManager;
import trackup.model.UserPrefs;
import trackup.model.category.Category;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(Optional.empty()), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(Optional.empty()), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withCategory_filtersList() {
        Category clientCategory = new Category("Client");
        model.updateFilteredPersonList(person -> person.hasCategory(clientCategory));
        expectedModel.updateFilteredPersonList(person -> person.hasCategory(clientCategory));
        assertCommandSuccess(new ListCommand(Optional.of(clientCategory)), model,
                String.format(ListCommand.MESSAGE_SUCCESS_FILTERED, clientCategory), expectedModel);
    }

    @Test
    public void equals() {
        Category clientCategory = new Category("Client");
        Category investorCategory = new Category("Investor");

        ListCommand listAllCommand = new ListCommand(Optional.empty());
        ListCommand listClientCommand = new ListCommand(Optional.of(clientCategory));
        ListCommand listSupplierCommand = new ListCommand(Optional.of(investorCategory));

        // same object -> returns true
        assertTrue(listAllCommand.equals(listAllCommand));
        assertTrue(listClientCommand.equals(listClientCommand));

        // same values -> returns true
        ListCommand listClientCommandCopy = new ListCommand(Optional.of(clientCategory));
        assertTrue(listClientCommand.equals(listClientCommandCopy));

        // different types -> returns false
        assertFalse(listClientCommand.equals(1));

        // null -> returns false
        assertFalse(listClientCommand.equals(null));

        // different category -> returns false
        assertFalse(listClientCommand.equals(listSupplierCommand));

        // empty vs non-empty category -> returns false
        assertFalse(listAllCommand.equals(listClientCommand));
    }
}
