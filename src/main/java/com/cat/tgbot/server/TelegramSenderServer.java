package com.cat.tgbot.server;

import com.cat.tgbot.utils.TelegramBotClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramSenderServer {
    public static TelegramBotClient telegramBotClient;
    private static final String TOKEN = "7673100781:AAF0CYU0HC6th_tTg5HeehtzBnJcacHDTkE";
    private static final String USERNAME = "sanxiantang_Test_bot";

    @PostConstruct
    public void init(){
        try {
            DefaultBotOptions botOptions = new DefaultBotOptions();
            botOptions.setProxyType(DefaultBotOptions.ProxyType.NO_PROXY);
            TelegramBotsApi telegramBotsApi=new TelegramBotsApi(DefaultBotSession.class);
            telegramBotClient=TelegramBotClient.getInstance(botOptions, TOKEN, USERNAME);
            telegramBotsApi.registerBot(telegramBotClient);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}