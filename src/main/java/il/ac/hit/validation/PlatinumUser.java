package il.ac.hit.validation;

/**
 * A user with platinum-tier access.
 */
public class PlatinumUser extends User {

    /**
     * Builds a new platinum user.
     *
     * @param username the username to set
     * @param email the email address to set
     * @param password the password to set
     * @param age the age to set
     */
    public PlatinumUser(String username, String email, String password, int age) {
        super(username, email, password, age);
    }
}
