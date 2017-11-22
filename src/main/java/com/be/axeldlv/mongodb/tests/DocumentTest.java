package com.be.axeldlv.mongodb.tests;

import static com.be.axeldlv.mongodb.tests.PrintJsonHelpers.printJson;

import java.util.Arrays;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;


public class DocumentTest {

    public static void main(String[] args) {
        Document document = new Document().append("str", "MongoDB, Hello")
                .append("int", 42)
                .append("ObjectID", new ObjectId())
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1, 2, 3));

//        String str = document.getString("str");
//        Integer i = document.getInteger("int");
        printJson(document);

        BsonDocument bsonDocument = new BsonDocument("str", new BsonString("MongoDB, Hello"));

    }

}
