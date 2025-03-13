package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CATEGORY = "Client";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Category category;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        category = new Category(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     *
     * @param personToCopy A person whose data is to be copied.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        category = personToCopy.getCategory();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     *
     * @param name The new name.
     * @return This builder instance.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     *
     * @param address The new address.
     * @return This builder instance.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     *
     * @param phone The new phone number.
     * @return This builder instance.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     *
     * @param email The new email.
     * @return This builder instance.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Person} that we are building.
     *
     * @param category The new category.
     * @return This builder instance.
     */
    public PersonBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Builds and returns a {@code Person} with the current builder settings.
     *
     * @return A new Person object.
     */
    public Person build() {
        return new Person(name, phone, email, address, category);
    }
}
