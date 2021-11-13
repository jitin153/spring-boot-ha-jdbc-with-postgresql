package com.hajdbc.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hajdbc.demo.model.Student;
import com.hajdbc.demo.rowmapper.StudentRowMapper;

@RestController
public class StudentController {

	private static final String FIND_BY_ID = "select * from students where id = ?";
	private static final String FIND_ALL = "select * from students";
	private static final String INSERT = "INSERT INTO students (name, email) VALUES ('#NAME','#EMAIL')";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StudentRowMapper studentRowMapper;

	@PostMapping("/student")
	public int save(@RequestBody Student student) {
		return jdbcTemplate.update(buildQuery(student));
	}

	@GetMapping("/student")
	public List<Student> findAll() {
		List<Student> value = jdbcTemplate.query(FIND_ALL, studentRowMapper);
		return value;
	}

	@GetMapping("/student/{id}")
	public Student findById(@PathVariable("id") Long id) {
		// Student value = (Student)jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, new BeanPropertyRowMapper(Student.class));
		Student value = jdbcTemplate.queryForObject(FIND_BY_ID, new Object[] { id }, studentRowMapper);
		return value;
	}

	private static String buildQuery(Student student) {
		return INSERT.replace("#NAME", student.getName()).replace("#EMAIL", student.getEmail());
	}
}
