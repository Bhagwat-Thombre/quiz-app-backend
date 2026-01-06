package com.brt.quiz_app.entities;

import com.brt.quiz_app.dto.TestResultDTO;
import jakarta.persistence.*;

@Entity
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalQuestions;
    private int correctAnswer;
    private double percentage;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TestResultDTO getDto(){
        TestResultDTO dto=new TestResultDTO();

        dto.setId(id);
        dto.setTotalQuestions(totalQuestions);
        dto.setCorrectAnswer(correctAnswer);
        dto.setPercentage(percentage);
        dto.setTestName(test.getTitle());
        dto.setUserName(user.getName());

        return dto;
    }
}
