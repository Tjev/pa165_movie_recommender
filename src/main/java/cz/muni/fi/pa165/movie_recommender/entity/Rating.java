package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents rating of a movie from a user
 *
 * @author Kristian Tkacik
 * @since 02.04.2021
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
        this.overallScore = new BigDecimal(originality + soundtrack + narrative + cinematography + depth)
                            .divide(new BigDecimal("5"), RoundingMode.HALF_EVEN);
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

    public BigDecimal getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(BigDecimal overallScore) {
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
        return getOverallScore().equals(rating.getOverallScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, user, overallScore, originality, soundtrack, narrative, cinematography, depth);
    }
}
