package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;


public class FindWithProjectionTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findWithProjection");

        coll.drop();

        for(int i = 0; i <10 ; i++ ) {
            coll.insertOne(new Document().append("x", new Random().nextInt(2)).append("y", new Random().nextInt(100)).append("i", i));
        }


        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));
        // x exclude
        Bson projection = new Document("x", 0).append("_id", 0);
        // exclude only _id
        Bson projection2 = new Document("y", 1).append("x", 1).append("_id", 0);

        // use exclude method and put the import in static
        Bson projection3 = exclude("x", "_id");

        // Projections can be put in static also and the "Projections" method will be "removed" from the code
        Bson projection4 = Projections.fields(include("y", "i"), exclude("_id"));

        Bson projection5 = Projections.fields(include("y", "i"), excludeId());

        List<Document> all = coll.find(filter).projection(projection).into(new ArrayList<Document>());

        System.out.println("Find with Filter :");
        for(Document cur : all) {
            printJson(cur);
        }

    }


}
