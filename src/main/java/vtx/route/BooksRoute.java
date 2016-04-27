package vtx.route;

//import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import vtx.handler.api.BooksApi;

public class BooksRoute
{
	
	private BooksApi booksApi;
	
	public Router booksRoute(Router router, MongoClient client){
		Router route = router;
		booksApi = new BooksApi(client);
		
		route.get("/books").handler(booksApi::index);
		route.get("/booksList").handler(booksApi::books);
		
		return route;
	}
	
}
