package com.sh.onezip.productquestion.service;

import com.sh.onezip.productquestion.dto.ProductQuestionCreateDto;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.repository.ProductQuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductQuestionService {
    @Autowired
    ProductQuestionRepository productQuestionRepository;
    @Autowired
    ModelMapper modelMapper;
    public List<ProductQuestion> pquestionFindByProductid(Long id) {
        return productQuestionRepository.pquestionFindByProductid(id);
    }

    public Page<ProductQuestionDto> productQuestionDtoFindAllByProductId(Pageable pageable, Long productId) {
        Page<ProductQuestion> productQuestionPage = productQuestionRepository.productQuestionFindAllByProductId(pageable, productId);
        return productQuestionPage.map((productQuestion) -> convertToProductQuestionDto(productQuestion));
    }

    private ProductQuestionDto convertToProductQuestionDto(ProductQuestion productQuestion) {
        ProductQuestionDto productQuestionDto = modelMapper.map(productQuestion, ProductQuestionDto.class);
        return productQuestionDto;
    }

    public List<ProductQuestionDto> productQuestionDtoFindAllByProductId(Long productId) {
        List<ProductQuestion> productQuestions = productQuestionRepository.productQuestionFindAllByProductId(productId);
        List<ProductQuestionDto> productQuestionDtos = new ArrayList<>();
        for (ProductQuestion productQuestion : productQuestions) {
            productQuestionDtos.add(convertToProductQuestionDto(productQuestion));
        }
        return productQuestionDtos;
    }

    public void createQuestion(ProductQuestionCreateDto productQuestionCreateDto) {
        ProductQuestion productQuestion = modelMapper.map(productQuestionCreateDto, ProductQuestion.class);
        productQuestionRepository.save(productQuestion);
    }
}
