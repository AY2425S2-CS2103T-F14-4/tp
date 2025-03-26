package trackup.logic.commands;

import static java.util.Objects.requireNonNull;

import trackup.commons.core.Visibility;
import trackup.logic.Messages;
import trackup.logic.commands.exceptions.CommandException;
import trackup.model.Model;

public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String NAME_FIELD_STRING = "name";
    public static final String PHONE_FIELD_STRING = "phone";
    public static final String EMAIL_FIELD_STRING = "email";
    public static final String ADDRESS_FIELD_STRING = "address";
    public static final String TAG_FIELD_STRING = "tag";
    public static final String CATEGORY_FIELD_STRING = "category";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle the given field in the address book UI. "
            + "Parameters: "
            + "[FIELD PREFIX]\n"
            + "Example: " + COMMAND_WORD + " "
            + NAME_FIELD_STRING;

    public static final String MESSAGE_HIDE_SUCCESS = "Field hidden: %1$s";
    public static final String MESSAGE_UNHIDE_SUCCESS = "Field unhidden: %1$s";

    private final String fieldName;

    public ToggleCommand(String fieldName) {
        requireNonNull(fieldName);

        this.fieldName = fieldName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Visibility visibility = model.getGuiSettings().getVisibility();

        CommandResult result = switch (fieldName) {
        case NAME_FIELD_STRING -> {
            visibility.setShowName(!visibility.isShowName());
            yield new CommandResult(String.format(
                    visibility.isShowName() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case PHONE_FIELD_STRING -> {
            visibility.setShowPhone(!visibility.isShowPhone());
            yield new CommandResult(String.format(
                    visibility.isShowPhone() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case EMAIL_FIELD_STRING -> {
            visibility.setShowEmail(!visibility.isShowEmail());
            yield new CommandResult(String.format(
                    visibility.isShowEmail() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case ADDRESS_FIELD_STRING -> {
            visibility.setShowAddress(!visibility.isShowAddress());
            yield new CommandResult(String.format(
                    visibility.isShowAddress() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case TAG_FIELD_STRING -> {
            visibility.setShowTag(!visibility.isShowTag());
            yield new CommandResult(String.format(
                    visibility.isShowTag() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        case CATEGORY_FIELD_STRING -> {
            visibility.setShowCategory(!visibility.isShowCategory());
            yield new CommandResult(String.format(
                    visibility.isShowCategory() ? MESSAGE_UNHIDE_SUCCESS : MESSAGE_HIDE_SUCCESS, fieldName));
        }
        default -> throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        };


        // Render the UI component
        return result;
    }


}
