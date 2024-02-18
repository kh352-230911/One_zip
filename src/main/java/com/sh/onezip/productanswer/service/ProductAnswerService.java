package com.sh.onezip.productanswer.service;

import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productanswer.repository.ProductAnswerRepository;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j

public class ProductAnswerService {
    @Autowired
    ProductAnswerRepository productAnswerRepository;

    @Autowired
    ModelMapper modelMapper;

//    public ProductAnswer qanswerupdate(ProductQuestion productQuestion) {
//        ProductAnswer updatedAnswer = productAnswerRepository.save(productAnswer);
//        return updatedAnswer;
//    }

    public ProductAnswer qanswerupdate(ProductAnswer productAnswer) {
        // 1. 전달받은 ProductAnswer 객체의 내용을 변경하거나 업데이트합니다.
        productAnswer.setARegdate(LocalDate.now()); // 예시: 답변 등록일 업데이트

        // 2. 변경된 ProductAnswer 객체를 저장하고 데이터베이스에 반영합니다.
        ProductAnswer updatedAnswer = productAnswerRepository.save(productAnswer);

        // 3. 변경된 ProductAnswer 객체를 반환합니다.
        return updatedAnswer;
    }



//    public ProductAnswer qanswerupdate(ProductAnswer productAnswer) {
//        ProductAnswer newAnswer = convertToqanswerupdate(productAnswer);
//        System.out.println(productAnswer + "나오냐");
//        return productAnswerRepository.save(newAnswer);
//    }
//
//    private ProductAnswer convertToqanswerupdate(ProductAnswer productQuestionDto) {
//        ProductAnswer productAnswer = modelMapper.map(productQuestionDto, ProductAnswer.class);
//        return productAnswer;
//    }

}
