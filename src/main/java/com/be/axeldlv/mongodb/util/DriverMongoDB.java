package com.be.axeldlv.mongodb.util;

import org.bson.BsonDocument;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DriverMongoDB
{
    public static void main( String[] args )
    {
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
//        asList(new ServerAddress("localhost", 27017))
//        new MongoClientURI("mongodb://localhost:27017")
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());

//        MongoCollection<Document> coll = db.getCollection("test");
        MongoCollection<BsonDocument> coll = db.getCollection("test", BsonDocument.class);

    }
}
