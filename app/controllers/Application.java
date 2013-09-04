package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import java.util.Map;
import java.util.HashMap;

import helpers.SolrHelper;

public class Application extends Controller {
  
    public static Result index()  throws Exception {
    	Map collections = SolrHelper.getCollectionHash();
        return ok(search.render(collections));
    }


  
}
