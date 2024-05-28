package user;

import com.github.javafaker.Faker;





public class UserParams {
    public static Faker faker = new Faker();
    public static NewUser randomUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 10);
        String name = faker.name().fullName();
        return new NewUser(email, password, name);
    }

    public static NewUser createdUser() {
        String email = "AlexSandro024@ya.ru";
        String password = "Zxc123456!";
        String name = "Test";
        return new NewUser(email, password, name);
    }

    public static NewUser wrongPasswordUser() {
        String email = "AlexSandro024@ya.ru";
        String password = "123456";
        String name = "Test";
        return new NewUser(email, password, name);
    }

}
