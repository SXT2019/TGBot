package com.cat.tgbot.utils;

import com.cat.tgbot.bean.user.ChatBean;
import com.cat.tgbot.bean.user.ReChat;
import com.cat.tgbot.bean.user.ReMessage;
import lombok.extern.log4j.Log4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

@Log4j
public class TelegramBotClient extends TelegramLongPollingBot {
    private Log logger = LogFactory.getLog(TelegramBotClient.class);
    private String token;
    private String userName;
    private static TelegramBotClient instance;

    private TelegramBotClient(DefaultBotOptions botOptions, String token, String userName){
        super(botOptions);
        this.token = token;
        this.userName = userName;
    }

    public static TelegramBotClient getInstance(DefaultBotOptions botOptions, String token, String userName){
        if (instance == null){
            instance = new TelegramBotClient(botOptions, token, userName);
        }
        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            Chat chat = message.getChat();
            logger.info(chat);
            String chatType = chat.getType();
            logger.info(chatType + " : " + text);
            if ("private".equals(chatType)) {
                Long userId = chat.getId();
                String userName=chat.getUserName();

                //创建消息列表
                List<String> messagesList=new ArrayList<>();
                messagesList.add("chatId");
                messagesList.add("测试");
                messagesList.add("哈哈");
                messagesList.add("chat");
                messagesList.add("message");
                List<ChatBean> chatBeanList=new ArrayList<>();
                for (int i=0;i<messagesList.size();i++){
                    ChatBean chatBean=new ChatBean();
                    chatBean.setId(i);
                    chatBean.setMessage(messagesList.get(i));
                    chatBeanList.add(chatBean);
                }

                //创建回复列表
                List<ReMessage> reMessageList=new ArrayList<>();
                List<String> bList=new ArrayList<>();
                bList.add(String.valueOf(userId));
                bList.add(userName+userId);
                bList.add("哈哈");
                bList.add(String.valueOf(chat));
                bList.add(String.valueOf(message));
                for(int i=0;i<bList.size();i++){
                    ReMessage reMessage=new ReMessage();
                    reMessage.setId(1);
                    reMessage.setContent(bList.get(i).toString());
                    reMessageList.add(reMessage);
                }

                List<ReChat> reChatList=new ArrayList<>();

                for (int i=0;i<chatBeanList.size();i++){
                    ReChat reChat=new ReChat();
                    reChat.setId(i);
                    reChat.setCid(i);
                    reChat.setRid(i);
                    reChatList.add(reChat);
                }

                for (ReChat reChat:reChatList){
                    ChatBean chatBean=chatBeanList.get(reChat.getCid());
                    ReMessage reMessage=reMessageList.get(reChat.getRid());
                    if (chatBean.getMessage().equals(text)) {
                        sendMsg(reMessage.getContent(), String.valueOf(userId));
                        return;
                    }
                }

                sendMsg(String.valueOf(message), String.valueOf(userId));

                /*if ("chatId".equals(text)) {
                    sendMsg(String.valueOf(userId+userName), String.valueOf(userId));
                }
                if("测试".equals(text)){
                    sendMsg(String.valueOf("成功部署机器人"), String.valueOf(userId));
                }
                 */
            } else if ("group".equals(chatType) || "supergroup".equals(chatType)) {
                Long chatId = chat.getId();
                if (("@" + userName + " chatId").equals(text)) {
                    sendMsg(String.valueOf(chatId), String.valueOf(chatId));
                }
            }
        }
    }

    /**
     * 发送消息
     * @param text
     * @param chatId
     */
    public void sendMsg(String text, String chatId){
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(text);
        try {
            execute(response);
        }catch (Exception ex){}
    }

    @Override
    public String getBotUsername() {
        return this.userName;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
