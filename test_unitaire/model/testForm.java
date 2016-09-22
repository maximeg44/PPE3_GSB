package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testForm {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getId() {
		Form form = new Form(1,"maForme");
		assertEquals(1,form.getId());		
	}
	@Test
	public void test_getName(){
		Form form = new Form(1,"maForme");
		assertEquals("maForme", form.getName());
	}
	@Test
	public void test_getFormById(){
		Form form = new Form(1,"maForme");
		ArrayList<Form> mesFormes = new ArrayList<Form>();
		mesFormes.add(form);
		for (Form f : mesFormes){
			assertEquals(1, f.getId());
		}
	}
	@Test
	public void test_getFormByName(){
		Form form = new Form(1,"maForme");
		ArrayList<Form> mesFormes = new ArrayList<Form>();
		mesFormes.add(form);
		for (Form f : mesFormes){
			assertEquals("maForme", f.getName());
		}
	}

}
