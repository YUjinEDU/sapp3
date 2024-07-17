package com.ll.sapp.question;

import com.ll.sapp.DataNotFoundException;
import com.ll.sapp.user.SiteUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(user);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseViewCount(Integer id) {
        Question question = this.getQuestion(id);
        question.setViewCount(question.getViewCount() + 1);
        this.questionRepository.save(question);
        entityManager.flush(); // 데이터베이스에 즉시 반영
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        question.setVoteCount(question.getVoter().size());  // 추천수를 업데이트
        this.questionRepository.save(question);
        entityManager.flush(); // 데이터베이스에 즉시 반영
    }



}