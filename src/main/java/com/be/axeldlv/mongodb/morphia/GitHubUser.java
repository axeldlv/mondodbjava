package com.be.axeldlv.mongodb.morphia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * MONGODB - Field mappings, indexes and constraints in Morphia
 */

/* "noClassnameStored" is used with FALSE by default => Add the class name in MONGODB if FALSE */
@Entity(value = "users", noClassnameStored = true)
@Indexes({
        @Index(value = "userName, -followers", name = "popular"),
        @Index(value = "lastActive", name = "idle", expireAfterSeconds = 1000000000)
})
public class GitHubUser {

    @Id
    public String userName;
    public String fullName;

    @Property("since")
    public Date memberSince;
    public Date lastActive;

    @Reference(lazy = true)
    public List<Repository> repository = new ArrayList();
    public int followers = 0;
    public int following = 0;

    public GitHubUser() {
    }

    public GitHubUser(final String userName) { this.userName = userName; }
}
