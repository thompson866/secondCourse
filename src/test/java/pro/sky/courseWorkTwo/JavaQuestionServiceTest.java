package pro.sky.courseWorkTwo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.courseWorkTwo.exception.QuestionAlreadyExistsException;
import pro.sky.courseWorkTwo.exception.QuestionNotFoundException;
import pro.sky.courseWorkTwo.exception.QuestionsAreEmptyException;
import pro.sky.courseWorkTwo.model.Question;
import pro.sky.courseWorkTwo.service.JavaQuestionService;
import pro.sky.courseWorkTwo.service.QuestionService;


import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

  private final QuestionService questionService = new JavaQuestionService();

  @BeforeEach
  public void beforeEach() {
    questionService.add(new Question("вопрос1", "ответ1"));
    questionService.add(new Question("вопрос2", "ответ2"));
    questionService.add(new Question("вопрос3", "ответ3"));
  }

  @AfterEach
  public void afterEach() {
    new HashSet<>(questionService.getAll()).forEach(questionService::remove);
  }

  @Test
  public void add1Test() {
    int beforeCount = questionService.getAll().size();
    Question question = new Question("вопрос4", "ответ4");

    assertThat(questionService.add(question))
        .isEqualTo(question)
        .isIn(questionService.getAll());
    assertThat(questionService.getAll()).hasSize(beforeCount + 1);
  }

  @Test
  public void add2Test() {
    int beforeCount = questionService.getAll().size();
    Question question = new Question("вопрос4", "ответ4");

    assertThat(questionService.add("вопрос4", "ответ4"))
        .isEqualTo(question)
        .isIn(questionService.getAll());
    assertThat(questionService.getAll()).hasSize(beforeCount + 1);
  }

  @Test
  public void add1NegativeTest() {
    Question question = new Question("вопрос1", "ответ1");

    assertThatExceptionOfType(QuestionAlreadyExistsException.class)
        .isThrownBy(() -> questionService.add(question));
  }

  @Test
  public void add2NegativeTest() {
    assertThatExceptionOfType(QuestionAlreadyExistsException.class)
        .isThrownBy(() -> questionService.add("вопрос1", "ответ1"));
  }

  @Test
  public void removeTest() {
    int beforeCount = questionService.getAll().size();
    Question question = new Question("вопрос2", "ответ2");

    assertThat(questionService.remove(question))
        .isEqualTo(question)
        .isNotIn(questionService.getAll());
    assertThat(questionService.getAll()).hasSize(beforeCount - 1);
  }

  @Test
  public void removeNegativeTest() {
    assertThatExceptionOfType(QuestionNotFoundException.class)
        .isThrownBy(() -> questionService.remove(new Question("вопрос4", "ответ4")));
  }

  @Test
  public void getAllTest() {
    assertThat(questionService.getAll())
        .hasSize(3)
        .containsExactlyInAnyOrder(
            new Question("вопрос1", "ответ1"),
            new Question("вопрос3", "ответ3"),
            new Question("вопрос2", "ответ2")
        );
  }

  @Test
  public void getRandomQuestionTest() {
    assertThat(questionService.getRandomQuestion())
        .isNotNull()
        .isIn(questionService.getAll());
  }

  @Test
  public void getRandomQuestionNegativeTest() {
    afterEach();

    assertThatExceptionOfType(QuestionsAreEmptyException.class)
        .isThrownBy(questionService::getRandomQuestion);
  }

}
