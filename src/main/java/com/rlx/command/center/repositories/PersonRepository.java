package com.rlx.command.center.repositories;

import java.util.List;
import com.rlx.command.center.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
  @Query("select p from Person p")
  public List<Person> getAll();
}
