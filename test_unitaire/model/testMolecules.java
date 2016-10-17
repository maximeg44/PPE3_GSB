package model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMolecules {
	private Molecule maMolecule;
	private ArrayList<Molecule>molecules;

	@Before
	public void setUp() throws Exception {
		maMolecule = new Molecule ("abacavir");
		molecules = new ArrayList<Molecule>();
		molecules.add(maMolecule);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getLibelle() {		
		assertEquals("abacavir", maMolecule.getLibelle());
	}
	
	@Test
	public void test_getMoleculeById() throws SQLException{
		assertEquals(Molecule.getMoleculeById(1), maMolecule);
	}
	@Test
	public void test_getMoleculesByLibelle(){
		assertEquals(Molecule.getMoleculesByLibelle("abacavir"), maMolecule);
	}	
}
