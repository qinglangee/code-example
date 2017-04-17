package com.zhch.example.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
//import com.mongodb.MongoException;  // 在mongodb-driver 3.x 中已经没有　MongoException 了

/**
 * 这个类就用一下, 以后就不用了
 * @author lifeix
 *
 */
public class MongoIndexHint {
	protected static final Logger logger = LoggerFactory.getLogger(MongoIndexHint.class);
	MongoClient mongo;
	DB db;
	DBCollection col;
	int limit = 10;
	

	MongoIndexHint() throws UnknownHostException {
//		mongo = new MongoClient("192.168.1.21", 27017);
		mongo = new MongoClient("192.168.2.3", 27017);
		db = mongo.getDB("l_comment");
		col = db.getCollection("comment");
	}

	/**
	 * L99 的comment 测试
	 */
	public void testComment(){
		DBCollection coll = col;
		DBObject obj = new BasicDBObject();
		obj.put("accountId",4L);
		List<DBObject> listOr = new ArrayList<DBObject>();
		listOr.add(new BasicDBObject("status", 11));
		listOr.add(new BasicDBObject("status", 12));
		obj.put("$or", listOr);
		BasicDBObject andDB = new BasicDBObject();
		andDB.append("$gt", 0);
		andDB.append("$lt",4514185L);
		obj.put("currBoardId",andDB);
		DBCursor cur = null;
		List<Long> comments = new ArrayList<Long>();
		long t2 = System.currentTimeMillis();
		long t3=0, t4=0, t5=0, t6=0;
		
		BasicDBObject returnFields = new BasicDBObject();
		returnFields.append("_id",-1);
		returnFields.append("currBoardId",1);
		try {
//			cur = coll.find(obj,returnFields).sort(new BasicDBObject("commentId",-1)).limit(limit);
			cur = coll.find(obj,returnFields).sort(new BasicDBObject("commentId",-1)).limit(limit).hint("idx_acc");
			t3 = System.currentTimeMillis();
			Iterator<DBObject> its = cur.iterator();
			System.out.println(obj);
			t4 = System.currentTimeMillis();
			while(its.hasNext()) {
				 t5 = System.currentTimeMillis();
				DBObject dbObj = its.next();
				comments.add(Long.parseLong(dbObj.get("currBoardId").toString()));
			}
			 t6 = System.currentTimeMillis();
//		} catch(MongoException e) {　 // 在mongodb-driver 3.x 中已经没有　MongoException 了
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(cur != null) {
				cur.close();
			}
		}
		print(t2,t3,t4,t5,t6);
	}


	void print(long ... t){
		System.out.print("time : ");
		for(int i=1;i<t.length;i++){
			System.out.print(" " + (t[i] - t[i-1]));
		}
		System.out.println();
	}
	public static void main(String[] args) throws UnknownHostException {
		MongoIndexHint t = new MongoIndexHint();
		t.testComment();
	}
}
