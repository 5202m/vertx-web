package vtx.vert_dao;

import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoDAO {

	private static MongoClient mongo;
	/*private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;*/
	
	public MongoDAO(MongoClient mongo) {
		/*JsonObject dbConfig = new JsonObject();
		dbConfig.put("mongo", mongoConfig());
		DeploymentOptions webserverOptions = new DeploymentOptions();
		webserverOptions.setConfig(dbConfig);*/
		this.mongo = mongo;
	}

	/*private static JsonObject mongoConfig() {
		JsonObject config = new JsonObject();
		config.put("host", MONGO_HOST);
		config.put("port", MONGO_PORT);
		config.put("db_name", "keystone");
		return config;
	}*/
	
	public void findAll(String collection,JsonObject where, Handler<AsyncResult<List<JsonObject>>> handler) {
		//JsonArray json = new JsonArray();
		//System.out.println(collection);
		mongo.find(collection, where, handler);
		/*mongo.find(collection, new JsonObject(), lookup -> {
			//System.out.println("in find : "+lookup.failed());
			if (lookup.failed()) {
				//json.add(new JsonObject().put("fail", lookup.cause()));
			} else {
				//System.out.println(lookup.result().toString());
				for (JsonObject o : lookup.result()) {
					json.add(o);
				}
				//System.out.println(json.toString());
				//return json;
			}
		});*/
		//System.out.println("out find : "+json.toString());
		//return json;
	}
}
