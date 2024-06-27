package com.example.test_system.service;

import com.example.test_system.entity.Option;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.OptionDto;
import com.example.test_system.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.test_system.entity.enums.OptionEnum;

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
            return new ApiResponse("Option successfully saved",true);
        }
        return new ApiResponse("OptionEnum not found",false);
    }
}
