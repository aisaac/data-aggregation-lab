package inescid.dataaggregation.casestudies.wikidata.reasoning;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;

import eu.europeana.commonculture.lod.crawler.rdf.RdfRegRdfs;
import inescid.dataaggregation.casestudies.wikidata.RdfRegWikidata;
import inescid.dataaggregation.casestudies.wikidata.SparqlClientWikidata;
import inescid.dataaggregation.crawl.http.CachedHttpRequestService;
import inescid.dataaggregation.data.RdfReg;
import inescid.dataaggregation.data.RdfRegRdf;
import inescid.dataaggregation.data.reasoning.DataModelReasoner;
import inescid.dataaggregation.data.reasoning.ReasonerByRulesAndSchemas;
import inescid.dataaggregation.dataset.GlobalCore;
import inescid.util.RdfUtil;
import inescid.util.RdfUtil.Jena;

public class ScriptTestWikidataReasoner {
 	
	public static void main(String[] args) throws Exception {
//		String httpCacheFolder = "c://users/nfrei/desktop/data/HttpRepository";
//
//		GlobalCore.init_componentHttpRequestService();
//		GlobalCore.init_componentDataRepository(httpCacheFolder);
//
//		CachedHttpRequestService rdfCache = new CachedHttpRequestService();
//		rdfCache.setRequestRetryAttempts(1);
//		
//		String propUri = "http://www.wikidata.org/entity/P727";
////		Resource wdPropRes = WikidataRdfUtil.fetchresource(propUri, rdfCache);
//		
//		Model model=Jena.createModel();
//		
//		SparqlClientWikidata sparql=new SparqlClientWikidata();
//		SparqlClientWikidata.INSTANCE.createAllStatementsAboutAndReferingResource(propUri, model);
//		System.out.println(""
////		Jena.createStatementAddToModel(model, model.createResource(propUri), RdfRegRdf.type, RdfRegRdf.Property)
////				Jena.createStatementAddToModel(model, model.createResource(propUri), RdfRegRdfs.subClassOf, model.createResource("http://wikiba.se/ontology#Property"))
////				Jena.createStatementAddToModel(model, model.createResource(propUri), RdfRegRdf.type, RdfRegRdfs.Class)
//				+"\n"+Jena.createStatementAddToModel(model, model.createResource("http://wikiba.se/ontology#Property"), RdfRegRdfs.subClassOf, RdfRegRdf.Property)
////				+"\n"+Jena.createStatementAddToModel(model, model.createResource("http://wikiba.se/ontology#Property"), RdfRegRdf.type, RdfRegRdf.Property)
//				+"\n"+Jena.createStatementAddToModel(model, model.createResource("http://wikiba.se/ontology#Entity"), RdfRegRdfs.subClassOf, RdfRegRdfs.Class)
//				+"\n"+Jena.createStatementAddToModel(model, model.createResource("http://wikiba.se/ontology#Entity"), RdfRegRdfs.subClassOf, RdfReg.OWL_Thing)
//				+"\n"+Jena.createStatementAddToModel(model, RdfRegWikidata.EQUIVALENT_CLASS, RdfReg.OWL_EQUIVALENT_PROPERTY, RdfReg.OWL_EQUIVALENT_CLASS)
//				+"\n"+Jena.createStatementAddToModel(model, RdfRegWikidata.EQUIVALENT_PROPERTY, RdfReg.OWL_EQUIVALENT_PROPERTY, RdfReg.OWL_EQUIVALENT_PROPERTY)
////				+"\n"+Jena.createStatementAddToModel(model, model.createResource("http://wikiba.se/ontology#Entity"), RdfRegRdfs.subClassOf, RdfReg.OWL_Thing)
//				);
//		System.out.println( RdfUtil.statementsToString(model));
//		
//		System.out.println("[initing reasoner]");
////		DataModelReasoner reasoner=new DataModelReasoner();
////		OwlOntologyReasoner reasoner=new OwlOntologyReasoner("inescid/dataaggregation/data/reasoning/wikibase.owl");
////		OwlOntologyReasoner reasoner=new OwlOntologyReasoner("inescid/dataaggregation/data/reasoning/wikibase.owl", "inescid/dataaggregation/data/reasoning/owl_def.owl", "inescid/dataaggregation/data/reasoning/schemaorg.owl");
////		OwlOntologyReasoner reasoner=new OwlOntologyReasoner("inescid/dataaggregation/data/reasoning/wikibase.owl", "inescid/dataaggregation/data/reasoning/owl_def.owl", "inescid/dataaggregation/data/reasoning/schemaorg.owl", "inescid/dataaggregation/data/reasoning/edm_owl_crawled_model.owl");
//		ReasonerByRulesAndSchemas reasoner=new ReasonerByRulesAndSchemas();
//		reasoner.loadSchemas("inescid/dataaggregation/data/reasoning/wikibase.owl", "inescid/dataaggregation/data/reasoning/owl_def.owl", "inescid/dataaggregation/data/reasoning/schemaorg.owl", "inescid/dataaggregation/data/reasoning/edm.owl");
//		
//		System.out.println("[infering]");
//		InfModel inferedModel = reasoner.infer(model);
//
//		System.out.println("### INFERED ###");
////		System.out.println( RdfUtil.statementsToString(inferedModel.getDeductionsModel()));
//		
//		StringBuilder sb=new StringBuilder();
//		for(Statement st : inferedModel.getDeductionsModel().listStatements().toList()) {
////			System.err.println(st);
//			if(st.getSubject().getURI()!=null && st.getSubject().getURI().startsWith(propUri)
//					&& !(st.getPredicate().equals(RdfRegRdf.type) && st.getObject().isAnon())) 
////				if(st.getSubject().getURI()!=null && 
////				!st.getSubject().getURI().startsWith("http://www.w3.org/2002/07/owl") &&
////				!st.getSubject().getURI().startsWith("http://www.w3.org/2000/01/rdf-schema") &&
////				!st.getSubject().getURI().startsWith("http://www.w3.org/1999/02/22-rdf-syntax-ns"))
//			sb.append(st.toString()).append('\n');
//		}
//		System.out.println(sb.toString());
	}
	
	
}
