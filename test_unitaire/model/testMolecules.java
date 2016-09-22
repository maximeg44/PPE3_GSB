package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMolecules {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getLibelle() {
		Molecules molecule = new Molecules (1, "nomMol");
		assertEquals("nomMol", molecule.getLibelle());
	}
	@Test
	public void test_getId(){
		Molecules molecule = new Molecules (1, "nomMol");
		assertEquals(1,molecule.getId());
	}
	@Test
	public void test_getMoleculeById(){
		Molecules molecule = new Molecules (1, "nomMol");
		int id=0;
		ArrayList<Molecules>molecules = new ArrayList<Molecules>();
		molecules.add(molecule);
		for (Molecules m : molecules){
			if(m.getId()==1)
				id=m.getId();
		}
		assertEquals(1,id);
	}
	@Test
	public void test_getMoleculesByLibelle(){
		Molecules molecule = new Molecules (1, "nomMol");
		ArrayList<Molecules>molecules = new ArrayList<Molecules>();
		molecules.add(molecule);
		for (Molecules m : molecules){
			assertEquals("nomMol", m.getLibelle());
		}
		
	}
	@Test
	public void test_addMyMedicament(){

	}
	
}
