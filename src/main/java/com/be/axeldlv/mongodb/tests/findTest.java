package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class findTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findTest");

        coll.drop();

        for(int i = 0; i <10 ; i++ ) {
            coll.insertOne(new Document("x", i));
        }

        System.out.println("Find One :");
        Document first = coll.find().first();
        printJson(first);

        System.out.println("Find All with into :");
        List<Document> all = coll.find().into(new ArrayList<Document>());
        for(Document cur : all) {
            printJson(cur);
        }

        System.out.println("Find All with iteration :");
        MongoCursor<Document> cursor = coll.find().iterator();
        try {
            while(cursor.hasNext()) {
                Document cur = cursor.next();
                printJson(cur);
            }
        } finally {
            cursor.close();
        }


        System.out.println("Count :");
        long count = coll.count();
        System.out.print(count);
    }
}
