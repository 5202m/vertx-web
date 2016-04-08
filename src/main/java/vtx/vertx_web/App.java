package vtx.vertx_web;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.ext.web.templ.JadeTemplateEngine;
import vtx.handler.api.BooksApi;
import vtx.handler.api.IndexApi;

/**
 * Hello world!
 *
 */
public class App extends AbstractVerticle
{
	private HttpServer server;
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;
	
	public static final int WEB_PORT = 8081;
	
	private int port = 8081;
	
	private List<String> deploymentIds;
	
	private IndexApi indexApi;
	private BooksApi booksApi;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Runner.runExample2(App.class);
        //Launcher.main(new String[] { "run", App.class.getName() });
    }
    
    @Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
		deploymentIds = new ArrayList<String>(3);
	}

    @Override
    public void start(Future<Void> future) throws Exception {
    	//final JadeTemplateEngine engine = JadeTemplateEngine.create();
    	final MongoClient mongo = MongoClient.createShared(vertx, mongoConfig());
        final Router router = Router.router(vertx);
    	this.indexApi = new IndexApi();
    	this.booksApi = new BooksApi(mongo);
    	deployWebServer(future);
    	//this.indexApi = new IndexApi();
        /*router.get("/").handler(ctx -> {
          // we define a hardcoded title for our application
          ctx.put("name", "Vert.x Web");

          // and now delegate to the engine to render it.
          engine.render(ctx, "templates/index.jade", res -> {
            if (res.succeeded()) {
              ctx.response().end(res.result());
            } else {
              ctx.fail(res.cause());
            }
          });
        });

        // start a HTTP web server on port 8080
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);*/
    }
    
	private void deployWebServer(Future<Void> future) {
		System.out.println("web server begin to start....");
		/*JsonObject dbConfig = new JsonObject();
		dbConfig.put("mongo", mongoConfig());
		DeploymentOptions webserverOptions = new DeploymentOptions();
		webserverOptions.setConfig(dbConfig);*/
		server = vertx.createHttpServer(createOptions());
		server.requestHandler(createRouter()::accept);
		server.listen(result -> {
			if (result.succeeded()) {
				future.complete();
			} else {
				future.fail(result.cause());
			}
		});
		
		System.out.println("Point your browser to: http://localhost:" + port);
	}
	
	private HttpServerOptions createOptions() {
		HttpServerOptions options = new HttpServerOptions();
		//Optional.ofNullable(System.getenv("PORT")).ifPresent(it -> port = Integer.parseInt(it));
		options.setHost("localhost");
		options.setPort(WEB_PORT);
		return options;
	}
	
	private Router createRouter() {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.route().failureHandler(ErrorHandler.create(true));

		/* Session / cookies for users */
		/*router.route().handler(CookieHandler.create());
		SessionStore sessionStore = LocalSessionStore.create(vertx);
		SessionHandler sessionHandler = SessionHandler.create(sessionStore);
		router.route().handler(sessionHandler);*/
		//userContextHandler = new UserContextHandler(mongo);
		//apiRouter();
		/* Dynamic pages */
		//dynamicPages(router);

		/* API */
		router.mountSubRouter("/", apiRouter());
		
		//router.route("/eventbus/*").handler(eventBusHandler());
		//router.route("/gts2event/*").handler(eventBusHandler());
		/* 页面上加载资源文件 */
		router.route().handler(StaticHandler.create());

		/* Static resources */
		//staticHandler(router);

		return router;
	}
	

	private static Router staticHandler(Router router) {
		//System.out.println("in static handler");
		StaticHandler web = StaticHandler.create("web").setFilesReadOnly(true).setDirectoryListing(false);
		//System.out.println(web);
		router.route("/").handler(rc -> {
			if (rc.request().uri().equals("/")) {
				String uri = rc.request().absoluteURI();
				//System.out.println(uri);
				rc.response().setStatusCode(301).putHeader("Location", uri).end();
			} else {
				rc.next();
			}
		});
		
		web.setCachingEnabled(false);
		router.route("/web/*").handler(web);
		//System.out.println(router);
		return router;
	}
	
	private Router dynamicPages(Router router) {
		JadeTemplateEngine jadeEngine = JadeTemplateEngine.create();
		jadeEngine.setMaxCacheSize(0); /* no cache since we wan't hot-reload for templates */
		TemplateHandler templateHandler = TemplateHandler.create(jadeEngine);
		//router.get("templates/*").handler();
		router.getWithRegex(".+\\.jade").handler(context -> {
			/*final Session session = context.session();
			context.data().put("userLogin", session.get("login")); *//* in order to greet him */
			//context.data().put("accessToken", session.get("accessToken")); /* for api calls */
			context.next();
		});
		router.getWithRegex(".+\\.jade").handler(templateHandler);
		return router;
	}

	private Router apiRouter() {
		Router  router = Router.router(vertx);
		
		/* login / user-related stuff : no token needed */

		router.get("/").handler(indexApi::index);
		router.get("/hbs").handler(indexApi::hbs);
		router.get("/thy").handler(indexApi::thy);
		
		router.get("/books").handler(booksApi::index);
		router.get("/booksList").handler(booksApi::books);
		
		System.out.println( "In apiRouter!" );
		return router;
	}
	
	private static JsonObject mongoConfig() {
		JsonObject config = new JsonObject();
		config.put("host", MONGO_HOST);
		config.put("port", MONGO_PORT);
		config.put("db_name", "keystone");
		return config;
	}

}
