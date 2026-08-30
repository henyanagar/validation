package il.ac.hit.validation;

/**
 * Builds {@link User} objects of a specific type. This is our
 * Factory Method implementation.
 */
public final class UserFactory {

    private UserFactory() {
        // utility class - not meant to be instantiated
    }

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
    public static User createUser(String type, String username, String email,
                                   String password, int age) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        return switch (type) {
            case "basic" -> new BasicUser(username, email, password, age);
            case "premium" -> new PremiumUser(username, email, password, age);
            case "platinum" -> new PlatinumUser(username, email, password, age);
            default -> throw new IllegalArgumentException("Unknown user type: " + type);
        };
    }
}
