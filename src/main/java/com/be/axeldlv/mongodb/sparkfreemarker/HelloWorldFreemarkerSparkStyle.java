package com.be.axeldlv.mongodb.sparkfreemarker;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldFreemarkerSparkStyle {

	public static void main(String[] args) {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldFreemarkerSparkStyle.class, "/");

		Spark.get(new Route("/") {
			@Override
			public Object handle(final Request request, final Response response) {
				StringWriter writer = new StringWriter();
				try {
					Template helloTemplate = configuration.getTemplate("hello.ftl");

					Map<String, Object> helloMap = new HashMap<String, Object>();
					helloMap.put("name", "Axel");
					helloTemplate.process(helloMap, writer);

					System.out.println(writer);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					halt(500);
					e.printStackTrace();
				}
				return writer;
			}
		});
		
		
		Spark.get(new Route("/testPage") {
			@Override
			public Object handle(final Request request, final Response response) {
		
				return "TestPage";
			}
		});
		
		Spark.get(new Route("/testPage/:things") {
			@Override
			public Object handle(final Request request, final Response response) {
		
				return request.params(":things");
			}
		});
		
	}

}
