package pro.sky.courseWorkTwo.service;


import pro.sky.courseWorkTwo.model.Question;

import java.util.Collection;

public interface ExaminerService {

  Collection<Question> getQuestions(int amount);

}
