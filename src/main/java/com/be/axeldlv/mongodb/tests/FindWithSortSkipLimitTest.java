package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;


public class FindWithSortSkipLimitTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findWithProjection");

        coll.drop();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                coll.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = Projections.fields(include("i", "j"), excludeId());
        // Bson sort = new Document("i", 1);
        // Bson sort = Sorts.ascending("i");
        // Bson sort = orderBy(ascending("i"), descending("j"));
        Bson sort = descending("j");


        List<Document> all = coll.find().projection(projection)
                .sort(sort)
                .skip(20)
                .limit(50)
                .into(new ArrayList<Document>());

        System.out.println("Find with Skip Sort Limit :");
        for (Document cur : all) {
            printJson(cur);
        }

    }
}
