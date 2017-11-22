package com.be.axeldlv.mongodb.util;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class ZipCodeAggregationTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("zipcodes");

        List<Document> pipeline;

        pipeline =  asList(
                new Document("$group", new Document("_id", "$state")
                .append("totalPop", new Document("$sum", "$pop"))),
                new Document("$match", new Document("totalPop", new Document("$gte", 1000000))));

        //pipeline2 =  asList(Document.parse("Add Match and group"));


        List<Document> results = collection.aggregate(pipeline).into(new ArrayList<Document>());

        for (Document cur : results) {
            System.out.println(cur.toJson());
        }
    }

}
