package vtx.vert_dao;

import java.io.File;
import java.io.OutputStream;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.MongoDatabase;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import io.vertx.core.json.JsonObject;
//import me.lightspeed7.mongofs.LoremIpsum;
import me.lightspeed7.mongofs.MongoFileConstants;
import vtx.util.CommonUtil;

public class MongoFSDAO
{
	private static Document ID = new Document(MongoFileConstants._id.name(), new ObjectId());
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private GridFS gridFs;
	
	public MongoFSDAO(JsonObject dbConfig){
		this.mongoClient = new MongoClient(dbConfig.getString("mongoHost"), dbConfig.getInteger("mongoPort"));
		this.database = new MongoDatabase(mongoClient.getDB(dbConfig.getString("mongoDatabase")));
		this.gridFs = new GridFS(database.getSurrogate(), "fs");
	}
	
	public GridFSDBFile findOne(DBObject query){
		GridFSDBFile gridFSDBFile = gridFs.findOne(query);
		return gridFSDBFile;
	}
	
	public Object Save(File file, String fileName){
		try{
    		GridFSInputFile gridFSInputFile = gridFs.createFile(file);
    		gridFSInputFile.setId(ID.get(MongoFileConstants._id.toString()));
    		gridFSInputFile.put(MongoFileConstants.contentType.toString(), CommonUtil.getContentType(fileName));
    		gridFSInputFile.setMetaData(new BasicDBObject());
    		gridFSInputFile.saveChunks();
            gridFSInputFile.save();
		}
		catch(Exception e){
			ID = null;
		}
		return ID;
	}
}
