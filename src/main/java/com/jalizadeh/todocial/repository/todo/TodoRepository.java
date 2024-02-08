package com.jalizadeh.todocial.repository.todo;

import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findAllByUser(User user);

	List<Todo> findAllByUserIdAndIsPublicTrue(Long id);

	List<Todo> findAllByIsPublicTrue();
	
	@Query("select t from Todo t where t.user.id=:#{principal.id} and t.completed = true and t.canceled = false")
	List<Todo> getAllCompleted();
	
	@Query("select t from Todo t where t.user.id=:#{principal.id} and t.completed = false and t.canceled = false")
	List<Todo> getAllNotCompleted();
	
	@Query("select t from Todo t where t.user.id=:#{principal.id} and t.canceled = true")
	List<Todo> getAllCanceled();

	@Query("select t from Todo t where t.user.id=:#{principal.id} and (t.name LIKE %:query% OR t.description LIKE %:query% OR t.reason LIKE %:query%)")
	List<Todo> searchAllByLoggedinUser(@Param("query") String query);

}
