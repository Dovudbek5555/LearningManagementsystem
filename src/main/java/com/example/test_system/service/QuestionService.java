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

import java.util.ArrayList;
import java.util.List;

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
        boolean exists = questionRepository.existsByQuestion(questionDto.getQuestion());
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

    public ApiResponse getQuestionList(){
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            QuestionDto questionDto = QuestionDto.builder()
                    .id(question.getId())
                    .question(question.getQuestion())
                    .subCategoryId(question.getSubCategory().getId())
                    .difficultyEnum(String.valueOf(question.getDifficulty()))
                    .build();
            questionDtos.add(questionDto);
        }
        return new ApiResponse("Success", true, HttpStatus.OK, questionDtos);
    }

    public ApiResponse updateQuestion(QuestionDto questionDto){
        Question question = questionRepository.findById(questionDto.getId()).orElseThrow(() -> GenericException.builder().message("Question not found").statusCode(400).build());
        boolean existsed = questionRepository.existsByQuestion(question.getQuestion());
        if (!existsed){
            SubCategory subCategory = subCategoryRepository.findById(questionDto.getSubCategoryId()).orElseThrow(() -> GenericException.builder().message("SubCategory not found").statusCode(400).build());
            question.setId(questionDto.getId());
            question.setQuestion(questionDto.getQuestion());
            question.setSubCategory(subCategory);
            question.setDifficulty(DifficultyEnum.valueOf(questionDto.getDifficultyEnum()));
            questionRepository.save(question);
            return new ApiResponse("Question successfully updated", true, HttpStatus.OK, null);
        }
        return new ApiResponse("Question not found", false, HttpStatus.BAD_REQUEST, null);
    }

    public ApiResponse deleteQuestion(Integer id){
        Question question = questionRepository.findById(id).orElseThrow(() -> GenericException.builder().message("Question not found").statusCode(400).build());
        questionRepository.delete(question);
        return new ApiResponse("Question successfully deleted", true, HttpStatus.OK, null);
    }

    public ApiResponse filterQuestionByDifficulty(String difficulty){
        List<Question> allByDifficulty = questionRepository.findAllByDifficulty(DifficultyEnum.valueOf(difficulty));
        return new ApiResponse("Success", true,HttpStatus.OK,allByDifficulty);
    }

    public ApiResponse filterQuestionBySubCategory(Integer subCategoryId){
        List<Question> allBySubCategoryId = questionRepository.findAllBySubCategory_Id(subCategoryId);
        return new ApiResponse("Success", true,HttpStatus.OK,allBySubCategoryId);
    }
}
