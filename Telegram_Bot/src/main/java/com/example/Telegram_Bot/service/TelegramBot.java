package com.example.Telegram_Bot.service;

import com.example.Telegram_Bot.configuration.BotConfiguration;
import com.example.Telegram_Bot.entity.User;
import com.example.Telegram_Bot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfiguration botConfiguration;
    private final UserRepository userRepository;

    private static final String HELP_TEXT = "/start - Позволяет зарегистрировать пользователя в базе данных бота. Эту команду нужно обязательно выполнять при первом использовании бота;\n" +
            "/help - Позволяет получить информацию о функциях приложения;\n" +
            "/get_my_data - Позволяет получить данные текущего пользователя, зарегистрированного в базе данных бота;\n" +
            "/delete_my_data - Позволяет удалить данные текущего пользователя из базы данных бота;\n" +
            "/get_all_users - Позволяет получить данные всех пользователей, зарегистрированных в базе данных бота;";

    public TelegramBot(BotConfiguration botConfiguration, UserRepository userRepository) {
        this.botConfiguration = botConfiguration;
        this.userRepository = userRepository;
        List<BotCommand> listOfCommand = new ArrayList();
        listOfCommand.add(new BotCommand("/start", "Зарегистрировать пользователя в базе данных бота"));
        listOfCommand.add(new BotCommand("/help", "Получить информацию о функциях приложения"));
        listOfCommand.add(new BotCommand("/get_my_data", "Получить данные текущего пользователя"));
        listOfCommand.add(new BotCommand("/delete_my_data", "Удалить данные текущего пользователя из базы данных бота"));
        listOfCommand.add(new BotCommand("/get_all_users", "Получить данные всех пользователей, зарегистрированных в базе данных бота"));

        try {
            this.execute(new SetMyCommands(listOfCommand, new BotCommandScopeDefault(), null));
            log.info("Init list command for chatBot {}.", botConfiguration.getBotName());
        } catch (TelegramApiException e) {
            log.error(MessageFormat.format("Error init bot {0}: {1}.", botConfiguration.getBotName(), e.getMessage()));
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    log.info("Call onUpdateReceived -> /start.");

                    if (!userRepository.findUserByUsername(update.getMessage().getChat().getUserName()).isPresent()) {
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } else {
                        log.warn("User {} is found in database!", update.getMessage().getChat().getUserName());
                        startCommandReceivedIfUserExists(chatId, update.getMessage().getChat().getFirstName());
                    }
                    break;

                case "/help":
                    log.info("Call onUpdateReceived -> /help.");
                    sendMessage(chatId, HELP_TEXT);
                    break;

                case "/get_my_data":
                    log.info("Call onUpdateReceived -> /getMyData.");
                    sendMessage(chatId, loadMyData(update));
                    break;

                case "/delete_my_data":
                    log.info("Call onUpdateReceived -> /deleteMyData.");
                    sendMessage(chatId, deleteUserData(update));
                    break;

                case "/get_all_users":
                    log.info("Call onUpdateReceived -> /show_аll_users.");
                    sendMessage(chatId, showAllUsers());
                    break;

                default:
                    log.info("Call onUpdateReceived -> default.");
                    sendMessage(chatId, "Прости, но такая команда не поддерживается!");
            }
        }
    }

    @Override
    public String getBotUsername() {
        log.info("Call getBotUsername.");
        return botConfiguration.getBotName();
    }

    @Override
    public String getBotToken() {
        log.info("Call getBotToken.");
        return botConfiguration.getToken();
    }

    private void startCommandReceived(long chatId, String firstname) {
        String answer = "Привет, " + firstname + ". Меня зовут " + botConfiguration.getBotName() + ".\n" +
                "Я сохраню твои данные в своей базе данных, что бы в дальнейшем мы могли общаться. " +
                "Эти данные ты можешь удалить в любое время, только скажи.";
        sendMessage(chatId, answer);
    }

    private void startCommandReceivedIfUserExists(long chatId, String firstname) {
        String answer = firstname + ", у меня уже есть твои данные! Повторный вызов /start не требуется.";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
            log.info("Message {} send to user.", sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error send message: {}.", e.getMessage());
        }
    }

    private void registerUser(Message message) {
        var chatId = message.getChatId();
        var chat = message.getChat();

        User user = new User();
        user.setUserId(user.getUserId());
        user.setFirstname(chat.getFirstName());
        user.setLastname(chat.getLastName());
        user.setUsername(chat.getUserName());
        user.setDateRegistration(LocalDateTime.now());
        user.setChatId(chatId);

        userRepository.save(user);
        log.info("Users data is saved for user: {}.", user);
    }

    private String loadMyData(Update update) {
        String username = update.getMessage().getChat().getUserName();
        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            log.error("User {} not found!", username);
            return MessageFormat.format("Пользователь {0} не найден в базе! " +
                    "Попробуйте выполнить /start, затем повторите попытку снова.", username);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(user.getUserId()).append('\n')
                .append("Firstname: ").append(user.getFirstname()).append('\n')
                .append("LastName: ").append(user.getLastname()).append('\n')
                .append("Username: ").append(user.getUsername()).append('\n')
                .append("Date registration: ").append(user.getDateRegistration());
        log.info("Load users date for {}.", username);
        return builder.toString();
    }

    private String deleteUserData(Update update) {
        String username = update.getMessage().getChat().getUserName();
        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            log.error("User {} not found!", username);
            return MessageFormat.format("Пользователь {0} не найден в базе!", username);
        }
        userRepository.delete(user);
        log.info("Delete users date for {}.", username);
        return MessageFormat.format("Данные пользователя {0} успешно удалены!", username);
    }

    private String showAllUsers() {
        List<User> users = userRepository.findAll();

        StringBuilder builder = new StringBuilder();
        for (User user : users) {
            builder.append("ID: ").append(user.getUserId()).append('\n')
                    .append("Firstname: ").append(user.getFirstname()).append('\n')
                    .append("LastName: ").append(user.getLastname()).append('\n')
                    .append("Username: ").append(user.getUsername()).append('\n')
                    .append("Date registration: ").append(user.getDateRegistration()).append('\n')
                    .append('\n');
        }
        log.info("Load date all users.");
        return builder.toString();
    }
}
