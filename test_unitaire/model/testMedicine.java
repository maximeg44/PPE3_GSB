package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMedicine {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getName() {
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		assertEquals("medoc", medicament.getName());
	}
	@Test
	public void test_getId(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		assertEquals(1, medicament.getId());
	}
	@Test
	public void test_getItsForm(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		assertEquals(form, medicament.getItsForm());
	}
	@Test
	public void test_getItsMolecule(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		assertEquals(mol, medicament.getItsMolecule());
	}
	@Test
	public void test_getPatentDate(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		assertEquals(date, medicament.getPatentDate());
	}
	@Test
	public void test_getMedicineByName(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		ArrayList<Medicine> mesMedocs = new ArrayList<Medicine>();
		mesMedocs.add(medicament);
		for(Medicine m : mesMedocs){
				assertEquals("medoc", m.getName());
		}
	}
	@Test
	public void test_getMedicineById(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		ArrayList<Medicine> mesMedocs = new ArrayList<Medicine>();
		mesMedocs.add(medicament);
		for(Medicine m : mesMedocs){
				assertEquals(1, m.getId());
		}
	}
	@Test
	public void test_setItsForm(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		Form form2 = new Form (1, "solution");
		medicament.setItsForm(form2);
		assertNotEquals(medicament.getItsForm(), form);
	}
	@Test
	public void test_setPatentDate(){
		GregorianCalendar date = new GregorianCalendar();
		Form form = new Form (1, "solution");
		Molecules mol = new Molecules(1,"libelleMol");
		Medicine medicament = new Medicine (1, "medoc", form, date, mol);
		GregorianCalendar date2 = new GregorianCalendar();
		medicament.setPatentDate(date2);
		assertNotEquals(medicament.getPatentDate(), date);
	}

}
