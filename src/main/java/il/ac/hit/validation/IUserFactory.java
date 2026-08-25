package il.ac.hit.validation;

/**
 * Defines the contract for creating {@link User} objects of a
 * specific type. This mirrors the FactoryInterface from the
 * Factory Method design pattern.
 */
public interface IUserFactory {

    /**
     * Creates a new user of the requested type.
     *
     * @param type the type of user to create: "basic", "premium", or "platinum"
     * @param username the username to set
     * @param email the email address to set
     * @param password the password to set
     * @param age the age to set
     * @return a new {@link User} of the requested type
     */
    User createUser(String type, String username, String email, String password, int age);
}