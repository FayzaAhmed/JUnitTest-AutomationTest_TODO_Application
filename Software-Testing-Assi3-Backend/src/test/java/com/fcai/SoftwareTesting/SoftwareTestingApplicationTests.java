package com.fcai.SoftwareTesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SoftwareTestingApplicationTests {

    @Test
    public void contextLoads() 
	{
        // Test that the application context loads without errors
        ConfigurableApplicationContext context = SpringApplication.run(SoftwareTestingApplication.class);
        assertNotNull(context);
        context.close();
    }

	/*************************************************Testing TodoServiceImpl Class******************************************************/
	@Test
	public void TodoServiceImplTestConstructor()
	{
		TodoServiceImpl todoServiseImplObj = new TodoServiceImpl();
		assertNotNull(todoServiseImplObj);
	}


	//Testing create() in the class TodoServiceImpl
    @Test
    public void TodoServiceImplTestCreateValidTodo() //Happy senario :)
	{
        TodoCreateRequest todoRequest = new TodoCreateRequest("Title", "Description");
        TodoServiceImpl todoService = new TodoServiceImpl();
        Todo todo = todoService.create(todoRequest);
        assertNotNull(todo.getId());
    }

    @Test 
    public void TodoServiceImplTestCreateNullTodo() 
	{
        TodoCreateRequest todoRequest = null;
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.create(todoRequest));
    }

    @Test
    public void TodoServiceImplTestCreateEmptyTitle() 
	{
        TodoCreateRequest todoRequest = new TodoCreateRequest("", "Description");
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.create(todoRequest));
    }

    @Test
    public void TodoServiceImplTestCreateEmptyDescription() 
	{
        TodoCreateRequest todoRequest = new TodoCreateRequest("Title", "");
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.create(todoRequest));
    }
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Testing read() in the class TodoServiceImpl
    @Test
    public void TodoServiceImplTestReadValidTodo() //Happy senario :)
	{
        Todo todo = new Todo("1", "Title", "Description", false);
        TodoServiceImpl todoService = new TodoServiceImpl();
        todoService.getTodos().add(todo);
        Todo resultTodo = todoService.read("1");
        assertEquals(todo, resultTodo);
    }

    @Test
    public void TodoServiceImplTestReadNullId() 
	{
        String id = null;
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.read(id));
    }

    @Test
    public void TodoServiceImplTestReadEmptyId() 
	{
        String id = "";
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.read(id));
    }

    @Test
    public void TodoServiceImplTestReadInvalidTodo() {
		TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.read("2"));
    }
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Testing update() in the class TodoServiceImpl
	@Test
    public void TodoServiceImplTestUpdateValidTodo() 
	{
		Todo todo = new Todo("1", "Title", "Description", false);
		TodoServiceImpl todoService = new TodoServiceImpl();
		todoService.getTodos().add(todo);
		Todo updatedTodo = todoService.update("1", true);
		assertTrue(updatedTodo.isCompleted());
	}
	
	/*
	@Test
    public void TodoServiceImplTestUpdateValidTodoMock() {
		Todo todo = new Todo("1", "Title", "Description", false);
        TodoServiceImpl todoService = mock(TodoServiceImpl.class);
        when(todoService.read("1")).thenReturn(todo);
        Todo updatedTodo = todoService.update("1", true);
        assertTrue(updatedTodo.isCompleted());
    }*/
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Testing delete() in the class TodoServiceImpl
	@Test
    public void TodoServiceImplTestDeleteValidTodo() 
	{
		Todo todo = new Todo("1", "Title", "Description", false);
		TodoServiceImpl todoService = new TodoServiceImpl();
		todoService.getTodos().add(todo);
		int sizeBeforeDelete = todoService.getTodos().size();
		todoService.delete("1");
		int sizeAfterDelete = todoService.getTodos().size();
		assertEquals(sizeBeforeDelete, sizeAfterDelete + 1);
		//assertTrue(todoService.getTodos().isEmpty());
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Testing list() in the class TodoServiceImpl
	@Test
    public void TodoServiceImplTestListValidTodos() 
	{
        Todo todo1 = new Todo("1", "Title 1", "Description 1", false);
        Todo todo2 = new Todo("2", "Title 2", "Description 2", false);

        List<Todo> todosTemp = new ArrayList<>();
        todosTemp.add(todo1);
        todosTemp.add(todo2);

        TodoServiceImpl todoService = new TodoServiceImpl();
		todoService.getTodos().add(todo1);
		todoService.getTodos().add(todo2);
        List<Todo> resultTodos = todoService.list();
        assertEquals(todosTemp, resultTodos);
    }

    @Test
    public void TodoServiceImplTestListNullTodos() //Always fails because of the unreachable area
	{
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.list());
    }
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Testing listComplete() in the class TodoServiceImpl
	@Test
    public void TodoServiceImplTestListCompletedValidTodos() {
        Todo todo1 = new Todo("1", "Title 1", "Description 1", false);
        Todo todo2 = new Todo("2", "Title 2", "Description 2", true);
		
        TodoServiceImpl todoService = new TodoServiceImpl();
        todoService.getTodos().add(todo1);
		todoService.getTodos().add(todo2);
        List<Todo> resultTodos = todoService.listCompleted();
        assertEquals(1, resultTodos.size());
        assertEquals(todo2, resultTodos.get(0));
    }

    @Test
    public void TodoServiceImplTestListCompletedNullTodos() //Always fails because of the unreachable area
	{
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.listCompleted());
    }
	
}