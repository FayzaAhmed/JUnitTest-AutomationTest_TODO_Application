package com.fcai.SoftwareTesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


	//Testing create() in the class TodoServiceImplTest
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

	//Testing read() in the class TodoServiceImplTest
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


}