package com.example.test_system.service;

import com.example.test_system.entity.Question;
import com.example.test_system.entity.SubCategory;
import com.example.test_system.entity.enums.DifficultyEnum;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.QuestionDto;
import com.example.test_system.payload.ResPageable;
import com.example.test_system.repository.QuestionRepository;
import com.example.test_system.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public ApiResponse getQuestionList(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<QuestionDto> questionDtos = new ArrayList<>();
        Page<Question> all = questionRepository.findAll(pageRequest);
        for (Question question : all) {
            QuestionDto questionDto = QuestionDto.builder()
                    .id(question.getId())
                    .question(question.getQuestion())
                    .subCategoryId(question.getSubCategory().getId())
                    .difficultyEnum(question.getQuestion())
                    .build();
            questionDtos.add(questionDto);
        }
        ResPageable resPageable=ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .build();
        return new ApiResponse("Success", true, HttpStatus.OK, resPageable);
    }

//    package com.example.test_system.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Entity
//    @Builder
//    @Table(name = "groups")
//    public class Group {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Integer id;
//
//        @Column(unique = true, nullable = false)
//        private String name;
//
//        @ManyToOne
//        private Category category;
//
//        @ManyToOne
//        private User teacherId;
//
//    }
//package com.example.test_system.entity;
//
//import com.example.test_system.entity.enums.RoleEnum;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Entity
//    @Builder
//    @Table(name = "users")
//    public class User implements UserDetails {
//        @Id
//        @GeneratedValue(strategy = GenerationType.UUID)
//        private UUID id;
//
//        @Column(nullable = false)
//        private String firstname;
//
//        private String lastname;
//
//        @Column(unique = true, nullable = false)
//        private String phoneNumber;
//
//        private Date birthDate;
//
//        @OneToOne
//        private Address address;
//
//        @Enumerated(EnumType.STRING)
//        private RoleEnum roleEnum;
//        @ManyToOne
//        private Group group;
//
//        @Column(nullable = false)
//        private String password;
//
//        private boolean accountNonExpired = true;
//        private boolean accountNonLocked = true;
//        private boolean credentialsNonExpired = true;
//        private boolean enabled;
//
//
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return List.of(new SimpleGrantedAuthority(roleEnum.name()));
//        }
//
//        @Override
//        public String getUsername() {
//            return phoneNumber;
//        }
//
//
//        @Override
//        public boolean isAccountNonExpired() {
//            return accountNonExpired;
//        }
//
//        @Override
//        public boolean isAccountNonLocked() {
//            return accountNonLocked;
//        }
//
//        @Override
//        public boolean isCredentialsNonExpired() {
//            return credentialsNonExpired;
//        }
//
//        @Override
//        public boolean isEnabled() {
//            return enabled;
//        }
//    }
//package com.example.test_system.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalTime;
//import java.util.List;
//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Entity
//    @Builder
//    public class Result {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Integer id;
//
//        //    Current user b/n topiladi
//        @ManyToOne
//        private User student;
//
//        @ManyToOne
//        private Test test;
//
//        @OneToMany
//        private List<Answer> answer;
//
//        @Column(nullable = false)
//        private LocalTime startTime;
//
//        @Column(nullable = false)
//        private LocalTime endTime;
//
//        private Integer correctCount;
//
//        private Boolean passed;
//
//        private Boolean checked;
//    }




}
