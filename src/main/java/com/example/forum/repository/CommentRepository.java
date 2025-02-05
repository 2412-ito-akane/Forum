package com.example.forum.repository;

import com.example.forum.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //SELECT文でかつIDを降順に並べ替える
    public List<Comment> findAllByOrderByIdDesc();

}
