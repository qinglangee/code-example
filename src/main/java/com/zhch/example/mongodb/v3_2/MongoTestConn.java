package com.zhch.example.mongodb.v3_2;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
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
}
