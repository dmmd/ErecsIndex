package helpers;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import models.Resource;

public class SolrHelper{
	private static HttpSolrServer solr = new HttpSolrServer("http://localhost:8983/solr/eri");

	public static Map<String, String> getCollectionHash() throws SolrServerException{
    	Map<String, String> collections = new HashMap<>();
		SolrQuery query = new SolrQuery().setQuery("*:*").setFacet(true).addFacetField("colId");
        QueryResponse rsp = solr.query(query);
        SolrDocumentList sdl = rsp.getResults();
        for(FacetField facet : rsp.getFacetFields()){
            for(FacetField.Count c: facet.getValues()){
				collections.put(c.getName(), c.getName());
			}
		}

		for(Map.Entry e: collections.entrySet()){
			query = new SolrQuery().setQuery("colId:" + e.getKey()).setRows(1).addField("colName");
			rsp = solr.query(query);
        	sdl = rsp.getResults();
        	for(SolrDocument doc: sdl){
        		collections.put(e.getKey().toString(), doc.getFieldValue("colName").toString());
        	}
		}

    	return collections;
    }

    public static Resource loadResource(Resource resource) throws SolrServerException {
    	SolrQuery query = new SolrQuery().setQuery("colId:" + resource.colId).setRows(1).addField("colName");
		QueryResponse rsp = solr.query(query);
        SolrDocumentList sdl = rsp.getResults();
        SolrDocument doc = sdl.get(0);

        resource.colName = doc.getFieldValue("colName").toString();
        getSets(resource);
    	return resource;
    }

    private static void getSets(Resource resource) throws SolrServerException{
    	SolrQuery query = new SolrQuery().setQuery("colId:" + resource.colId).setRows(5000).addField("localIdentifier").addField("componentTitle").addField("diskId").addField("fileType");
		QueryResponse rsp = solr.query(query);
        SolrDocumentList sdl = rsp.getResults();
        for(SolrDocument doc: sdl){
        	resource.archivalObjects.add(doc.getFieldValue("localIdentifier").toString()  + ". " + doc.getFieldValue("componentTitle").toString() );
        	resource.mediaObjects.add(doc.getFieldValue("diskId").toString());
        	resource.fileFormats.add(doc.getFieldValue("fileType").toString());
        }	
    }
	
}