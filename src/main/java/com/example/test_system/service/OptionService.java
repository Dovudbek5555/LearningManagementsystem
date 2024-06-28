package com.example.test_system.service;

import com.example.test_system.entity.Option;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.OptionDto;
import com.example.test_system.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.test_system.entity.enums.OptionEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;

    public ApiResponse saveOption(OptionDto optionDto) {
        boolean exists = optionRepository.existsByOptionEnum(optionDto.getOptionEnum().toUpperCase());
        if (!exists){
            Option option= Option.builder()
                    .optionEnum(OptionEnum.valueOf(optionDto.getOptionEnum()))
                    .description(optionDto.getDescription())
                     .status(optionDto.getStatus())
                    .build();
            optionRepository.save(option);
            return new ApiResponse("Option successfully saved",true, HttpStatus.OK, null);
        }
        return new ApiResponse("OptionEnum not found",false, HttpStatus.BAD_REQUEST, null);
    }

    public ApiResponse getOptionList(){
        List<OptionDto> optionDtos = new ArrayList<>();
        for (Option option : optionRepository.findAll()) {
            OptionDto optionDto = OptionDto.builder()
                    .id(option.getId())
                    .optionEnum(String.valueOf(option.getOptionEnum()))
                    .description(option.getDescription())
                    .status(option.getStatus())
                    .build();
            optionDtos.add(optionDto);
        }

        return new ApiResponse("Success", true, HttpStatus.OK, optionDtos);
    }

    public ApiResponse getOneOption(Integer id){
        Option option = optionRepository.findById(id).orElseThrow(() -> new ResourceAccessException("option not found"));
        OptionDto optionDto = OptionDto.builder()
                .id(option.getId())
                .optionEnum(String.valueOf(option.getOptionEnum()))
                .description(option.getDescription())
                .status(option.getStatus())
                .build();
        return new ApiResponse("Success", true, HttpStatus.OK, optionDto);
    }

    public ApiResponse updateOption(OptionDto optionDto){
        Option option = optionRepository.findById(optionDto.getId()).orElseThrow(() -> new ResourceAccessException("option not found"));
        option.setId(option.getId());
        option.setOptionEnum(OptionEnum.valueOf(optionDto.getOptionEnum()));
        option.setDescription(option.getDescription());
        option.setStatus(optionDto.getStatus());
        optionRepository.save(option);
        return new ApiResponse("Option successfully updated", true, HttpStatus.OK, null);
    }

    public ApiResponse deleteOption(Integer id){
        optionRepository.findById(id).orElseThrow(() -> new ResourceAccessException("option not found"));
        optionRepository.deleteById(id);
        return new ApiResponse("Option successfully deleted", true, HttpStatus.OK, null);
    }
}
