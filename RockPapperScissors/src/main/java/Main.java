import java.util.*;

public class Main {
    public static void main(String[] args) {

        //Выводим правила
        outputRules();

        Function function = new Function();
        User user1 = new User("КОМПЬЮТЕР");
        User user2 = new User("ИГРОК");

        //Задаём имя реального игрока
        System.out.println("Ваше имя: ");
        String setName = new Scanner(System.in).nextLine().toUpperCase();
        System.out.println("Для игрока установлено имя " + "\"" + setName + "\"");
        user2.setName(setName);

        while (true) {
            //Выбор числа реального игрока и присвоение ему значения
            System.out.println("Камень, Ножницы, Бумага: ");
            String userValue = new Scanner(System.in).nextLine().toUpperCase();
            System.out.println("Игрок " + "\"" + user2.getName() + "\"" + ": " + function.getSettingValue(userValue));

            //Установка числа компьютера и присвоение ему значения
            List<String> values = Arrays.asList("К", "Н", "Б");
            Collections.shuffle(values);
            String computerValue = values.get(0);
            System.out.println("Игрок " + "\"" + user1.getName() + "\"" + ": " + function.getSettingValue(computerValue));

            System.out.println();

            //Параметры для сравнения значений и получение результатов
            switch (userValue) {
                case "К" -> {
                    if (computerValue.equals("К")) {
                        System.out.println("Ничья");
                    }
                    if (computerValue.equals("Н")) {
                        System.out.println("Победил " + "\"" + user2.getName() + "\"");
                        user2.increaseScore();
                    }
                    if (computerValue.equals("Б")) {
                        System.out.println("Победил " + "\"" + user1.getName() + "\"");
                        user1.increaseScore();
                    }
                    function.getAllScore(user1, user2);
                }
                case "Н" -> {
                    if (computerValue.equals("Н")) {
                        System.out.println("Ничья");
                    }
                    if (computerValue.equals("Б")) {
                        System.out.println("Победил " + "\"" + user2.getName() + "\"");
                        user2.increaseScore();
                    }
                    if (computerValue.equals("К")) {
                        System.out.println("Победил " + "\"" + user1.getName() + "\"");
                        user1.increaseScore();
                    }
                    function.getAllScore(user1, user2);
                }
                case "Б" -> {
                    if (computerValue.equals("Б")) {
                        System.out.println("Ничья");
                    }
                    if (computerValue.equals("К")) {
                        System.out.println("Победил " + "\"" + user2.getName() + "\"");
                        user2.increaseScore();
                    }
                    if (computerValue.equals("Н")) {
                        System.out.println("Победил " + "\"" + user1.getName() + "\"");
                        user1.increaseScore();
                    }
                    function.getAllScore(user1, user2);
                }
            }
            if (userValue.equals("СТОП")) {
                boolean result = function.stop(user1, user2);
                if (result == true) {
                    break;
                }
            }
        }
    }

    public static void outputRules() {
        System.out.println("_____________________________________" + "\n" +
                "ИГРА КАМЕНЬ-НОЖНИЦЫ-БУМАГА\n" +
                "Игроку необходимо выбрать один из вариантов:\n " +
                "К - камень\n Н - ножницы\n Б - бумага" +
                "\nВ роли вашего соперника выступит компьютер, он рандомно будет сам выбирать один из вариантов.\n" +
                "Игра ведется на счет, победит тот, кто наберет больше очков.\n" +
                "Если вы хотите закончить игру - введите команду \"СТОП\" \n" +
                "Удачной игры!!!\n" +
                "_____________________________________" + "\n");
    }
}