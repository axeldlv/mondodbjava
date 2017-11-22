package com.be.axeldlv.mongodb.tests;

import static java.util.Arrays.asList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document smith = new Document("name", "Smith").append("age", 30).append("profession", "programmer");
        Document axel = new Document("name", "Axel").append("age", 26).append("profession", "Dev/Analyst");

//        coll.insertOne(smith);

        coll.insertMany(asList(smith, axel));
    }
}
