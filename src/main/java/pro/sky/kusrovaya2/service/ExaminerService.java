package pro.sky.kusrovaya2.service;

import pro.sky.kusrovaya2.model.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
