package com.example.Project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "questions")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name = "created_by")
    @JsonProperty("created_by")
    private String createdBy;

    @Column(name = "tags")
    private String tags;

    @Column(name = "votes_up")
    @JsonProperty("votes_up")
    private int votesUp;

    @Column(name = "votes_down")
    @JsonProperty("votes_down")
    private int votesDown;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Response> responses;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QuestionVote> votes;

    public Question() {
    }

    public Question(int id, String title, String description, Timestamp createdAt, String createdBy, String tags,
                    int votesUp, int votesDown, List<Response> responses, List<QuestionVote> votes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.tags = tags;
        this.votesUp = votesUp;
        this.votesDown = votesDown;
        this.responses = responses;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getVotesUp() {
        return votesUp;
    }

    public void setVotesUp(int votesUp) {
        this.votesUp = votesUp;
    }

    public int getVotesDown() {
        return votesDown;
    }

    public void setVotesDown(int votesDown) {
        this.votesDown = votesDown;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<QuestionVote> getVotes() {
        return votes;
    }

    public void setVotes(List<QuestionVote> votes) {
        this.votes = votes;
    }
}
