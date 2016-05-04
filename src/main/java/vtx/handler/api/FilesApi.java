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
import vtx.vert_dao.MongoFSDAO;

public class FilesApi
{
	//private static MongoClient mongo;
	private static MongoClient mongo;
	//private JsonObject dbConfig;
	private MongoFSDAO mongoFsDao;
	
	private MD5Check md5Chk;
	
	public FilesApi(MongoClient mongo, JsonObject dbConfig){
		this.mongo = mongo;
		this.mongoFsDao = new MongoFSDAO(dbConfig);
		this.md5Chk = new MD5Check();
	}
	
	public void uploadIndex(RoutingContext context){
		context.put("title", "Upload File");
		TemplateEngineUtil.JadeEngine(context, "webapp/templates/files/index.jade"); 
	}
	
	public void saveFile2(RoutingContext context){
		String dir = System.getProperty("user.dir")+"\\src\\main\\file-uploads\\";
        context.response().putHeader("Content-Type", "text/plain");
        context.response().setChunked(true);
		context.request().setExpectMultipart(true);
        context.request().uploadHandler(upload -> {
        	String fileName = upload.filename();
            
            upload.streamToFileSystem(dir + fileName);
            upload.exceptionHandler(cause -> {
            	context.response().setChunked(true).end("Upload failed");
            });
            
            upload.endHandler(v -> {
            	try
    			{
            		/*MongoDAO mongoDAO = new MongoDAO();
            		File f = new File(dir + fileName);
                    DBObject query = new BasicDBObject("md5", md5Chk.getFileMD5String(f));
                    GridFSDBFile gridFSDBFile = mongoDAO.findOne(query);
                    if(gridFSDBFile != null){
                    	System.out.println(gridFSDBFile);
                    	context.response().setChunked(true).write("file is exit \n");
                    }
                    else{
                    	Object id = mongoDAO.Save(f, fileName);
                    	System.out.println(id);
                    	if(id != null){
                    		context.response().setChunked(true).write("Successfully uploaded to " + fileName + " \n");
                    	}
                    	else{
                    		context.response().setChunked(true).write("file is exit \n");
                    	}
                    }*/
    			}
    			catch (Exception e)
    			{
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            	//context.response().setChunked(true).end("Successfully uploaded to " + upload.filename());
            	context.response().setChunked(true).end();
            });
        });
	}
	
	public void saveFile(RoutingContext context){
		String dir = System.getProperty("user.dir")+"\\src\\main\\file-uploads\\";
        context.response().putHeader("Content-Type", "text/plain");
        context.response().setChunked(true);
		context.request().setExpectMultipart(true);
        context.request().uploadHandler(upload -> {
        	String fileName = upload.filename();
            
            upload.streamToFileSystem(dir + fileName);
            
            upload.exceptionHandler(cause -> {
            	context.response().setChunked(true).end("Upload failed");
            });
            
            upload.endHandler(v -> {
            	try{
                    File f = new File(dir + fileName);
                    DBObject query = new BasicDBObject("md5", md5Chk.getFileMD5String(f));
                    GridFSDBFile gridFSDBFile = mongoFsDao.findOne(query);
                    if(gridFSDBFile != null){
                    	System.out.println(gridFSDBFile);
                    	context.response().setChunked(true).write("file is exit \n");
                    }
                    else{
                    	Object id = mongoFsDao.Save(f, fileName);
                    	System.out.println(id);
                    	if(id != null){
                    		context.response().setChunked(true).write("Successfully uploaded to " + fileName + " \n");
                    	}
                    	else{
                    		context.response().setChunked(true).write("file is exit \n");
                    	}
                    }
                }
                catch(Exception e){
                	context.response().setChunked(true).write("Upload failed. error message:" + e.getMessage() + " \n");
                }
            	context.response().setChunked(true).end();
            });
        });
	}
}
