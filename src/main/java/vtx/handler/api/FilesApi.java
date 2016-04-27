package vtx.handler.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerFileUpload;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.FileUpload;
import vtx.util.CommonUtil;
import vtx.util.MD5Check;
import vtx.util.TemplateEngineUtil;

public class FilesApi
{
	//private static MongoClient mongo;
	private static MongoClient mongo;
	
	public FilesApi(MongoClient mongo){
		this.mongo = mongo;
	}
	
	public void uploadIndex(RoutingContext context){
		context.put("title", "Upload File");
        /*try
		{
        	Mongo connection = new Mongo("localhost", 27017);  
        	DB db = connection.getDB("keystone_test");  
        	DBCollection collection = db.getCollection("fs.files"); 
        	System.out.println(collection);
        	GridFS myFS = new GridFS(db);  
        	ObjectId id = new ObjectId();
            //InputStream in = new FileInputStream(dir + fileName);
            DBObject query  = new BasicDBObject("_id", id);
            GridFSDBFile gridFSDBFile = myFS.findOne(query); 
            System.out.println(gridFSDBFile);
        	
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		TemplateEngineUtil.JadeEngine(context, "webapp/templates/files/index.jade"); 
	}
	
	public void saveFile(RoutingContext context){
		String dir = System.getProperty("user.dir")+"\\src\\main\\file-uploads\\";
        context.response().putHeader("Content-Type", "text/plain");
        context.response().setChunked(true);
		context.request().setExpectMultipart(true);
        context.request().uploadHandler(upload -> {
        	
            upload.exceptionHandler(cause -> {
            	context.response().setChunked(true).end("Upload failed");
            });
            
            upload.endHandler(v -> {
            	//context.response().setChunked(true).end("Successfully uploaded to " + upload.filename());
            	context.response().setChunked(true).end();
            });
            String fileName = upload.filename();
            
            upload.streamToFileSystem(dir + fileName);
            try
			{
            	Mongo connection = new Mongo("localhost", 27017);  
            	DB db = connection.getDB("keystone_test");  
            	//DBCollection collection = db.getCollection("fs.files");  
            	GridFS myFS = new GridFS(db, "fs");  
            	ObjectId id = new ObjectId();
            	File f = new File(dir + fileName);
            	
                DBObject query  = new BasicDBObject("md5", MD5Check.getFileMD5String(f));
                //System.out.println(query);
                //gridFSInputFile.getMD5();
                GridFSDBFile gridFSDBFile = myFS.findOne(query);
            	
                if(gridFSDBFile != null){
                	//System.out.println(gridFSDBFile);
                	context.response().setChunked(true).write("file is exit \n");
                }
                else{
                    GridFSInputFile gridFSInputFile = myFS.createFile(f);
                    gridFSInputFile.setId(id);
                    gridFSInputFile.setContentType(CommonUtil.getContentType(fileName));
                    //System.out.println(gridFSInputFile.getContentType());
                    //System.out.println(gridFSInputFile.getChunkSize());
                    gridFSInputFile.saveChunks();
                    gridFSInputFile.save();
                    context.response().setChunked(true).write("Successfully uploaded to " + upload.filename() + "\n");
                }
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
	}
	
}
