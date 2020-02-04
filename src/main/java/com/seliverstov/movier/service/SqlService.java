package com.seliverstov.movier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class SqlService {


    private final JdbcOperations jdbcOperations;

    @Autowired
    public SqlService(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    public  List<String> selectData(String sql) {
        List select = new ArrayList();
        try {
            select = jdbcOperations.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return select;
    }

    public void insertData(String sql) {
        try {
            jdbcOperations.execute(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData(String sql) {
        try {
            jdbcOperations.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sqlCheck(String sql) {
        List<String> operations = new ArrayList<String>(Arrays.asList("SELECT","select","Select","INSERT","insert", "Insert", "UPDATE", "update","Update"));
        if (operations.contains(sql)) return true;
        else return false;
    }

    public List countActorsGenres(int genreid){
        List count = jdbcOperations.queryForList("SELECT COUNT(*) c FROM actors_genres WHERE genre_idgenres = " + genreid);
        return count;
    }
    public List countMoviesGenres(int genreid){
        List count = jdbcOperations.queryForList("SELECT COUNT(*) c FROM movie_genres WHERE genres_idgenres = " + genreid);
        return count;
    }
    public List countProducersGenres(int genreid){
        List count = jdbcOperations.queryForList("SELECT COUNT(*) c FROM producers_genres WHERE genres_idgenres = " + genreid);
        return count;
    }

}
