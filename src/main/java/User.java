import io.qameta.allure.Step;
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
    @Step("берутся данные уже созданного, валидного пользователя")
    public static User getExistUser() {
        return new User("Marty", "Marty-McFly@back.com",
                "OCT.21.2015");
    }
    @Step("берутся данные для нового, валидного пользователя")
    public static User dataForCreateUser() {
        return new User("Marty", RandomStringUtils.randomAlphanumeric(10) + "@back.com",
                "OCT.21.2015");
    }
    @Step("берутся данные для создания нового пользователя без пароля")
    public static User createUserWithoutPassword() {
        return new User("Marty",
                RandomStringUtils.randomAlphanumeric(10) + "@back.com", null);
    }
    @Step("берутся данные для создания нового пользователя без почты")
    public static User createUserWithoutEmail() {
        return new User("Marty", null, "OCT.21.2015");
    }
    @Step("берутся данные для создания нового пользователя без имени")
    public static User createUserWithoutName() {
        return new User(null, RandomStringUtils.randomAlphanumeric(10) + "@back.com",
                "OCT.21.2015");
    }
    @Step("берется e-mail пользователя")
    public String getEmail() {
        return email;
    }
    @Step("берется пароль пользователя")
    public String getPassword() {
        return password;
    }
    @Step("устанавливается новый e-mail пользователю")
    public void setEmail(String email) {
        this.email = email;
    }
    @Step("берется name пользователя")
    public String getName() {
        return name;
    }
    @Step("устанавливается новый name пользователю")
    public void setName(String name) {
        this.name = name;
    }
    @Step("устанавливается новый пароль пользователю")
    public void setPassword(String password) {
        this.password = password;
    }
}

