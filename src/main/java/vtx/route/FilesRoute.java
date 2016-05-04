package vtx.route;

//import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import vtx.handler.api.FilesApi;

public class FilesRoute
{
	
	private FilesApi filesApi;
	
	public Router filesRoute(Router router, MongoClient client){
		Router route = router;
		//filesApi = new FilesApi(client);
		
		route.get("/uploadIndex").handler(filesApi::uploadIndex);
		//route.get("/booksList").handler(filesApi::books);
		
		return route;
	}
	
}
