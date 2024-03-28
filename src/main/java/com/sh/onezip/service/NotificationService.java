package com.sh.onezip.service;

import com.sh.onezip.stomp.dto.MessageDto;
import com.sh.onezip.stomp.dto.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class NotificationService {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void notifyFileDownload(Long id) {
        // 1. 파일관련 정보 조회

        // 2. 메시지 Dto 생성
        MessageDto messageDto = MessageDto.builder()
                .type(Type.NOTIFICATION)
                .from("admin")
                .to("honggd")
                .message("무슨 파일이 다운로드 되었습니다...")
                .createdAt(System.currentTimeMillis()) // 현재시각을 UnixTime으로 변환
                .build();

        // 3. stomp 해당 사용자에게 알림
        simpMessagingTemplate.convertAndSend("/sub/member/" + messageDto.getTo(), messageDto);
    }
}