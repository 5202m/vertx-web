package vtx.vert_dao;

import java.io.File;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import io.vertx.core.json.JsonObject;
import vtx.util.CommonUtil;

public class MongoDAO
{
	Mongo mongo;// new MongoClient("localhost", 27017);
	//MongoDatabase db;
	DB db;
	GridFS gridFs;// = new GridFS(db, "fs");
	public MongoDAO(JsonObject dbConfig){
		this.mongo = new Mongo(dbConfig.getString("mongoHost"), dbConfig.getInteger("mongoPort"));
		this.db = mongo.getDB(dbConfig.getString("mongoDatabase"));
		this.gridFs = new GridFS(db, "fs");
	}
	
	public GridFSDBFile findOne(DBObject query){
		GridFSDBFile gridFSDBFile = gridFs.findOne(query);
		return gridFSDBFile;
	}
	
	public Object Save(File file, String fileName){
		ObjectId id = new ObjectId();
		try{
    		GridFSInputFile gridFSInputFile = gridFs.createFile(file);
    		gridFSInputFile.setId(id);
    		gridFSInputFile.setContentType(CommonUtil.getContentType(fileName));
    		gridFSInputFile.setMetaData(new BasicDBObject());
    		gridFSInputFile.saveChunks();
            gridFSInputFile.save();
		}
		catch(Exception e){
			id = null;
		}
		return id;
	}
}
