package prise_en_main;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MainTest {

	public static void main(String[] args) 
	{
		Model m = ModelFactory.createDefaultModel();
		String NS = "http://example.com/test/";
		
		Resource r = m.createResource( NS + "r" );
		Property p = m.createProperty( NS + "p" );
		
		r.addProperty( p, "Hello world", XSDDatatype.XSDstring );
		
		m.write(System.out, "Turtle");
	}

}
