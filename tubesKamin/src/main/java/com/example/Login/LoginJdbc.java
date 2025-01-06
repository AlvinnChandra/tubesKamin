package com.example.Login;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginJdbc implements LoginRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<LoginData> findUser(String nama){
        String sql = "SELECT * FROM users WHERE nama = ?";
        List<LoginData> users = jdbcTemplate.query(
            sql, 
            ps -> ps.setString(1, nama), 
            new BeanPropertyRowMapper<>(LoginData.class)
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}
