package main.dto;

import main.model.Message;

import java.time.LocalDateTime;

public class MessageMapper {

    public static DtoMessage map (Message message){

        DtoMessage dtoMessage = new DtoMessage();
        dtoMessage.setDatetime(message.getDateTime().toString());
        dtoMessage.setUsername(message.getUser().getName());
        dtoMessage.setText(message.getMessage());
        return dtoMessage;
    }
}
