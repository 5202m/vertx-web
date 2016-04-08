package vtx.handler.api;

import java.util.LinkedHashMap;
import java.util.Map;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import vtx.util.TemplateEngineUtil;
import vtx.vert_dao.MongoDAO;

public class BooksApi {

	private static MongoClient mongo;
	//MongoDAO mongoDao;
	public BooksApi(MongoClient mongo){
		this.mongo = mongo;
		//this.mongoDao = new MongDAO(mongo);
	}
	
	public void index(RoutingContext context){
		context.put("title", "Books");
		TemplateEngineUtil.JadeEngine(context, "webapp/templates/books/index.jade"); 
	}
	
	public void books(RoutingContext context){
		MongoDAO mongoDao = new MongoDAO(mongo);
		mongoDao.findAll("books", new JsonObject(), result -> {
			if (result.failed()) {
				context.fail(result.cause());
			} else {
				final JsonArray json = new JsonArray();
				for (JsonObject o : result.result()) {
					json.add(o);
				}
				context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		        // encode to json string
				context.response().end(json.encode());
			}
		});
	}
}
