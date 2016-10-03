package model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMedicine {
	private Medicine medicament;
	private Form form;
	private Molecule mol;
	private GregorianCalendar date;
	private ArrayList<Medicine> mesMedocs;

	@Before
	public void setUp() throws Exception {
		form = new Form (1, "solution");
		mol = new Molecule(1,"libelleMol");
		date = new GregorianCalendar(2000, 0, 0);
		medicament = new Medicine ("medoc", form, date, mol);
		mesMedocs = new ArrayList<Medicine>();
		mesMedocs.add(medicament);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test_getName() {
		assertEquals("medoc", medicament.getName());
	}
	@Test
	public void test_getItsForm(){
		assertEquals(form, medicament.getItsForm());
	}
	@Test
	public void test_getItsMolecule(){
		assertEquals(mol, medicament.getItsMolecule());
	}
	@Test
	public void test_getPatentDate(){
		assertEquals(date, medicament.getPatentDate());
	}
	@Test
	public void test_getMedicineByName(){
		assertEquals(Medicine.getMedicineByName("medoc"), medicament);
	}
	@Test
	public void test_getMedicineById() throws SQLException{
		assertEquals(Medicine.getMedicineById(1), medicament);
	}
	@Test
	public void test_setItsForm(){
		Form form2 = new Form (2, "Poudre");
		medicament.setItsForm(form2);
		assertNotEquals(medicament.getItsForm(), form);
	}
	@Test
	public void test_setPatentDate(){
		GregorianCalendar date2 = new GregorianCalendar(1990, 0, 0);
		medicament.setPatentDate(date2);
		assertNotEquals(medicament.getPatentDate(), date);
	}
}
