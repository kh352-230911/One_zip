package com.sh.onezip.customeranswercenter.service;

import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.customeranswercenter.repository.AnswerCenterRepository;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AnswerCenterService {
    @Autowired
    AnswerCenterRepository answerCenterRepository;
    // HBK start
    public AnswerCenter createAnswer(AnswerCenter answerCenter) {
        return answerCenterRepository.save(answerCenter);
    }

    public Optional<AnswerCenter> findById(Long id) {
        return answerCenterRepository.findById(id);
    }

    public AnswerCenter updateAnswerCenter(AnswerCenter newAnswer) {
        return answerCenterRepository.save(newAnswer);
    }
    // HBK end
}
