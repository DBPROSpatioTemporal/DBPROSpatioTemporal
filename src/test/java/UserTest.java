import User.User;
import org.junit.Test;

public class UserTest {
    private User user1 = new User();
    private User user2 = new User(30, true, true, true, true);

    @Test
    public void User() {
        System.out.println(user1.hasLuggage());

    }
}
