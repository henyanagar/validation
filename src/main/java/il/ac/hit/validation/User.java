package il.ac.hit.validation;

/**
 * Represents a user of the system, holding the basic identifying
 * information: username, email, password, and age.
 *
 * <p>This class does not perform any validation on its own. All
 * validation logic lives in {@link UserValidation}, so that a user
 * with any combination of field values can be constructed and then
 * checked against the desired rules.
 */
public class User {

    private String username;
    private String email;
    private String password;
    private int age;

    /**
     * Creates a new user with the given field values.
     *
     * @param username the username to set
     * @param email the email address to set
     * @param password the password to set
     * @param age the age to set
     */
    public User(String username, String email, String password, int age) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setAge(age);
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return a string representation of this user, including the
     * username, email, and age (the password is intentionally omitted)
     */
    @Override
    public String toString() {
        return "User{username='" + username +
                "', email='" + email +
                "', age=" + age + "}";
    }
}