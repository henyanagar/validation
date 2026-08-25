package il.ac.hit.validation;

/**
 * A user with premium-tier access.
 */
public class PremiumUser extends User {

    /**
     * Builds a new premium user.
     *
     * @param username the username to set
     * @param email the email address to set
     * @param password the password to set
     * @param age the age to set
     */
    public PremiumUser(String username, String email, String password, int age) {
        super(username, email, password, age);
    }
}
