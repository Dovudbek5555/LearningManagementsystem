package com.example.test_system.service;

import com.example.test_system.entity.*;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.AnswerDto;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.ResultDto;
import com.example.test_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final ExamRepository examRepository;
    private final TestRepository testRepository;
    private final ResultRepository resultRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public ApiResponse startTest(Integer id, User user) {
        Exam exam = fetchExam(id);
        Test test = fetchTest(exam.getId());

        if (!isUserInGroup(exam, user)) {
            return new ApiResponse("Your group is not allowed for this test", false, HttpStatus.METHOD_NOT_ALLOWED, null);
        }

        if (!isExamAvailable(exam.getId())) {
            return new ApiResponse("This exam is outdated", false, HttpStatus.METHOD_NOT_ALLOWED, null);
        }

        Result result = createResult(user, test);
        resultRepository.save(result);
        ResultDto resultDto = createResultDto(user, test);

        return new ApiResponse("Successfully started exam", true, HttpStatus.OK, resultDto);
    }

    private Exam fetchExam(Integer examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> GenericException.builder().message("Exam not found").statusCode(404).build());
    }

    private Test fetchTest(Integer testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> GenericException.builder().message("Test not found").statusCode(404).build());
    }

    private boolean isUserInGroup(Exam exam, User user) {
        return exam.getGroup().getId().equals(user.getGroup().getId());
    }

    private Result createResult(User user, Test test) {
        return Result.builder()
                .student(user)
                .test(test)
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plus(test.getDuration()))
                .checked(false)
                .build();
    }

    private ResultDto createResultDto(User user, Test test) {
        return ResultDto.builder()
                .studentId(user.getId())
                .testId(test.getId())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plus(test.getDuration()))
                .checked(false)
                .build();
    }

    public boolean isExamAvailable(Integer examId) {
        return examRepository.findById(examId)
                .map(exam -> exam.getFinishDate().isAfter(LocalDate.now()))
                .orElse(false);
    }

//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    public ApiResponse passResult(Integer resultId, List<AnswerDto> answerDtos) {
        Result result = fetchResult(resultId);
        int correctCount = 0;
        List<Answer> answers = new ArrayList<>();

        for (AnswerDto answerDto : answerDtos) {
            Question question = fetchQuestion(answerDto.getQuestionId());
            int optionCount = optionRepository.countByQuestion(question);

            if (optionCount > 0) {
                ApiResponse response = processOptionAnswer(answerDto, correctCount);
                if (!response.isSuccess()) {
                    return response;
                }
            } else {
                if (answerDto.getAnswer() == null || answerDto.getAnswer().isEmpty()) {
                    return new ApiResponse("Text answer not provided", false, HttpStatus.BAD_REQUEST, null);
                }
                answers.add(createAnswer(question, answerDto.getAnswer()));
            }
        }

        result.setAnswer(answers);
        result.setCorrectCount(correctCount);
        resultRepository.save(result);

        return new ApiResponse(correctCount == answerDtos.size() ? "Test successfully passed" : "Your exam is claimed", true, HttpStatus.OK, correctCount);
    }

    private Result fetchResult(Integer resultId) {
        return resultRepository.findById(resultId)
                .orElseThrow(() -> GenericException.builder().message("Result not found").statusCode(404).build());
    }

    private Question fetchQuestion(Integer questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> GenericException.builder().message("Question not found").statusCode(404).build());
    }

    private ApiResponse processOptionAnswer(AnswerDto answerDto, int correctCount) {
        if (answerDto.getOptionId() == 0) {
            return new ApiResponse("No option selected", false, HttpStatus.BAD_REQUEST, null);
        }

        Optional<Option> option = optionRepository.findById(answerDto.getOptionId());
        if (option.isPresent() && option.get().getStatus()) {
            correctCount++;
            return new ApiResponse(null, true, HttpStatus.OK, null);
        }
        return new ApiResponse("Option not found or incorrect", false, HttpStatus.NOT_FOUND, null);
    }

    private Answer createAnswer(Question question, String answerText) {
        return Answer.builder()
                .question(question)
                .answer(answerText)
                .correct(false)
                .build();
    }

//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------



}
