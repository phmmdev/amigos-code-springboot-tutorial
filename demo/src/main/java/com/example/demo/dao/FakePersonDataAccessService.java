package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{
    private static List<Person> dataBase =  new ArrayList<Person>();

    @Override
    public int insertPerson(UUID id, Person person) {
        dataBase.add(new Person(id, person.getName()));
        return 0;
    }

    @Override
    public List<Person> getAllPeople() {
        return dataBase;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return dataBase.stream()
                       .filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        return dataBase.removeIf(x -> x.getId().equals(id)) ? 1 : 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return getPersonById(id)
                .map(x -> {
                                int indexOf = dataBase.indexOf(x);
                                if(indexOf >= 0)
                                {

                                    dataBase.set(indexOf, new Person(id, person.getName()));
                                    return 1;
                                }
                                return 0;
                })
                .orElse(null);
    }
}
