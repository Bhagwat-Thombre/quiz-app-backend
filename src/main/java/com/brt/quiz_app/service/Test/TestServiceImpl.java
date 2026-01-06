package com.brt.quiz_app.service.Test;

import com.brt.quiz_app.dto.*;
import com.brt.quiz_app.entities.Questions;
import com.brt.quiz_app.entities.Test;
import com.brt.quiz_app.entities.TestResult;
import com.brt.quiz_app.entities.User;
import com.brt.quiz_app.repository.QuestionRepository;
import com.brt.quiz_app.repository.TestRepository;
import com.brt.quiz_app.repository.TestResultRepository;
import com.brt.quiz_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private UserRepository userRepository;

    public TestDTO createTest(TestDTO dto){
        Test test=new Test();
        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setTime(dto.getTime());


        return testRepository.save(test).getDTO();
    }


    public QuestionDTO addQuestionInTest(QuestionDTO dto){
        Optional<Test> optionalTest=testRepository.findById(dto.getId());
        if(optionalTest.isPresent()){
            Questions questions=new Questions();
            questions.setTest(optionalTest.get());
            questions.setQuestionText(dto.getQuestionText());
            questions.setOptionA(dto.getOptionA());
            questions.setOptionB(dto.getOptionB());
            questions.setOptionC(dto.getOptionC());
            questions.setOptionD(dto.getOptionD());
            questions.setCorrectOptions(dto.getCorrectOptions());

            return questionRepository.save(questions).getDto();
        }
        throw new EntityNotFoundException("Test not found");
    }

    public List<TestDTO> getAllTests(){
        return testRepository.findAll().stream().peek(test->test.setTime(test.getQuestions().size()*test.getTime())).collect(Collectors.toList()).stream().map(Test::getDTO).collect(Collectors.toList());
    }

    public TestDetailsDTO getAllQuestionsByTest(Long id){
        Optional<Test> optionalTest=testRepository.findById(id);
        TestDetailsDTO testDetailsDTO=new TestDetailsDTO();
        if(optionalTest.isPresent()){
            TestDTO testDTO=optionalTest.get().getDTO();
            testDTO.setTime(optionalTest.get().getTime()*optionalTest.get().getQuestions().size());

            testDetailsDTO.setTestDTO(testDTO);
            testDetailsDTO.setQuestions(optionalTest.get().getQuestions().stream().map(Questions::getDto).toList());

            return testDetailsDTO;
        }
        return testDetailsDTO;
    }

    public TestResultDTO submitTest(SubmitTestDTO request) {

        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new EntityNotFoundException("Test not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        int correctAnswer = 0;

        for (QuestionResponse response : request.getQuestionResponses()) {
            Questions question = questionRepository.findById(response.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException("Question not found"));

            // ✅ Safe null check before comparing
            String correctOption = question.getCorrectOptions();
            String selectedOption = response.getSelectedOption();

            if (correctOption != null && selectedOption != null && correctOption.equals(selectedOption)) {
                correctAnswer++;
            }
        }

        int totalQuestions = test.getQuestions().size();
        double percentage = totalQuestions > 0 ? ((double) correctAnswer / totalQuestions) * 100 : 0;

        TestResult testResult = new TestResult();
        testResult.setTest(test);
        testResult.setUser(user);
        testResult.setTotalQuestions(totalQuestions);
        testResult.setCorrectAnswer(correctAnswer);
        testResult.setPercentage(percentage);

        return testResultRepository.save(testResult).getDto();
    }


    public List<TestResultDTO> getAllTestResult(){
        return testResultRepository.findAll().stream().map(TestResult::getDto).collect(Collectors.toList());
    }

    public List<TestResultDTO> getAllTestResultsOfUser(Long userId){
        return testResultRepository.findAllByUserId(userId).stream().map(TestResult::getDto).collect(Collectors.toList());

    }
}
