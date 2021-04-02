package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents rating of a movie from a user
 *
 * @author Kristian Tkacik
 */
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ManyToOne()
    private Movie movie;

    @Column(nullable = false)
    @ManyToOne()
    private User user;

    @Column(nullable = false)
    private BigDecimal overallScore;

    @Column(nullable = false)
    private int originality;

    @Column(nullable = false)
    private int soundtrack;

    @Column(nullable = false)
    private int narrative;

    @Column(nullable = false)
    private int cinematography;

    @Column(nullable = false)
    private int depth;

    public Rating() {
    }

    public Rating(Movie movie, User user, int originality, int soundtrack, int narrative, int cinematography, int depth) {
        this.movie = movie;
        this.user = user;
        this.originality = originality;
        this.soundtrack = soundtrack;
        this.narrative = narrative;
        this.cinematography = cinematography;
        this.depth = depth;
        updateOverallScore();
    }

    public Long getId() {
        return id;
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

    public BigDecimal getOverallScore() {
        return overallScore;
    }

    public int getOriginality() {
        return originality;
    }

    public void setOriginality(int originality) {
        this.originality = originality;
        updateOverallScore();
    }

    public int getSoundtrack() {
        return soundtrack;
    }

    public void setSoundtrack(int soundtrack) {
        this.soundtrack = soundtrack;
        updateOverallScore();
    }

    public int getNarrative() {
        return narrative;
    }

    public void setNarrative(int narrative) {
        this.narrative = narrative;
        updateOverallScore();
    }

    public int getCinematography() {
        return cinematography;
    }

    public void setCinematography(int cinematography) {
        this.cinematography = cinematography;
        updateOverallScore();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
        updateOverallScore();
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
        return getOverallScore().equals(rating.getOverallScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getMovie(),
                getUser(),
                getOverallScore(),
                getOriginality(),
                getSoundtrack(),
                getNarrative(),
                getCinematography(),
                getDepth());
    }

    private void updateOverallScore() {
        this.overallScore = new BigDecimal(
                        getOriginality() +
                            getSoundtrack() +
                            getNarrative() +
                            getCinematography() +
                            getDepth()).divide(new BigDecimal("5"), RoundingMode.HALF_EVEN);
    }
}
