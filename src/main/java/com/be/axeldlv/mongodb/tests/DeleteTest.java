package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DeleteTest {


    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("deleteTest");

        coll.drop();

        for(int i = 0; i <8 ; i++ ) {
            coll.insertOne(new Document().append("_id", i));
        }

        // Delete all collections after the 4
        coll.deleteMany(gt("_id", 4));

        coll.deleteOne(eq("_id", 4));

        System.out.println("Delete :");
        for(Document cur : coll.find().into(new ArrayList<Document>())) {
            printJson(cur);
        }


    }
}
