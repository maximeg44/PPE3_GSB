package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testForm {

	private Form form;
	private ArrayList<Form> mesFormes;
	@Before
	public void setUp() throws Exception {
		form = new Form(1,"maForme");
		mesFormes = new ArrayList<Form>();
		mesFormes.add(form);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getId() {
		assertEquals(1,form.getId());		
	}
	@Test
	public void test_getName(){
		assertEquals("maForme", form.getName());
	}
	@Test
	public void test_getFormById(){
		assertEquals(Form.getFormById(1), form);
	}
	@Test
	public void test_getFormByName(){
		assertEquals(Form.getFormByName("maForme"), form);
	}
}
