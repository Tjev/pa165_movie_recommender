package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.util.Objects;
import javax.validation.constraints.NotNull;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Movie movie;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private int overallScore;

    @NotNull
    private int originality;

    @NotNull
    private int soundtrack;

    @NotNull
    private int narrative;

    @NotNull
    private int cinematography;

    @NotNull
    private int depth;

    public Rating() {
    }

    public Rating(@NotNull Movie movie, @NotNull User user, @NotNull int overallScore, @NotNull int originality, @NotNull int soundtrack, @NotNull int narrative, @NotNull int cinematography, @NotNull int depth) {
        this.movie = movie;
        this.user = user;
        this.overallScore = overallScore;
        this.originality = originality;
        this.soundtrack = soundtrack;
        this.narrative = narrative;
        this.cinematography = cinematography;
        this.depth = depth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public int getOriginality() {
        return originality;
    }

    public void setOriginality(int originality) {
        this.originality = originality;
    }

    public int getSoundtrack() {
        return soundtrack;
    }

    public void setSoundtrack(int soundtrack) {
        this.soundtrack = soundtrack;
    }

    public int getNarrative() {
        return narrative;
    }

    public void setNarrative(int narrative) {
        this.narrative = narrative;
    }

    public int getCinematography() {
        return cinematography;
    }

    public void setCinematography(int cinematography) {
        this.cinematography = cinematography;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;

        Rating rating = (Rating) o;

        if (!getMovie().equals(rating.getMovie())) return false;
        if (!getUser().equals(rating.getUser())) return false;
        if (getCinematography() != rating.getCinematography()) return false;
        if (getDepth() != rating.getDepth()) return false;
        if (getNarrative() != rating.getNarrative()) return false;
        if (getOriginality() != rating.getOriginality()) return false;
        if (getSoundtrack() != rating.getSoundtrack()) return false;
        return getOverallScore() == rating.getOverallScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, user, overallScore, originality, soundtrack, narrative, cinematography, depth);
    }
}
