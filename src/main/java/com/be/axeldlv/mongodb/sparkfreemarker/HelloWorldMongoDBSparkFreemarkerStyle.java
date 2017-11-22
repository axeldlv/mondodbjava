package com.be.axeldlv.mongodb.sparkfreemarker;

import java.io.StringWriter;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;


public class HelloWorldMongoDBSparkFreemarkerStyle {

    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreemarkerStyle.class, "/");

        MongoClient client = new MongoClient();

        MongoDatabase db = client.getDatabase("course");
        final MongoCollection<Document> coll = db.getCollection("hello");

        coll.drop();

        coll.insertOne(new Document("name", "MongoDB"));

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");

                    Document document = coll.find().first();

                    helloTemplate.process(document, writer);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });

    }
}
