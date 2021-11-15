package com.hajdbc.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hajdbc.demo.model.Student;

@Component
public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student value = new Student();
		value.setId(rs.getLong("ID"));
		value.setName(rs.getString("NAME"));
		value.setEmail(rs.getString("EMAIL"));
		return value;
	}
}