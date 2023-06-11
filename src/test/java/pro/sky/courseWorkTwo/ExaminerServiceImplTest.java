package pro.sky.courseWorkTwo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.courseWorkTwo.exception.IncorrectAmountOfQuestions;
import pro.sky.courseWorkTwo.model.Question;
import pro.sky.courseWorkTwo.service.ExaminerServiceImpl;
import pro.sky.courseWorkTwo.service.QuestionService;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

  @Mock
  private QuestionService questionService;

  @InjectMocks
  private ExaminerServiceImpl examinerService;

  private final Collection<Question> questions = Set.of(
      new Question("вопрос1", "ответ1"),
      new Question("вопрос2", "ответ2"),
      new Question("вопрос3", "ответ3"),
      new Question("вопрос4", "ответ4"),
      new Question("вопрос5", "ответ5")
  );

  @Test
  public void getQuestionsNegativeTest() {
    when(questionService.getAll()).thenReturn(questions);

    assertThatExceptionOfType(IncorrectAmountOfQuestions.class)
        .isThrownBy(() -> examinerService.getQuestions(-1));
    assertThatExceptionOfType(IncorrectAmountOfQuestions.class)
        .isThrownBy(() -> examinerService.getQuestions(questions.size() + 1));
  }

  @Test
  public void getQuestionsTest() {
    when(questionService.getAll()).thenReturn(questions);

    when(questionService.getRandomQuestion()).thenReturn(
        new Question("вопрос4", "ответ4"),
        new Question("вопрос4", "ответ4"),
        new Question("вопрос5", "ответ5"),
        new Question("вопрос2", "ответ2")
    );

    assertThat(examinerService.getQuestions(3))
        .hasSize(3)
        .containsExactlyInAnyOrder(
            new Question("вопрос4", "ответ4"),
            new Question("вопрос2", "ответ2"),
            new Question("вопрос5", "ответ5")
        );
  }

}
