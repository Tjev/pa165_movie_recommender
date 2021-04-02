package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int overallScore;

    private int originality;

    private int soundtrack;

    private int narrative;

    private int cinematography;

    private int depth;

    public Rating() {
    }

    public Rating(int overallScore, int originality, int soundtrack, int narrative, int cinematography, int depth) {
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

        if (getCinematography() != rating.getCinematography()) return false;
        if (getDepth() != rating.getDepth()) return false;
        if (getNarrative() != rating.getNarrative()) return false;
        if (getOriginality() != rating.getOriginality()) return false;
        if (getSoundtrack() != rating.getSoundtrack()) return false;
        return getOverallScore() == rating.getOverallScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(overallScore, originality, soundtrack, narrative, cinematography, depth);
    }
}
