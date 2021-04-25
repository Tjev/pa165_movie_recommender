package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents rating of a movie from a user
 *
 * @author Kristian Tkacik
 */
@Entity
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne()
    private Movie movie;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne()
    private User user;

    @NotNull
    @Column(nullable = false)
    private BigDecimal overallScore;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int originality;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int soundtrack;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int narrative;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int cinematography;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
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
        return getSoundtrack() == rating.getSoundtrack();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getMovie(),
                getUser(),
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

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", movie=" + movie +
                ", user=" + user +
                ", overallScore=" + overallScore +
                ", originality=" + originality +
                ", soundtrack=" + soundtrack +
                ", narrative=" + narrative +
                ", cinematography=" + cinematography +
                ", depth=" + depth +
                '}';
    }
}
