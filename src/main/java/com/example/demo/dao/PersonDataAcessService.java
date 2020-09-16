package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAcessService implements PersonDao{

    final private JdbcTemplate jdbcTemplate;

    public PersonDataAcessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        String sqlQuery =  "INSERT INTO person(id, name) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, new Object[]{id, person.getName()});
        return 0;
    }

    @Override
    public List<Person> getAllPeople()
    {
        String sqlQuery =  "SELECT id, name FROM person";
        List<Person> people = jdbcTemplate.query(sqlQuery,(resultSet, i) -> {
            return new Person(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("name"));
        });
        return people;
    }

    @Override
    public Optional<Person> getPersonById(UUID id)
    {
        String sqlQuery =  "SELECT id, name FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(sqlQuery, new Object[] {id}, (resultSet, i) -> {
            return new Person(
                    UUID.fromString(resultSet.getString("id")),
                    resultSet.getString("name"));
        });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        String sqlQuery =  "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{id});
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person)
    {
        String sqlQuery =  "UPDATE person SET name = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{person.getName(), id});
        return 0;
    }
}
