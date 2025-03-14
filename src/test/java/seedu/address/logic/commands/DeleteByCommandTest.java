package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class DeleteByCommandTest {

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void constructor_nullCriteria_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new DeleteByCommand(null, null, null, null, null));
    }

    @Test
    public void execute_personFound_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.of(ALICE.getName()), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByPhone_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.of(ALICE.getPhone()), Optional.empty(),
                Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByEmail_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.of(ALICE.getEmail()),
                Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByAddress_success() throws Exception {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(ALICE.getAddress()), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteByTag_success() throws Exception {
        Tag tag = ALICE.getTags().iterator().next();
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(ALICE));
        when(model.hasPerson(ALICE)).thenReturn(true);

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(tag));

        CommandResult commandResult = deleteCommand.execute(model);

        verify(model, times(1)).deletePerson(ALICE);
        assertEquals(String.format(DeleteByCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE)), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList());

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.of(new Name("Nonexistent Name")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_multiplePersonsFound_multipleMatchesMessage() throws CommandException {
        Person person1 = new PersonBuilder().withName("John Doe").build();
        Person person2 = new PersonBuilder().withName("John Doe").build();
        when(model.getFilteredPersonList()).thenReturn(FXCollections.observableArrayList(person1, person2));

        DeleteByCommand deleteCommand = new DeleteByCommand(
                Optional.of(new Name("John Doe")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        CommandResult commandResult = deleteCommand.execute(model);

        assertEquals(String.format(DeleteByCommand.MESSAGE_MULTIPLE_PEOPLE_TO_DELETE,
                deleteCommand.toString()), commandResult.getFeedbackToUser());
    }
}
