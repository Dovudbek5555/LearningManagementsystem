package com.example.test_system.service;

import com.example.test_system.entity.Question;
import com.example.test_system.entity.SubCategory;
import com.example.test_system.entity.enums.DifficultyEnum;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.QuestionDto;
import com.example.test_system.repository.QuestionRepository;
import com.example.test_system.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ApiResponse saveQuestion(QuestionDto questionDto){
        SubCategory subCategory = subCategoryRepository.findById(questionDto.getSubCategoryId()).orElseThrow(() -> GenericException.builder()
                .message("Question not fond")
                .statusCode(400)
                .build());
        boolean exists = questionRepository.existsByDifficulty(questionDto.getQuestion());
        if (!exists){
            Question question = Question.builder()
                    .question(questionDto.getQuestion())
                    .subCategory(subCategory)
                    .difficulty(DifficultyEnum.valueOf(questionDto.getDifficultyEnum()))
                    .build();
            questionRepository.save(question);
            return new ApiResponse("Question successfully saved", true, HttpStatus.OK, null);
        }
        return new ApiResponse("DifficultyEnum not found", false, HttpStatus.BAD_REQUEST, null);
    }


}
