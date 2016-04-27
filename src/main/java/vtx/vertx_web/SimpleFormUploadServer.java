package vtx.vertx_web;

import io.vertx.core.AbstractVerticle;
import vtx.util.TemplateEngineUtil;

public class SimpleFormUploadServer extends AbstractVerticle {

	  // Convenience method so you can run it in your IDE
	  public static void main(String[] args) {
	    Runner.runExample(SimpleFormUploadServer.class);
	  }

	  @Override
	  public void start() throws Exception {
	    vertx.createHttpServer().requestHandler(req -> {
	      if (req.uri().equals("/")) {
	        // Serve the index page
	    	//TemplateEngineUtil.JadeEngine(context, "webapp/templates/files/index.jade"); 
	        //req.response().sendFile("index.html");
	    	req.response().putHeader("content-type", "text/html").end("<form action=\"/form\" method=\"post\" enctype=\"multipart/form-data\">\n" +
	  	          "    <div>\n" +
		          "        <label for=\"name\">Select a file:</label>\n" +
		          "        <input type=\"file\" name=\"file\" />\n" +
		          "    </div>\n" +
		          "    <div class=\"button\">\n" +
		          "        <button type=\"submit\">Send</button>\n" +
		          "    </div>" +
		          "</form>");
	      } else if (req.uri().startsWith("/form")) {
	    	  String dir = System.getProperty("user.dir")+"\\src\\main\\file-uploads\\";
	        req.setExpectMultipart(true);
	        req.uploadHandler(upload -> {
	          upload.exceptionHandler(cause -> {
	            req.response().setChunked(true).end("Upload failed");
	          });

	          upload.endHandler(v -> {
	            req.response().setChunked(true).end("Successfully uploaded to " + upload.filename());
	          });
	          // FIXME - Potential security exploit! In a real system you must check this filename
	          // to make sure you're not saving to a place where you don't want!
	          // Or better still, just use Vert.x-Web which controls the upload area.
	          upload.streamToFileSystem(dir + upload.filename());
	          
	        });
	      } else {
	        req.response().setStatusCode(404);
	        req.response().end();
	      }
	    }).listen(8080);

	  }
	}
