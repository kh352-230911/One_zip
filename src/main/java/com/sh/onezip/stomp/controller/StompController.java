package com.sh.onezip.stomp.controller;

import com.sh.onezip.stomp.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class StompController {

    /**
     * @MessageMapping - applicationDestinationPrefix(/app)을 제외한 url을 작성
     * @SendTo - prefix 포함한 simple broker channel url
     */
    @MessageMapping("/abc")
    @SendTo("/sub/abc")
    public String abc(String message){
        log.debug("message = {}", message);
        return "🤗🤗🤗" + message + "🤗🤗🤗";
    }

    @MessageMapping("/member/{username}")
    @SendTo("/sub/member/{username}")
    public MessageDto dm(@DestinationVariable("username") String username, @RequestBody MessageDto messageDto){
        log.debug("message = {}", messageDto);
        return messageDto;
    }
}
