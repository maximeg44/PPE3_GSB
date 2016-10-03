package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMolecules {
	private Molecule maMolecule;
	private ArrayList<Molecule>molecules;

	@Before
	public void setUp() throws Exception {
		maMolecule = new Molecule (1, "nomMol");
		molecules = new ArrayList<Molecule>();
		molecules.add(maMolecule);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getLibelle() {		
		assertEquals("nomMol", maMolecule.getLibelle());
	}
	@Test
	public void test_getId(){
		assertEquals(1,maMolecule.getId());
	}
	@Test
	public void test_getMoleculeById(){
		assertEquals(Molecule.getMoleculeById(1), maMolecule);
	}
	@Test
	public void test_getMoleculesByLibelle(){
		assertEquals(Molecule.getMoleculesByLibelle("nomMol"), maMolecule);
	}	
}
