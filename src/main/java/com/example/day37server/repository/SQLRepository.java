package com.example.day37server.repository;

import com.example.day37server.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.day37server.repository.Queries.*;

@Repository
public class SQLRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public String save(Post post) {

        String id = UUID.randomUUID().toString().substring(0,8);

        int result = jdbcTemplate.update(SQL_INSERT_POST,
                id,
                post.getComments(),
                post.getPicture());

        if (result >0)
            return id;


        else
            return "fail";
    }

    public Post get(String post_id) {

        return jdbcTemplate.queryForObject(
                SQL_GET_POST,
                (rs, rowNum) -> new Post(
                        rs.getString("post_id"),
                        rs.getString("comments"),
                        rs.getBytes("picture")
                ), post_id );

    }
}
