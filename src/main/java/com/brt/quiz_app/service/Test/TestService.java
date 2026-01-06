package com.brt.quiz_app.service.Test;

import com.brt.quiz_app.dto.*;
import com.brt.quiz_app.entities.Test;

import java.util.List;

public interface TestService {

    TestDTO createTest(TestDTO dto);
    QuestionDTO addQuestionInTest(QuestionDTO dto);
    List<TestDTO> getAllTests();
    TestDetailsDTO getAllQuestionsByTest(Long id);
    TestResultDTO submitTest(SubmitTestDTO request);
    List<TestResultDTO> getAllTestResult();
    List<TestResultDTO> getAllTestResultsOfUser(Long userId);
}
