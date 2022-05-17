package com.example.states;

import java.util.HashMap;
import java.util.Map;

import com.example.MetricsConfig;

import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import application.boilerplate.MessageSender;
import application.context.ApplicationContext;
import application.context.annotation.Component;
import application.context.annotation.Message;
import application.context.annotation.State;

@State
@Component
public class InitialState {
    private static FluentLogger LOG = FluentLogger.getLogger("app", "127.0.0.1", 8080);

    @Message(message = "*")
    public void getInfo(Update update) {
        int userId = ApplicationContext.getCurrentUserId();
        String username = update.getMessage().getFrom().getUserName();
        String message = update.getMessage().getText();
        Map<String, Object> data = new HashMap<>();
        data.put("UserId", userId);
        data.put("UserName", username);
        data.put("Message", message);
        LOG.log("UserData", data);
        MetricsConfig.ReceivedMessagesCounter.increment();

        if (message.equals("Hello")) {
            MessageSender sender = new MessageSender();
            sender.setChatId(userId);
            sender.setText("Hello, " + username + "!");
            sender.sendMessage();
            MetricsConfig.SentMessagesCounter.increment();
        }
    }
}
