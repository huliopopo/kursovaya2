package pro.sky.kusrovaya2.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.kusrovaya2.exception.QuestionIsAlreadyAddedException;
import pro.sky.kusrovaya2.exception.QuestionNotFoundException;
import pro.sky.kusrovaya2.exception.QuestionsAreEmptyException;
import pro.sky.kusrovaya2.model.Question;
import pro.sky.kusrovaya2.service.QuestionService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final Set<Question> questions = new HashSet<>();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionIsAlreadyAddedException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getALL() {
        return Collections.unmodifiableCollection(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new QuestionsAreEmptyException();
        }
        int randomQuestion = ThreadLocalRandom.current().nextInt(questions.size());
        return questions.stream()
                .skip(randomQuestion)
                .findFirst()
                .orElseThrow(QuestionIsAlreadyAddedException::new);
    }
}
