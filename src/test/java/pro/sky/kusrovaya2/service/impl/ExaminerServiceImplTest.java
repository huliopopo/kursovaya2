package pro.sky.kusrovaya2.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.kusrovaya2.exception.IncorrectAmountException;
import pro.sky.kusrovaya2.model.Question;
import pro.sky.kusrovaya2.service.QuestionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;
    @InjectMocks
    ExaminerServiceImpl examinerService;
    private final List<Question> questions = List.of(
            new Question("q1", "a1"),
            new Question("q2", "a2"),
            new Question("q3", "a3")
    );

    @BeforeEach
    public void beforeEach() {
        when(questionService.getALL()).thenReturn(questions);
    }

    @Test
    void getQuestionsTest() {
        when(questionService.getRandomQuestion()).thenReturn(
                new Question("q1", "a1"),
                new Question("q3", "a3"),
                new Question("q3", "a3"),
                new Question("q2", "a2")
        );

        assertThat(examinerService.getQuestions(3)).containsExactlyInAnyOrder(
                new Question("q1", "a1"),
                new Question("q2", "a2"),
                new Question("q3", "a3")
        );
    }

    @Test
    void getQuestionsNegativeTest() {
        assertThatExceptionOfType(IncorrectAmountException.class)
                .isThrownBy(() -> examinerService.getQuestions(4));
        assertThatExceptionOfType(IncorrectAmountException.class)
                .isThrownBy(() -> examinerService.getQuestions(-2));

    }
}