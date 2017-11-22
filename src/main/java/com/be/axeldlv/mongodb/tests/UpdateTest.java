package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;


public class UpdateTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("updateTest");

        coll.drop();

        for(int i = 0; i <8 ; i++ ) {
            coll.insertOne(new Document().append("x", i).append("_id", i));
        }


        // add the value "update" to the document x = 5
        // coll.replaceOne(eq("x", 5), new Document("_id", 5).append("x", 20).append("update", true));

        // Update the _id = 7 and modified the x by 33
        //coll.updateOne(eq("_id", 7), new Document("$set", new Document("x", 33)));

        // Add a new document with the _id = 9 and x = 52
        coll.updateOne(eq("_id", 9), new Document("$set", new Document("x", 52)), new UpdateOptions().upsert(true));

        // NOT add (because upsert at False) a new document with the _id = 9 and x = 52
        coll.updateOne(eq("_id", 9), new Document("$set", new Document("x", 52)), new UpdateOptions().upsert(false));

        // Increment all _id < 5 with x + 1
        coll.updateMany(gte("_id", 5), new Document("$inc", new Document("x", 1)));

        System.out.println("Updated :");
        for(Document cur : coll.find().into(new ArrayList<Document>())) {
            printJson(cur);
        }


    }
}
