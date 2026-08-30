package il.ac.hit.validation;

import java.util.Comparator;

/**
 * Simple checks for the User model, the factory, and the sort utility.
 */
public class UserModelTest {

    private static void check(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            System.out.println("PASS - " + testName);
        } else {
            System.out.println(
                    "FAIL - " + testName +
                            " | expected: " + expected +
                            ", actual: " + actual
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("--- USER TESTS ---");

        User user = new User("ofek123", "ofek@gmail.com", "pass1234", 25);

        check("User - username set correctly", true, user.getUsername().equals("ofek123"));
        check("User - email set correctly", true, user.getEmail().equals("ofek@gmail.com"));
        check("User - age set correctly", true, user.getAge() == 25);

        user.setUsername("newName");
        check("User - setUsername works", true, user.getUsername().equals("newName"));


        System.out.println("\n--- USER FACTORY TESTS ---");

        User basic = UserFactory.createUser("basic", "u1", "u1@gmail.com", "pass1234", 20);
        User premium = UserFactory.createUser("premium", "u2", "u2@gmail.com", "pass1234", 22);
        User platinum = UserFactory.createUser("platinum", "u3", "u3@gmail.com", "pass1234", 24);

        check("Factory - basic type", true, basic instanceof BasicUser);
        check("Factory - premium type", true, premium instanceof PremiumUser);
        check("Factory - platinum type", true, platinum instanceof PlatinumUser);

        boolean unknownTypeThrew = false;
        try {
            UserFactory.createUser("gold", "u4", "u4@gmail.com", "pass1234", 26);
        } catch (IllegalArgumentException e) {
            unknownTypeThrew = true;
        }
        check("Factory - unknown type throws exception", true, unknownTypeThrew);


        System.out.println("\n--- USER UTILS TESTS ---");

        User[] users = {
                new User("charlie", "charlie@gmail.com", "pass1234", 30),
                new User("alice", "alice@gmail.com", "pass1234", 25),
                new User("bob", "bob@gmail.com", "pass1234", 28)
        };

        UserUtils.sort(users, Comparator.comparing(User::getUsername));

        check("UserUtils - sort by username, first is alice", true,
                users[0].getUsername().equals("alice"));
        check("UserUtils - sort by username, last is charlie", true,
                users[2].getUsername().equals("charlie"));

        UserUtils.sort(users, Comparator.comparingInt(User::getAge));

        check("UserUtils - sort by age, first is youngest", true, users[0].getAge() == 25);
        check("UserUtils - sort by age, last is oldest", true, users[2].getAge() == 30);

        boolean nullArrayThrew = false;
        try {
            UserUtils.sort(null, Comparator.comparing(User::getUsername));
        } catch (IllegalArgumentException e) {
            nullArrayThrew = true;
        }
        check("UserUtils - null array throws exception", true, nullArrayThrew);
    }
}
