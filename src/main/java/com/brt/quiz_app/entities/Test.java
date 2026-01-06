package com.brt.quiz_app.entities;

import com.brt.quiz_app.dto.TestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.List;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private  Long time;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Questions> questions;

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestDTO getDTO() {
        TestDTO testDTO = new TestDTO();
        testDTO.setId(this.id);
        testDTO.setTitle(this.title);
        testDTO.setDescription(this.description);
        testDTO.setTime(this.time);
        return testDTO;
    }

}
