package il.ac.hit.validation;

/**
 * A user with basic-tier access.
 */
public class BasicUser extends User {

    /**
     * Builds a new basic user.
     *
     * @param username the username to set
     * @param email the email address to set
     * @param password the password to set
     * @param age the age to set
     */
    public BasicUser(String username, String email, String password, int age) {
        super(username, email, password, age);
    }
}
