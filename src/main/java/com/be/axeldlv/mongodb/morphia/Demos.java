package com.be.axeldlv.mongodb.morphia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;


public class Demos {

    private SimpleDateFormat sdf = new SimpleDateFormat();
    private GitHubUser evanchooly;
    private Date date;

    /* MONGODB Instance */
    MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
    MongoClient mongo = new MongoClient(new ServerAddress(), options);
    /* Morphia Instance */
    Morphia morphia = new Morphia();
    Datastore ds = morphia.createDatastore(mongo, "my_database");

    public Demos() throws ParseException { date = sdf.parse("29-08-1989");
    }

    @Test
    public void basicUser() {
        evanchooly = new GitHubUser("evanchooly");
        evanchooly.fullName = "Axel";
        evanchooly.memberSince = date;
        evanchooly.following = 1000;

        ds.save(evanchooly);
    }

    @Test(dependsOnMethods = {"basicUser"})
    public void repositories() throws ParseException {
        Organization org = new Organization("SkyProducts");
        ds.save(org);

        Repository morphia1 = new Repository(org, "morphia");
        Repository morphia2 = new Repository(evanchooly, "morphia");

        ds.save(morphia1);
        ds.save(morphia2);

        evanchooly.repository.add(morphia1);
        evanchooly.repository.add(morphia2);

        ds.save(evanchooly);

    }

    @Test(dependsOnMethods = {"repositories"})
    public void query() {
        Query<Repository> query = ds.createQuery(Repository.class);

        Repository repository = query.get();

        List<Repository> repositories = query.asList();

        Iterable<Repository> fetch = query.fetch();
        ((MorphiaIterator) fetch).close();

        Iterator<Repository> iterator = fetch.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        query.field("owner").equal(evanchooly).get();

        GitHubUser memberSince = ds.createQuery(GitHubUser.class).field("memberSince").equal(date).get();
        System.out.println("memberSince = " +memberSince);
        GitHubUser since = ds.createQuery(GitHubUser.class).field("since").equal(date).get();
        System.out.println("Since = " +since);
    }

    @Test(dependsOnMethods = {"repositories"})
    public void updates() {
        evanchooly.followers = 33;
        evanchooly.following = 678;
        ds.save(evanchooly);
    }

    @Test(dependsOnMethods = {"repositories"})
    public void massUpdates() {
        UpdateOperations<GitHubUser> update =
                ds.createUpdateOperations(GitHubUser.class)
                    .inc("followers")
                    .set("following", 42);
        Query<GitHubUser> query = ds.createQuery(GitHubUser.class)
                    .field("followers").equal(0);
        ds.update(query, update);
    }

    @Test(dependsOnMethods = {"repositories"},
                    expectedExceptions = {ConcurrentModificationException.class})
    public void versioned() {
        Organization organization = ds.createQuery(Organization.class).get();
        Organization organization2 = ds.createQuery(Organization.class).get();

        Assert.assertEquals(organization.version, 1L);
        ds.save(organization);

        Assert.assertEquals(organization.version, 2L);
        ds.save(organization);

        Assert.assertEquals(organization.version, 3L);
        ds.save(organization2);

    }


}
