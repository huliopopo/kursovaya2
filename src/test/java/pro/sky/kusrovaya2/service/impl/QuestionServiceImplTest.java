package pro.sky.kusrovaya2.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.kusrovaya2.exception.QuestionIsAlreadyAddedException;
import pro.sky.kusrovaya2.exception.QuestionNotFoundException;
import pro.sky.kusrovaya2.exception.QuestionsAreEmptyException;
import pro.sky.kusrovaya2.model.Question;
import pro.sky.kusrovaya2.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {
    private final QuestionService questionService = new QuestionServiceImpl();
    private final List<Question> questions = List.of(
            new Question("q1", "a1"),
            new Question("q2", "a2"),
            new Question("q3", "a3")
    );

    @BeforeEach
    public void beforeEach() {
        questions.forEach(questionService::add);
    }

    @AfterEach
    public void afterEach() {
        new ArrayList<>(questionService.getALL()).forEach(questionService::remove);
    }

    @Test
    void add1Test() {
        int listSize = questionService.getALL().size();
        Question expected = new Question("q4", "a4");
        assertThat(questionService.getALL()).doesNotContain(expected);

        Question actual = questionService.add("q4", "a4");

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getALL()).hasSize(listSize + 1);
        assertThat(questionService.getALL()).contains(expected);
    }

    @Test
    void add2Test() {
        int listSize = questionService.getALL().size();
        Question expected = new Question("q4", "a4");
        assertThat(questionService.getALL()).doesNotContain(expected);

        Question actual = questionService.add(new Question("q4", "a4"));

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getALL()).hasSize(listSize + 1);
        assertThat(questionService.getALL()).contains(expected);
    }

    @Test
    void add1NegativeTest() {
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add("q1", "a1"));

    }

    @Test
    void add2NegativeTest() {
        assertThatExceptionOfType(QuestionIsAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add(new Question("q1", "a1")));

    }

    @Test
    void removeTest() {
        int listSize = questionService.getALL().size();
        Question expected = new Question("q2", "a2");
        assertThat(questionService.getALL()).contains(expected);

        Question actual = questionService.remove(new Question("q2", "a2"));

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getALL()).hasSize(listSize - 1);
        assertThat(questionService.getALL()).doesNotContain(expected);

    }
    @Test
    void removeNegativeTest() {
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("q5", "a5")));
    }

    @Test
    void getALLTest() {
        assertThat(questionService.getALL()).containsExactlyInAnyOrderElementsOf(questions);
    }

    @Test
    void getRandomQuestionTest() {
        assertThat((questionService.getRandomQuestion())).isIn(questionService.getALL());
    }
    @Test
    void getRandomQuestionNegativeTest() {
        afterEach();
        assertThatExceptionOfType(QuestionsAreEmptyException.class)
                .isThrownBy(questionService::getRandomQuestion);
    }

}