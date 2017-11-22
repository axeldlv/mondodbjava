package com.be.axeldlv.mongodb.morphia;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;


@Entity("repos")
public class Repository {

    @Id
    public String name;

    @Reference
    public Organization organization;

    @Reference
    public GitHubUser owner;
    // public Settings settings = new Settings();

    public Repository() {}

    public Repository(final Organization organization, final String name) {
        this.organization = organization;
        this.name = organization.name + "/" +name;
    }

    /* To be verified */
    public Repository(final GitHubUser owner, final String name) {
        this.owner = owner;
        this.name = name;
    }

}
