package com.seliverstov.movier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SqlService {


    private final JdbcOperations jdbcOperations;

    @Autowired
    public SqlService(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    public  List<String> selectData(String sql) {
        List select = jdbcOperations.queryForList(sql);
        return select;
    }

    public void insertData(String sql) {
        jdbcOperations.execute(sql);
    }

    public void updateData(String sql) {
        jdbcOperations.execute(sql);
    }

    public boolean sqlCheck(String sql) {
        List<String> operations = new ArrayList<String>(Arrays.asList("SELECT","select","Select","INSERT","insert", "Insert", "UPDATE", "update","Update"));
        if (operations.contains(sql)) return true;
        else return false;
    }

}
