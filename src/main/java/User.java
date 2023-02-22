import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String email;
    private String password;
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public static User getExistUser() {
        return new User("Marty", "Marty-McFly@back.com",
                "OCT.21.2015");
    }
    public static User createUser() {
        return new User("Marty", RandomStringUtils.randomAlphanumeric(10) + "@back.com",
                "OCT.21.2015");
    }
    public static User createUserWithEmptyPassword() {
        return new User("Marty", RandomStringUtils.randomAlphanumeric(10) + "@back.com",
                null);
    }
    public static User createUserWithEmptyEmail() {
        return new User("Marty", null, "OCT.21.2015");
    }
    public static User createUserWithEmptyName() {
        return new User(null, RandomStringUtils.randomAlphanumeric(10) + "@back.com",
                "OCT.21.2015");
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

