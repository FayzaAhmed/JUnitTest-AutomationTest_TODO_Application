package com.fcai.SoftwareTesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.lang.IllegalArgumentException;

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
	public void TodoServiceImplConstructor()
	{
		TodoServiceImpl todoServiseImplObj = new TodoServiceImpl();
		assertNotNull(todoServiseImplObj);
	}


    @Test
    public void testCreateValidTodo() //Happy senario :)
	{
        TodoCreateRequest todoRequest = new TodoCreateRequest("Title", "Description");
        TodoServiceImpl todoService = new TodoServiceImpl();
        Todo todo = todoService.create(todoRequest);
        assertNotNull(todo.getId());
    }

    @Test 
    public void testCreateNullTodo() 
	{
        TodoCreateRequest todoRequest = null;
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.create(todoRequest));
    }

    @Test
    public void testCreateEmptyTitle() 
	{
        TodoCreateRequest todoRequest = new TodoCreateRequest("", "Description");
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.create(todoRequest));
    }

    @Test
    public void testCreateEmptyDescription() 
	{
        TodoCreateRequest todoRequest = new TodoCreateRequest("Title", "");
        TodoServiceImpl todoService = new TodoServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> todoService.create(todoRequest));
    }

	

}