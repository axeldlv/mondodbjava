package com.be.axeldlv.mongodb.sparkfreemarker;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkFormHandling {

	public static void main(String[] args) {
		// Configuration FreeMarker
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldFreemarkerSparkStyle.class, "/");

		//Configure Route
		Spark.get(new Route("/") {
			@Override
			public Object handle(final Request request, final Response response) {
				
				try {
					Template helloTemplate = configuration.getTemplate("fruitPicker.ftl");
					Map<String, Object> FruitsMap = new HashMap<String, Object>();
					FruitsMap.put("fruits", Arrays.asList("Apple", "Pears", "Orange", "banana"));
					StringWriter writer = new StringWriter();
					helloTemplate.process(FruitsMap, writer);

					return writer;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					halt(500);
					e.printStackTrace();
					return null;
				}
				
			}
		});
		
		//Configure Route
		Spark.post(new Route("/favorite_fruit") {
			@Override
			public Object handle(final Request request, final Response response) {
				
				final String fruit = request.queryParams("fruit");
				if(fruit == null) {
					return "Why don't you pick one?";
				} else {
					return "You favorite fruit is " +fruit;
				}
			}
		});
	}
	
	
}
