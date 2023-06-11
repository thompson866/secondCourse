package pro.sky.courseWorkTwo.service;

import org.springframework.stereotype.Service;
import pro.sky.courseWorkTwo.exception.QuestionAlreadyExistsException;
import pro.sky.courseWorkTwo.exception.QuestionNotFoundException;
import pro.sky.courseWorkTwo.exception.QuestionsAreEmptyException;

import pro.sky.courseWorkTwo.model.Question;

import java.util.*;
@Service
public class JavaQuestionService implements QuestionService{

    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionAlreadyExistsException();
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
    public Collection<Question> getAll() {
        return Collections.unmodifiableCollection(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new QuestionsAreEmptyException();
        }
        return new ArrayList<>(questions).get(random.nextInt(questions.size()));
    }

}
