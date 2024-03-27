package com.sh.onezip.customerquestioncenter.service;

import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.customerquestioncenter.repository.QuestionCenterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class QuestionCenterService {
    @Autowired
    QuestionCenterRepository questionCenterRepository;
    // HBK start
    public Page<QuestionCenter> findAllQuestions(Pageable pageable) {
        return questionCenterRepository.findAllQuestions(pageable);
    }

    public void deleteByQId(Long id) {
        questionCenterRepository.deleteById(id);
    }

    public QuestionCenter findByQId(Long id) {
        return questionCenterRepository.findByQId(id);
    }

    public QuestionCenter saveQuestionCenter(QuestionCenter questionCenter) {
        return questionCenterRepository.save(questionCenter);

    }
    // HBK end
}
