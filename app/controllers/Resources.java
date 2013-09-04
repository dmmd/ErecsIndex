package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

import helpers.SolrHelper;

import models.Resource;

public class Resources extends Controller{
	
	public static Result show(String colId) throws Exception {
		Resource res = new Resource();
		res.colId = colId;
		SolrHelper.loadResource(res);
		return ok(resource.render(res));
	}
}