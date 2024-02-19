package com.sh.onezip.productquestion.service;

import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productanswer.repository.ProductAnswerRepository;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.repository.ProductQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProductQuestionService {

    @Autowired
    ProductQuestionRepository productQuestionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductAnswerRepository productAnswerRepository;

    // 질문 상세 페이지 (Get)
    public ProductQuestionDto findByProductQuestionAnswerId(Long id) {
        return productQuestionRepository.findById(id)
                .map((productQuestion) -> convertTofindByProductQuestionAnswer(productQuestion))
        .orElseThrow();
    }

    private ProductQuestionDto convertTofindByProductQuestionAnswer(ProductQuestion productQuestion) {
        ProductQuestionDto productQuestionDto = modelMapper.map(productQuestion, ProductQuestionDto.class);
        return productQuestionDto;
    }

    public Optional<ProductQuestion> findById(Long id) {
        return productQuestionRepository.findById(id);
    }



//    public void saveAnswer(ProductAnswer productAnswer) {
//        try {
//            ProductAnswer savedAnswer = productAnswerRepository.save(productAnswer);
//            if (savedAnswer != null) {
//                // 답변이 성공적으로 저장된 경우 처리
//            } else {
//                // 답변 저장에 실패한 경우 처리
//            }
//        } catch (Exception e) {
//            // 데이터베이스 작업 중 예외 발생 시 처리
//            e.printStackTrace();
//            // 예외 처리 로직 추가
//        }
//    }
//    public void createAnswer(Optional<ProductQuestion> productQuestion) {
//        productQuestionRepository.save();
//        ProductAnswer productAnswer = modelMapper.map(productQuestion, ProductAnswer.class);
//        productAnswerRepository.save(productAnswer);
    }


//    public Optional<ProductQuestion> findByDetailProductQuestionAnswerId(Long id) {
//        return productQuestionRepository.findById(id);
//
//    }
//    public void bizAnswercreate(ProductQuestionDto productQuestionDto) {
//        private ProductAnswer convertToanswercreate(ProductQuestionDto ) {
//            ProductAnswer productAnswer = new ProductAnswer();
//            productAnswer.setAContent(productQuestionDto.getProductAnswer().getAContent());
//            // 나머지 필드도 필요한 경우 설정
//            return productAnswer;
//        }
//    }
//
//    private ProductQuestion convertToanswercreate(ProductQuestionDto productQuestionDto) {
//    }

//    // 질문에 대한 답변 등록 (Post)
//    public void bizAnswercreate(ProductQuestionDto productQuestionDto) {
//        ProductQuestion productQuestion = convertToanswercreate(productQuestionDto);
//        System.out.println(productQuestion + "넘어왔나");
//        ProductQuestion productAnswer = productAnswerRepository.save(productQuestion);
//    }
//
//    private ProductQuestion convertToanswercreate(ProductQuestionDto productQuestionDto) {
//    }

