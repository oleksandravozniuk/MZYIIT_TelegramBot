package com.example.states;

import java.util.HashMap;
import java.util.Map;

import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    }
}
