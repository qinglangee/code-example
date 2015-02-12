package com.zhch.example.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoTestConn {

	MongoClient mongo;
	DB db;
	DBCollection col;
	int limit = 10;

	MongoTestConn() throws UnknownHostException {
		mongo = new MongoClient("192.168.1.21", 27017);
		db = mongo.getDB("test");
		col = db.getCollection("zhch");
	}

	public void test() {
		BasicDBObject condition = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		values.add(new BasicDBObject("weight", new BasicDBObject("$lt", 4)));

		condition.put("$or", values);
		db.getCollection("zhch").find(condition);

		pringResult(condition);
	}

	private void pringResult(BasicDBObject condition) {
		DBCursor cur = col.find(condition).sort(new BasicDBObject("id", 1)).limit(limit);
		List<User> list = new ArrayList<User>();
		if (cur != null) {
			User user = null;
			while (cur.hasNext()) {
				user = getObjectByResultSet(cur.next());
				if (user != null)
					list.add(user);
			}
		}
		for(User u : list){
			System.out.println(u);
		}
	}

	private User getObjectByResultSet(DBObject dbobj) {
		if(dbobj == null) return null;
		User user = new User();
		user.id = (Long)dbobj.get("id");
		user.age = (Long)dbobj.get("age");
		user.weight = (Long)dbobj.get("weight");
		user.height = (Long)dbobj.get("height");
		return user;
	}
	

	public static void main(String[] args) throws UnknownHostException {
		MongoTestConn t = new MongoTestConn();
		t.test();
	}
}
