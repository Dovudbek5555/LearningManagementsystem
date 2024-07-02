package com.example.test_system.service;

import com.example.test_system.entity.*;
import com.example.test_system.exceptions.GenericException;
import com.example.test_system.payload.AnswerDto;
import com.example.test_system.payload.ApiResponse;
import com.example.test_system.payload.GroupDto;
import com.example.test_system.payload.ResultDto;
import com.example.test_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExaminationService {

    private final ExamRepository examRepository;
    private final TestRepository testRepository;
    private final ResultRepository resultRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final GroupRepository groupRepository;

    public ApiResponse startTest(Integer id, User user) {
        Exam exam = fetchExam(id);

        if (!isUserInGroup(exam, user)) {
            return new ApiResponse("Your group is not allowed for this test", false, HttpStatus.METHOD_NOT_ALLOWED, null);
        }

        if (!isExamAvailable(exam.getId())) {
            return new ApiResponse("This exam is outdated", false, HttpStatus.METHOD_NOT_ALLOWED, null);
        }

        Result result = createResult(user, exam);
        resultRepository.save(result);
        ResultDto resultDto = createResultDto(user, exam);

        return new ApiResponse("Successfully started exam", true, HttpStatus.OK, resultDto);
    }

    private Exam fetchExam(Integer examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> GenericException.builder().message("Exam not found").statusCode(404).build());
    }

    private boolean isUserInGroup(Exam exam, User user) {
        for (Group group : user.getGroup()) {
            if (exam.getGroup().getId().equals(group.getId())){
                return true;
            }
        }
        return false;
    }

    private Result createResult(User user, Exam exam) {
        return Result.builder()
                .student(user)
                .exam(exam)
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plus(exam.getTest().getDuration()))
                .checked(false)
                .build();
    }

    private ResultDto createResultDto(User user, Exam exam) {
        return ResultDto.builder()
                .studentId(user.getId())
                .examId(exam.getId())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plus(exam.getTest().getDuration()))
                .checked(false)
                .build();
    }

    public boolean isExamAvailable(Integer examId) {
        return examRepository.findById(examId)
                .map(exam -> exam.getFinishDate().isAfter(LocalDate.now()))
                .orElse(false);
    }


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

//    private ApiResponse getResultByUncheckedOfTeacher(User user){
//        List<Group> groups = groupRepository.findAllByTeacherId_Id(user.getId());
//        List<Result> results = resultRepository.findAllByCheckedIsFalse();
//        Map<GroupDto, List<ResultDto>> groupByUnchecked = new HashMap<>();
//        for (Result result : results) {
//            if (groups.contains(result.getExam().getGroup())){
//                List<Result> uncheckedResultsByGroup =
//                        resultRepository.findUncheckedResultsByGroup(result.getExam().getGroup());
//                List<ResultDto> resultDtos = new ArrayList<>();
//                for (Result result1 : uncheckedResultsByGroup) {
//                    ResultDto resultDto = convertToResultDto(result1);
//                    resultDtos.add(resultDto);
//                }
//                groupByUnchecked.put(convertToGroupDto(result.getExam().getGroup()), resultDtos);
//                return new ApiResponse("Succesfully retrieved unchecked results", true, HttpStatus.OK, groupByUnchecked);
//            }
//        }
//        return new ApiResponse("You don't have unchecked answers", true, HttpStatus.OK, null);
//    }

    public ApiResponse getResultByUncheckedOfTeacher(User user) {
        // Fetch groups taught by the teacher
        List<Group> groups = groupRepository.findAllByTeacherId_Id(user.getId());

        // Fetch unchecked results
        List<Result> results = resultRepository.findAllByCheckedIsFalse();

        Map<GroupDto, List<ResultDto>> groupByUnchecked = new HashMap<>();

        // Iterate through groups
        for (Group group : groups) {
            List<Result> uncheckedResultsByGroup = new ArrayList<>();

            // Filter results by group
            for (Result result : results) {
                if (result.getExam().getGroup().equals(group)) {
                    uncheckedResultsByGroup.add(result);
                }
            }

            // Convert and group results if there are any unchecked results for the group
            if (!uncheckedResultsByGroup.isEmpty()) {
                List<ResultDto> resultDtos = new ArrayList<>();
                for (Result result : uncheckedResultsByGroup) {
                    ResultDto resultDto = convertToResultDto(result);
                    resultDtos.add(resultDto);
                }
                groupByUnchecked.put(convertToGroupDto(group), resultDtos);
            }
        }

        // Check if there are any results to return
        if (groupByUnchecked.isEmpty()) {
            return new ApiResponse("You don't have unchecked answers", true, HttpStatus.OK, null);
        }

        return new ApiResponse("Successfully retrieved unchecked results", true, HttpStatus.OK, groupByUnchecked);
    }

    private GroupDto convertToGroupDto(Group group){
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .categoryId(group.getCategory().getId())
                .teacherId(group.getTeacher().getId())
                .createdAt(group.getCreatedAt())
                .build();
    }

    private ResultDto convertToResultDto(Result result){
        List<AnswerDto> answerDtos = new ArrayList<>();
        for (Answer answer : result.getAnswer()) {
            AnswerDto answerDto = convertToAnswerDto(answer);
            answerDtos.add(answerDto);
        }
        return ResultDto.builder()
                .id(result.getId())
                .studentId(result.getStudent().getId())
                .answerDtos(answerDtos)
                .startTime(result.getStartTime())
                .endTime(result.getEndTime())
                .correctCount(result.getCorrectCount())
                .checked(result.getChecked())
                .build();
    }

    private AnswerDto convertToAnswerDto(Answer answer){
        return AnswerDto.builder()
                .id(answer.getId())
                .answer(answer.getAnswer())
                .correct(answer.isCorrect())
                .questionId(answer.getQuestion().getId())
                .build();
    }

}
