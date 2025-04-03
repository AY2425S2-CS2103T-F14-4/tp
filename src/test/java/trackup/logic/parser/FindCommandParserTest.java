package trackup.logic.parser;

import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackup.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import trackup.logic.commands.FindCommand;
import trackup.model.person.Name;
import trackup.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidNameWithQuotes_throwsParseException() {
        String invalidInput = "\"John\""; // the quotes make the name invalid
        assertParseFailure(parser, invalidInput, Name.MESSAGE_CONSTRAINTS);
    }


}
