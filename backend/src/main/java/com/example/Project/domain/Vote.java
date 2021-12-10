package com.example.Project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "votes")
public class Vote {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "created_by")
    @JsonProperty("created_by")
    private String createdBy;

    @Column(name = "vote_up")
    @JsonProperty("vote_up")
    private int voteUp;

    @Column(name = "vote_down")
    @JsonProperty("vote_down")
    private int voteDown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    @JsonIgnore
    private Response response;


    public Vote() {
    }

    public Vote(int id, String createdBy, int voteUp, int voteDown, Response response) {
        this.id = id;
        this.createdBy = createdBy;
        this.response = response;
        this.voteUp = voteUp;
        this.voteDown = voteDown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }
}

