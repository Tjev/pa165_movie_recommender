package cz.muni.fi.pa165.dto.rating;

import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;

import java.util.Objects;

/**
 * DTO for Rating entity.
 *
 * @author Kristian Tkacik
 */
public class RatingDTO {

    private Long id;
    private UserDTO user;
    private MovieDTO movie;
    private int originality;
    private int soundtrack;
    private int narrative;
    private int cinematography;
    private int depth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
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
        if (!(o instanceof RatingDTO)) return false;

        RatingDTO rating = (RatingDTO) o;

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

    @Override
    public String toString() {
        return "RatingDTO{" +
                "id=" + id +
                ", user=" + user +
                ", movie=" + movie +
                ", originality=" + originality +
                ", soundtrack=" + soundtrack +
                ", narrative=" + narrative +
                ", cinematography=" + cinematography +
                ", depth=" + depth +
                '}';
    }
}
