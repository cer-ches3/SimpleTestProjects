import java.util.Scanner;

public class Function {

    public String getSettingValue(String value) {
        String gamerValue = "";
        switch (value) {
            case "К" -> gamerValue = "Камень";
            case "Н" -> gamerValue = "Ножницы";
            case "Б" -> gamerValue = "Бумага";
            case "СТОП" -> gamerValue = "СТОП";
            default -> gamerValue = "Игрок выбрал не корректное значение";
        }
        return gamerValue;
    }

    public void getAllScore(User user1, User user2) {
        System.out.println("_______________________________" + "\n" +
                "Счет: " + "\n" +
                user1.getName() + "-" + user1.getScore() + "\n" +
                user2.getName() + " - " + user2.getScore() + "\n" +
                "_______________________________\n");
    }

    public boolean stop(User user1, User user2) {
        boolean result;
        System.out.println("Вы действительно хотите закончить игру? ДА/НЕТ");
        String answer = new Scanner(System.in).nextLine().toUpperCase();
        if (answer.equals("ДА")) {
            getAllScore(user1, user2);
            String winner = "";
            if (user1.getScore() > user2.getScore()) {
                System.out.println("Победитель: " + user1.getName());
            }
            if (user2.getScore() > user1.getScore()) {
                System.out.println("Победитель: " + user2.getName());
            }
            if (user1.getScore() == user2.getScore()) {
                System.out.println("Ничья");
            }
            System.out.println("Спасибо за игру!");
            result = true;
        } else {
            System.out.println("Продолжим игру!");
            result = false;
        }
        return result;
    }
}
