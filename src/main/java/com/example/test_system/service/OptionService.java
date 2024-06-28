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
import com.example.test_system.entity.enums.OptionEnum;

import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final QuestionRepository QuestionRepository;
    private final OptionRepository optionRepository;

    public ApiResponse saveOption(OptionDto optionDto) {
        boolean exists = optionRepository.existsByOptionEnum(optionDto.getOptionEnum());
        Question oQuestion = QuestionRepository.findById(optionDto.getQuestionId()).orElseThrow(() -> GenericException.builder()
                .message("Question not fond")
                .statusCode(400)
                .build());
        if (!exists) {
            Option option = Option.builder()
                    .optionEnum(OptionEnum.valueOf(optionDto.getOptionEnum()))
                    .description(optionDto.getDescription())
                    .status(optionDto.getStatus())
                    .oQuestion(oQuestion)
                    .build();
            optionRepository.save(option);
            return new ApiResponse("Option successfully saved", true, HttpStatus.OK, null);
        }
        return new ApiResponse("OptionEnum already exists", false, HttpStatus.BAD_REQUEST, null);
    }


    public ApiResponse getOptionList(){
        List<OptionDto> optionDtos = new ArrayList<>();
        for (Option option : optionRepository.findAll()) {
            OptionDto optionDto = OptionDto.builder()
                    .id(option.getId())
                    .optionEnum(String.valueOf(option.getOptionEnum()))
                    .description(option.getDescription())
                    .status(option.getStatus())
                    .questionId(option.getOQuestion().getId())
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
        option.setOptionEnum(OptionEnum.valueOf(optionDto.getOptionEnum()));
        option.setDescription(option.getDescription());
        option.setStatus(optionDto.getStatus());
        option.setOQuestion(oQuestion);
        optionRepository.save(option);
        return new ApiResponse("Option successfully updated", true, HttpStatus.OK, null);
    }

    public ApiResponse deleteOption(Integer id){
        optionRepository.findById(id).orElseThrow(() -> new ResourceAccessException("option not found"));
        optionRepository.deleteById(id);
        return new ApiResponse("Option successfully deleted", true, HttpStatus.OK, null);
    }
}
