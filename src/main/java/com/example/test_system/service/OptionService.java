package com.example.test_system.service;

import com.example.test_system.entity.Question;
import com.example.test_system.entity.Option;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.OptionDto;
import com.example.test_system.repository.QuestionRepository;
import com.example.test_system.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final QuestionRepository QuestionRepository;
    private final OptionRepository optionRepository;

    public ApiResponse saveOption(OptionDto optionDto) {
        Question question = QuestionRepository.findById(optionDto.getQuestionId()).orElseThrow(() -> GenericException
                .builder()
                .message("Question not fond")
                .statusCode(400)
                .build());
        Option option = Option.builder()
                .description(optionDto.getDescription())
                .status(optionDto.getStatus())
                .question(question)
                .build();
        optionRepository.save(option);
        return new ApiResponse("Option successfully saved", true, HttpStatus.OK, null);
    }


    public ApiResponse getOptionList(){
        List<Option> optionList = optionRepository.findAll();
        List<OptionDto> optionDtos = new ArrayList<>();
        for (Option option : optionList) {
            OptionDto optionDto = OptionDto.builder()
                    .id(option.getId())
                    .description(option.getDescription())
                    .status(option.getStatus())
                    .questionId(option.getQuestion().getId())
                    .build();
            optionDtos.add(optionDto);
        }

        return new ApiResponse("Success", true, HttpStatus.OK, optionDtos);
    }

    public ApiResponse updateOption(OptionDto optionDto){
        Option option = optionRepository.findById(optionDto.getId()).orElseThrow(() -> new ResourceAccessException("option not found"));
        Question oQuestion = QuestionRepository.findById(optionDto.getQuestionId()).orElseThrow(() -> GenericException.builder()
                .message("Question not fond")
                .statusCode(400)
                .build());
        option.setId(option.getId());
        option.setDescription(option.getDescription());
        option.setStatus(optionDto.getStatus());
        option.setQuestion(oQuestion);
        optionRepository.save(option);
        return new ApiResponse("Option successfully updated", true, HttpStatus.OK, null);
    }

    public ApiResponse deleteOption(Integer id){
        optionRepository.findById(id).orElseThrow(() -> new ResourceAccessException("option not found"));
        optionRepository.deleteById(id);
        return new ApiResponse("Option successfully deleted", true, HttpStatus.OK, null);
    }
}
