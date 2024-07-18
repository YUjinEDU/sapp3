package com.ll.sapp.answer;

import com.ll.sapp.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findAllByQuestion(Question question, Pageable pageable);

    @Query("SELECT a "
            + "FROM Answer a "
            + "WHERE a.question = :question "
            + "ORDER BY SIZE(a.voter) DESC, a.createDate")
    Page<Answer> findAllByQuestionOrderByVoter(Question question, Pageable pageable);
}