package com.sh.onezip.tip.service;

import com.sh.onezip.tip.entity.TipComment;
import com.sh.onezip.tip.repository.TipCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipCommentService {
    @Autowired
    private TipCommentRepository tipCommentRepository;

    @Transactional
    public void createComment(TipComment tipComment) {
        tipCommentRepository.save(tipComment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        tipCommentRepository.deleteById(commentId);
    }
}
