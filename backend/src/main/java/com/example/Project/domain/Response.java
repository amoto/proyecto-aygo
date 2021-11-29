package com.example.Project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "responses")
public class Response {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name="text")
    private String text;

    @Column(name="created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name="created_by")
    @JsonProperty("created_by")
    private String createdBy;

    @Column(name="up_votes")
    @JsonProperty("up_votes")
    private int upVotes;

    @Column(name="down_votes")
    @JsonProperty("down_votes")
    private int downVotes;

    @Column(name = "accepted")
    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vote> votes;

    public Response(){}

    public Response(int id, String text, Timestamp createdAt, String createdBy, int upVotes, int downVotes,
                    boolean accepted, Question question, List<Vote> votes) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.accepted = accepted;
        this.question = question;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
