package com.example.day37server.repository;

public class Queries {

    public static final String SQL_INSERT_POST =
            "insert into posts values (?,?,?)";

    public static final String SQL_GET_POST =
            "select * from posts where post_id=?";
}
