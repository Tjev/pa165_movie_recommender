package cz.fi.muni.pa165.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * DTO for rating creation.
 *
 * @author Kristian Tkacik
 */
public class RatingCreateDTO {

    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int originality;

    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int soundtrack;

    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int narrative;

    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int cinematography;

    @Min(value = 1, message = "Minimum rating value is 1")
    @Max(value = 5, message = "Maximum rating value is 5")
    private int depth;

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
        if (!(o instanceof RatingCreateDTO)) return false;

        RatingCreateDTO rating = (RatingCreateDTO) o;

        if (getCinematography() != rating.getCinematography()) return false;
        if (getDepth() != rating.getDepth()) return false;
        if (getNarrative() != rating.getNarrative()) return false;
        if (getOriginality() != rating.getOriginality()) return false;
        return getSoundtrack() == rating.getSoundtrack();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getOriginality(),
                getSoundtrack(),
                getNarrative(),
                getCinematography(),
                getDepth());
    }

    @Override
    public String toString() {
        return "RatingCreateDTO{" +
                "originality=" + originality +
                ", soundtrack=" + soundtrack +
                ", narrative=" + narrative +
                ", cinematography=" + cinematography +
                ", depth=" + depth +
                '}';
    }
}
