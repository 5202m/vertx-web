package vtx.handler.api;

import io.vertx.ext.web.RoutingContext;
import vtx.util.TemplateEngineUtil;

public class IndexApi {

	public void index(RoutingContext context) {
		System.out.println("in the indexapi");
		context.put("name", "Vert.x Web");
		TemplateEngineUtil.JadeEngine(context, "webapp/templates/index.jade");
	}
	
	public void hbs(RoutingContext context){
		context.put("name", "Vert.x Web");
		TemplateEngineUtil.HandlebarsEngine(context, "webapp/templates/index.hbs");
	}
	
	public void thy(RoutingContext context){
		context.put("welcome", "Hi there!");
		TemplateEngineUtil.ThymeleafEngine(context, "webapp/templates/index.html");
	}

}
