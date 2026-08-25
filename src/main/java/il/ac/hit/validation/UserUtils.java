package il.ac.hit.validation;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility operations for arrays of {@link User} objects. The sort
 * method here is our Template Method implementation, since the
 * comparator supplies the actual comparison logic.
 */
public class UserUtils {

    /**
     * Sorts an array of users in place, using the given comparator
     * to decide the order between two users.
     *
     * @param users the array to sort
     * @param comparator the comparator that defines the order
     * @throws IllegalArgumentException if users or comparator is null
     */
    public static void sort(User[] users, Comparator<User> comparator) {
        if (users == null) {
            throw new IllegalArgumentException("users cannot be null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("comparator cannot be null");
        }

        Arrays.sort(users, comparator);
    }
}
