package vtx.vertx_web;

import java.io.File;


import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class UploadServer extends AbstractVerticle
{
	// Convenience method so you can run it in your IDE
	  public static void main(String[] args) {
	    Runner.runExample(UploadServer.class);
	  }

	  @Override
	  public void start() throws Exception {

	    Router router = Router.router(vertx);

	    String dir = System.getProperty("user.dir")+"\\src\\main\\file-uploads\\";
	    // Enable multipart form data parsing
	    //router.route().handler(BodyHandler.create().setUploadsDirectory(dir));
System.out.println(dir);
	    router.route("/").handler(routingContext -> {
	      routingContext.response().putHeader("content-type", "text/html").end(
	        "<form action=\"/form\" method=\"post\" enctype=\"multipart/form-data\">\n" +
	          "    <div>\n" +
	          "        <label for=\"name\">Select a file:</label>\n" +
	          "        <input type=\"file\" name=\"file\" />\n" +
	          "    </div>\n" +
	          "    <div class=\"button\">\n" +
	          "        <button type=\"submit\">Send</button>\n" +
	          "    </div>" +
	          "</form>"
	      );
	    });

	    // handle the form
	    router.post("/form").handler(ctx -> {
	    	ctx.request().setExpectMultipart(true);
	    	ctx.request().uploadHandler(upload -> {
	          upload.exceptionHandler(cause -> {
	        	  ctx.request().response().setChunked(true).end("Upload failed");
	          });

	          upload.endHandler(v -> {
	        	  ctx.request().response().setChunked(true).end("Successfully uploaded to " + upload.filename());
	          });
	          // FIXME - Potential security exploit! In a real system you must check this filename
	          // to make sure you're not saving to a place where you don't want!
	          // Or better still, just use Vert.x-Web which controls the upload area.
	          upload.streamToFileSystem(dir + upload.filename());
	          
	        });
	      /*ctx.response().putHeader("Content-Type", "text/plain");

	      ctx.response().setChunked(true);
	      
	      for (FileUpload f : ctx.fileUploads()) {
	        System.out.println("f");
	        ctx.response().write("Filename: " + f.fileName());
	        ctx.response().write("\n");
	        ctx.response().write("Size: " + f.size());
	        ctx.response().write("\n");
	        ctx.response().write("content: " + f.contentTransferEncoding().toString());
	        File imageFile = new File(dir + f.uploadedFileName());
	        File newFile = new File(dir + f.fileName());
	        imageFile.renameTo(newFile);
	        
	        System.out.println(f.uploadedFileName());
	      }

	      ctx.response().end();*/
	    });

	    vertx.createHttpServer().requestHandler(router::accept).listen(8081);
	  }
}
