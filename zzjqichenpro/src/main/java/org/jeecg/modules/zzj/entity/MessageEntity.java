package org.jeecg.modules.zzj.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageEntity implements Serializable {

    private String messageType;
    private String messageContent;

}
