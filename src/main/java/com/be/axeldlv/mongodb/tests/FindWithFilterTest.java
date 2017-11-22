package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


public class FindWithFilterTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findWithFilterTest");

        coll.drop();

        for(int i = 0; i <10 ; i++ ) {
            coll.insertOne(new Document().append("x", new Random().nextInt(2)).append("y", new Random().nextInt(100)));
        }

//        Bson filter = new Document("x", 0).append("y", new Document("$gt", 10).append("$lt", 90));

//        Bson filter = Filters.eq("x", 0);

        Bson filter = and(Filters.eq("x", 0), gt("y", 10), lt("y", 90));

        List<Document> all = coll.find(filter).into(new ArrayList<Document>());

        System.out.println("Find with Filter :");
        for(Document cur : all) {
            printJson(cur);
        }

        System.out.println("Count :");
        long count = coll.count(filter);
        System.out.print(count);
    }
}
