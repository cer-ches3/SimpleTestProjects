import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private int score = 0;

    public User(String name) {
        this.name = name;
    }

    public int increaseScore() {
        return score++;
    }
}
