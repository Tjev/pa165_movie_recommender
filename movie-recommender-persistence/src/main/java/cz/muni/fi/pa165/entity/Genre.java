package cz.muni.fi.pa165.entity;

public enum Genre {
    ACTION,
    ADULT,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FAMILY,
    FANTASY,
    NOIR,
    HISTORY,
    HORROR,
    MUSICAL,
    MYSTERY,
    NEWS,
    REALITY,
    ROMANCE,
    SCIFI,
    SPORT,
    THRILLER,
    WAR,
    WESTERN;

    @Override
    public String toString() {
        var s = this.name();
        return s.charAt(0) + s.substring(1).toLowerCase();
    }
}
