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
		Form form2 = new Form (2,"anOtherForm");
		assertEquals(Form.getFormById(2), form2);
	}
	@Test
	public void test_getFormByName(){
		assertEquals(Form.getFormByName("maForme"), form);
	}
	@Test
	public void test_addAlltheForms(){
		int nbrForm = Form.allTheForms.size();
		Form form3 = new Form (3,"anOtherForm");
		nbrForm += 1;
		//mesFormes.add(form2);
		assertEquals(nbrForm, Form.allTheForms.size());
	}
	@Test
	public void test_addAlltheFromsNtimes(){
		int nbrForm = Form.allTheForms.size();
		Form form4 = new Form (1,"maForme");
		assertEquals(nbrForm, Form.allTheForms.size());
	}
}
