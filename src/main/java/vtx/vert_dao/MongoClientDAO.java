package vtx.vert_dao;

import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.UpdateOptions;
import io.vertx.ext.mongo.WriteOption;

public class MongoClientDAO {

	private static MongoClient mongo;
	/*private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;*/
	
	public MongoClientDAO(MongoClient mongo) {
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
	
	/**
	 * Save a document in the specified collection
	 * @param collection
	 * @param document
	 * @param resultHandler
	 */
	public void save(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler){
		mongo.save(collection, document, resultHandler);
	}
	
	/**
	 * Save a document in the specified collection with the specified write option
	 * @param collection
	 * @param document
	 * @param writeOption
	 * @param resultHandler
	 */
	public void saveWithOptions(String collection, JsonObject document, WriteOption writeOption, Handler<AsyncResult<String>> resultHandler){
		mongo.saveWithOptions(collection, document, writeOption, resultHandler);
	}
	
	/**
	 * Insert a document in the specified collection
	 * @param collection
	 * @param document
	 * @param resultHandler
	 */
	public void insert(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler){
		mongo.insert(collection, document, resultHandler);	
	}
	
	/**
	 * Insert a document in the specified collection with the specified write option
	 * @param collection
	 * @param document
	 * @param writeOption
	 * @param resultHandler
	 */
	public void insertWithOptions(String collection, JsonObject document, WriteOption writeOption, Handler<AsyncResult<String>> resultHandler){
		mongo.insertWithOptions(collection, document, writeOption, resultHandler);
	}
	
	/**
	 * Update matching documents in the specified collection
	 * @param collection
	 * @param query
	 * @param update
	 * @param resultHandler
	 */
	public void update(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<Void>> resultHandler){
		mongo.update(collection, query, update, resultHandler);
	}
	
	/**
	 * Update matching documents in the specified collection, specifying options
	 * @param collection
	 * @param query
	 * @param update
	 * @param options
	 * @param resultHandler
	 */
	public void updateWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler){
		mongo.updateWithOptions(collection, query, update, options, resultHandler);
	}
	
	/**
	 * Replace matching documents in the specified collection
	 * @param collection
	 * @param query
	 * @param replace
	 * @param resultHandler
	 */
	public void replace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<Void>> resultHandler){
		mongo.replace(collection, query, replace, resultHandler);
	}
	
	/**
	 * Replace matching documents in the specified collection, specifying options
	 * @param collection
	 * @param query
	 * @param replace
	 * @param options
	 * @param resultHandler
	 */
	public void replaceWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<Void>> resultHandler){
		mongo.replaceWithOptions(collection, query, replace, options, resultHandler);
	}
	
	/**
	 * Find matching documents in the specified collection
	 * @param collection
	 * @param query
	 * @param resultHandler
	 */
	public void find(String collection, JsonObject query, Handler<AsyncResult<List<JsonObject>>> resultHandler){
		mongo.find(collection, query, resultHandler);
	}
	
	/**
	 * Find matching documents in the specified collection. This method use batchCursor for returning each found document.
	 * @param collection
	 * @param query
	 * @param resultHandler
	 */
	public void findBatch(String collection, JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler) {
		mongo.findBatch(collection, query, resultHandler);
	}
	
	/**
	 * Find matching documents in the specified collection, specifying options
	 * @param collection
	 * @param query
	 * @param options
	 * @param resultHandler
	 */
	public void findWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler){
		mongo.findWithOptions(collection, query, options, resultHandler);
	}
	
	/**
	 * Find matching documents in the specified collection, specifying options. This method use batchCursor for returning each found document.
	 * @param collection
	 * @param query
	 * @param options
	 * @param resultHandler
	 */
	public void findBatchWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<JsonObject>> resultHandler){
		mongo.findBatchWithOptions(collection, query, options, resultHandler);
	}
	
	/**
	 * Find a single matching document in the specified collection
	 * @param collection
	 * @param query
	 * @param fields
	 * @param resultHandler
	 */
	public void findOne(String collection, JsonObject query, JsonObject fields, Handler<AsyncResult<JsonObject>> resultHandler){
		mongo.findOne(collection, query, fields, resultHandler);
	}
	
	/**
	 * Count matching documents in a collection.
	 * @param collection
	 * @param query
	 * @param resultHandler
	 */
	public void count(String collection, JsonObject query, Handler<AsyncResult<Long>> resultHandler){
		mongo.count(collection, query, resultHandler);
	}
	
	/**
	 * Remove matching documents from a collection
	 * @param collection
	 * @param query
	 * @param resultHandler
	 */
	public void remove(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler){
		mongo.remove(collection, query, resultHandler);
	}
	
	/**
	 * Remove matching documents from a collection with the specified write option
	 * @param collection
	 * @param query
	 * @param writeOption
	 * @param resultHandler
	 */
	public void removeWithOptions(String collection, JsonObject query, WriteOption writeOption,  Handler<AsyncResult<Void>> resultHandler){
		mongo.removeWithOptions(collection, query, writeOption, resultHandler);
	}
	
	/**
	 * Remove a single matching document from a collection
	 * @param collection
	 * @param query
	 * @param resultHandler
	 */
	public void removeOne(String collection, JsonObject query, Handler<AsyncResult<Void>> resultHandler){
		mongo.removeOne(collection, query, resultHandler);
	}
	
	/**
	 * Remove a single matching document from a collection with the specified write option
	 * @param collection
	 * @param query
	 * @param writeOption
	 * @param resultHandler
	 */
	public void removeOneWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<Void>> resultHandler){
		mongo.removeOneWithOptions(collection, query, writeOption, resultHandler);
	}
	
	/**
	 * Create a new collection
	 * @param collectionName
	 * @param resultHandler
	 */
	public void createCollection(String collectionName, Handler<AsyncResult<Void>> resultHandler){
		mongo.createCollection(collectionName, resultHandler);
	}
	
	/**
	 * Get a list of all collections in the database.
	 * @param resultHandler
	 */
	public void getCollections(Handler<AsyncResult<List<String>>> resultHandler){
		mongo.getCollections(resultHandler);
	}
	
	/**
	 * Drop a collection
	 * @param collection
	 * @param resultHandler
	 */
	public void dropCollection(String collection, Handler<AsyncResult<Void>> resultHandler){
		mongo.dropCollection(collection, resultHandler);
	}
	
	/**
	 * Run an arbitrary MongoDB command.
	 * @param commandName
	 * @param command
	 * @param resultHandler
	 */
	public void runCommand(String commandName, JsonObject command, Handler<AsyncResult<JsonObject>> resultHandler){
		mongo.runCommand(commandName, command, resultHandler);
	}
	
	/**
	 * Gets the distinct values of the specified field name. Return a JsonArray containing distinct values (eg: [ 1 , 89 ])
	 * @param collection
	 * @param fieldName
	 * @param resultClassname
	 * @param resultHandler
	 */
	public void distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> resultHandler){
		mongo.distinct(collection, fieldName, resultClassname, resultHandler);
	}
	
	/**
	 * Gets the distinct values of the specified field name. This method use batchCursor for returning each found value. Each value is a json fragment with fieldName key (eg: {"num": 1}).
	 * @param collection
	 * @param fieldName
	 * @param resultClassname
	 * @param resultHandler
	 */
	public void distinctBatch(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonObject>> resultHandler){
		mongo.distinctBatch(collection, fieldName, resultClassname, resultHandler);
	}
	
	/**
	 * Close the client and release its resources
	 */
	public void close(){
		mongo.close();
	}
}
