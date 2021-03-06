package inescid.dataaggregation.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;

import inescid.dataaggregation.dataset.Global;
import inescid.util.RdfUtil;

public class ScriptCreateRdfRegOfNamespace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		http://www.wikidata.org/entity.json
		try { 
			Global.init_developement();
			String nsLocation = null;
//			String name="Rdfs";
//			String ns = "http://www.w3.org/2000/01/rdf-schema#";
//			String name="Rdf";
//			String ns = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
//			String name="Edm";
//			String ns = "http://www.europeana.eu/schemas/edm/";
//			String name="Owl";
//			String ns = "http://www.w3.org/2002/07/owl#";
//			String name="Skos";
//			String ns = "http://www.w3.org/2004/02/skos/core#";
			
//			String makeRequestForContent = HttpUtil.makeRequestForContent(ns);
			String name="Schemaorg";
			String ns = "http://schema.org/";
			nsLocation = "http://schema.org/version/latest/schema.rdf";
			
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream o = new PrintStream(os);
			o.printf("package inescid.dataaggregation.data;\r\n" + 
					"\r\n" + 
					"import org.apache.jena.rdf.model.Property;\r\n" + 
					"import org.apache.jena.rdf.model.RDFNode;\r\n" + 
					"import org.apache.jena.rdf.model.Resource;\r\n" + 
					"import org.apache.jena.rdf.model.ResourceFactory;\r\n" + 
					"\r\n" + 
					"public final class RdfReg%s {\r\n" + 
					"	public static String PREFIX=\"%s\";\r\n" +
					"	public static String NS=\"%s\";\r\n\r\n", name, name.toLowerCase(), ns);
			
			Model rdf = null;
			if(nsLocation==null)
				rdf = RdfUtil.readRdfFromUri(ns);
			else
				rdf = RdfUtil.readRdfFromUri(nsLocation);
			for (ResIterator listSubjects = rdf.listSubjects() ; listSubjects.hasNext() ; ) {
				Resource r = listSubjects.nextResource();
				String uri = r.getURI();
				if(uri!=null && uri.startsWith(ns) && !uri.equals(ns)) {
					String elName=uri.substring(ns.length());
					if(Character.isUpperCase(elName.charAt(0)))
						o.printf("	public static final Resource %s = ResourceFactory.createResource(\"%s\");\r\n", elName, uri); 
					else
						o.printf("	public static final Property %s = ResourceFactory.createProperty(\"%s\");\r\n", elName, uri); 
				}
			}
			o.print("}");
			System.out.println(new String(os.toByteArray(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Global.shutdown();
	}

}
