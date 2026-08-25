package il.ac.hit.validation;

/**
 * Builds {@link User} objects of a specific type. This is our
 * Factory Method implementation.
 */
public class UserFactory implements IUserFactory {

    /**
     * Creates a new user of the requested type.
     *
     * @param type the type of user to create: "basic", "premium", or "platinum"
     * @param username the username to set
     * @param email the email address to set
     * @param password the password to set
     * @param age the age to set
     * @return a new {@link User} of the requested type
     * @throws IllegalArgumentException if the type is null or unknown
     */
    @Override
    public User createUser(String type, String username, String email, String password, int age) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        if (type.equals("basic")) {
            return new BasicUser(username, email, password, age);
        }

        if (type.equals("premium")) {
            return new PremiumUser(username, email, password, age);
        }

        if (type.equals("platinum")) {
            return new PlatinumUser(username, email, password, age);
        }

        throw new IllegalArgumentException("Unknown user type: " + type);
    }
}