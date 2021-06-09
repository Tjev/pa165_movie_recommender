package cz.muni.fi.pa165;

import javax.inject.Inject;

import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.PersonService;
import cz.muni.fi.pa165.service.RatingService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Jiri Papousek
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    private final MovieService movieService;
    private final PersonService personService;
    private final RatingService ratingService;
    private final UserService userService;

    @Inject
    public SampleDataLoadingFacadeImpl(MovieService movieService,
                                       PersonService personService,
                                       RatingService ratingService,
                                       UserService userService) {
        this.movieService = movieService;
        this.personService = personService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @Override
    public void loadData() throws IOException {
        Person bradPitt = createPerson("Brad Pitt", LocalDate.of(1963, 12, 18), "William Bradley Pitt is an American actor and film producer. He has received multiple awards, including two Golden Globe Awards and an Academy Award for his acting, in addition to another Academy Award, another Golden Globe Award and a Primetime Emmy Award as a producer under his production company, Plan B Entertainment.");
        Person quentinTarantino = createPerson("Quentin Tarantino", LocalDate.of(1963, 3, 27), "Quentin Jerome Tarantino is an American film director, screenwriter, producer, and actor. His films are characterized by nonlinear storylines, dark humor, stylized violence, extended dialogue, ensemble casts, references to popular culture, alternate history, and neo-noir.");
        Person emmaStone = createPerson("Emma Stone", LocalDate.of(1988, 11, 6), "Emily Jean \"Emma\" Stone is an American actress. The recipient of various accolades, including an Academy Award, a British Academy Film Award, and a Golden Globe Award, she was the world's highest-paid actress in 2017.");
        Person rubenFleischer = createPerson("Ruben Fleischer", LocalDate.of(1974, 10, 31), "Ruben Samuel Fleischer is an American film director, film producer, television producer, music video director, and commercial director who lives in Los Angeles.");

        Person keanuReeves = createPerson("Keanu Reeves", LocalDate.of(1964, 9, 2), "Keanu Charles Reeves, whose first name means \"cool breeze over the mountains\" in Hawaiian, was born September 2, 1964 in Beirut, Lebanon. He is the son of Patricia Taylor, a showgirl and costume designer, and Samuel Nowlin Reeves, a geologist.");
        Person lanaWachowski = createPerson("Lana Wachowski", LocalDate.of(1965, 6, 21), "Lana Wachowski and her sister Lilly Wachowski, also known as the Wachowskis, are the duo behind such ground-breaking movies as Matrix (1999) and Atlas mrakÅ¯ (2012). Born to mother Lynne, a nurse, and father Ron, a businessman of Polish descent, Wachowski grew up in Chicago and formed a tight creative relationship with her sister Lilly.");
        Person lillyWachowski = createPerson("Lilly Wachowski", LocalDate.of(1967, 12, 29), "Director, writer, and producer Lilly Wachowski was born in 1967 in Chicago, the daughter of Lynne, a nurse and painter, and Ron, a businessman. Lilly was educated at Kellogg Elementary School in Chicago, before moving on to Whitney M. Young High School.");
        Person laurenceFishburne = createPerson("Laurence Fishburne", LocalDate.of(1961, 7, 30), "Critically hailed for his forceful, militant, authoritative figures and one of Hollywood's most talented and versatile performers, Laurence (John) Fishburne III has been the recipient of numerous awards, including a number of NAACP Image honors.");
        Person tomHanks = createPerson("Tom Hanks", LocalDate.of(1956, 7, 9), "Thomas Jeffrey Hanks was born in Concord, California, to Janet Marylyn (Frager), a hospital worker, and Amos Mefford Hanks, an itinerant cook.");
        Person hughGrant = createPerson("Hugh Grant", LocalDate.of(1960, 9, 9), "Hugh Grant, one of Britain's best known faces, has been equally entertaining on-screen as well as in real life, and has had enough sense of humor to survive a media frenzy.");
        Person tommMoore = createPerson("Tomm Moore", LocalDate.of(1977, 1, 7), "Tomm Moore was born on January 7, 1977 in Newry, Northern Ireland.");
        Person honorKneafsey = createPerson("Honor Kneafsey", LocalDate.of(2004, 8, 5), "Honor Kneafsey was born on August 5, 2004. She is an actress, known for Miss You Already (2015), A Christmas Prince (2017) and Crooked House (2017).");

        Person samuelJackson = createPerson("Samuel L. Jackson", LocalDate.of(1948, 12, 21), "Samuel L. Jackson is an American producer and highly prolific actor, having appeared in over 100 films.");
        Person kurtRussel = createPerson("Kurt Russell", LocalDate.of(1951, 3, 17), "Kurt Vogel Russell on March 17, 1951 in Springfield, Massachusetts to Louise Julia Russell (nÃ©e Crone), a dancer & Bing Russell, an actor. He is of English, German, Scottish and Irish descent. His first roles were as a child on television series.");

        Person peterFarrelly = createPerson("Peter Farrelly", LocalDate.of(1956, 12, 17), "Peter Farrelly was born on December 17, 1956 in Phoenixville, Pennsylvania, USA as Peter John Farrelly. He is a producer and writer.");
        Person viggoMortensen = createPerson("Viggo Mortensen", LocalDate.of(1958, 10, 20), "Mortensen was born in New York City, to Grace Gamble (Atkinson) and Viggo Peter Mortensen, Sr. His father was Danish, his mother was American, and his maternal grandfather was Canadian. His parents met in Norway.");
        Person mahershalaAli = createPerson("Mahershala Ali", LocalDate.of(1974, 2, 16), "Mahershala Ali is fast becoming one of the freshest and most in-demand faces in Hollywood with his extraordinarily diverse skill set and wide-ranging background in film, television, and theater.");

        Person makotoShinkai = createPerson("Makoto Shinkai", LocalDate.of(1973, 2, 9), "Makoto Shinkai is a Japanese director, writer, producer, animator, editor, cinematographer, voice actor, manga artist and former graphic designer. Shinkai studied Japanese literature at Chuo University where he was a member of juvenile literature club where he drew picture books.");
        Person ryunosukeKamiki = createPerson("Ryunosuke Kamiki", LocalDate.of(1993, 5, 19), "Ryunosuke Kamiki was born in Fujimi. He was diagnosed with a rare disease soon after he was born and was lucky enough to become one of the 1% who survived the disease. To celebrate this miracle, his mother decided to register him as a child actor in an agency to record his growth.");
        Person moneKamishiraishi = createPerson("Mone Kamishiraishi", LocalDate.of(1998, 1, 27), "Mone Kamishiraishi was born on January 27, 1998 in Kagoshima, Japan. She is an actress, known for Kimi no na wa. (2016), Maiko wa redi (2014) and Tenki no ko (2019).");

        Person chadStahelski = createPerson("Chad Stahelski", LocalDate.of(1968, 9, 20), "Chad Stahelski (born September 20, 1968) is an American stuntman and film director. He is known for his work on Buffy the Vampire Slayer and directing the 2014 film John Wick along with David Leitch, as well as solo directing its two sequels.");
        Person joelCoen = createPerson("Joel Coen", LocalDate.of(1954, 11, 29), "Joel Coen was born on November 29, 1954 in Minneapolis, Minnesota, USA as Joel Daniel Coen. He is a producer and writer, known for The Ballad of Buster Scruggs (2018), Fargo (1996) and A Serious Man (2009). He has been married to Frances McDormand since April 1, 1984. They have one child.");
        Person ethanCoen = createPerson("Ethan Coen", LocalDate.of(1957, 9, 21), "The younger brother of Joel, Ethan Coen is an Academy Award and Golden Globe winning writer, producer and director coming from small independent films to big profile Hollywood films. He was born on September 21, 1957 in Minneapolis, Minnesota.");
        Person jeffBridges = createPerson("Jeff Bridges", LocalDate.of(1949, 12, 4), "Jeffrey Leon Bridges (born December 4, 1949) is an American actor, singer, producer, and composer. He comes from a prominent acting family and appeared on the television series Sea Hunt (1958–1960) with his father, Lloyd Bridges, and brother, Beau Bridges. One of the most acclaimed actors of his generation, he has won numerous accolades, including the Academy Award for Best Actor for the film Crazy Heart.");
        Person johnGoodman = createPerson("John Goodman", LocalDate.of(1952, 6, 20), "John Stephen Goodman is a U.S. film, television, and stage actor. He was born in St. Louis, Missouri, to Virginia Roos (Loosmore), a waitress and saleswoman, and Leslie Francis Goodman, a postal worker who died when John was a small child. He is of English, Welsh, and German ancestry.");
        Person johnTravolta = createPerson("John Travolta", LocalDate.of(1954, 2, 18), "John Joseph Travolta was born in Englewood, New Jersey, one of six children of Helen Travolta (née Helen Cecilia Burke) and Salvatore/Samuel J. Travolta. His father was of Italian descent and his mother was of Irish ancestry.");

        Person chris = createPerson("Christopher Nolan", LocalDate.of(1970, 7, 30), "Best known for his cerebral, often nonlinear, storytelling, acclaimed writer-director Christopher Nolan was born on July 30, 1970, in London, England. Over the course of 15 years of filmmaking, Nolan has gone from low-budget independent films to working on some of the biggest blockbusters ever made.");
        Person matthew = createPerson("Matthew McConaughey", LocalDate.of(1969, 11, 4), "American actor and producer Matthew David McConaughey was born in Uvalde, Texas. His mother, Mary Kathleen (McCabe), is a substitute school teacher originally from New Jersey. His father, James Donald McConaughey, was a Mississippi-born gas station owner who ran an oil pipe supply business.");
        Person peter = createPerson("Peter Jackson", LocalDate.of(1961, 10, 31), "Peter Jackson was born as an only child in a small coast-side town in New Zealand in 1961. When a friend of his parents bought him a super 8 movie camera (because she saw how much he enjoyed taking photos), the then eight-year-old Peter instantly grabbed the thing to start recording his own movies, which he made with his friends.");
        Person elijah = createPerson("Elijah Wood", LocalDate.of(1981, 1, 28), "Elijah Wood is an American actor best known for portraying Frodo Baggins in Peter Jackson's blockbuster Lord of the Rings film trilogy.");
        Person ian = createPerson("Ian McKellen", LocalDate.of(1939, 5, 25), "Widely regarded as one of greatest stage and screen actors both in his native Great Britain and internationally, twice nominated for the Oscar and recipient of every major theatrical award in the UK and US, Ian Murray McKellen was born on May 25, 1939 in Burnley, Lancashire, England, to Margery Lois (Sutcliffe) and Denis Murray McKellen, a civil engineer and lay preacher.");


        Movie zombieland = createMovie("Zombieland", "A shy student trying to reach his family in Ohio, a gun-toting bruiser in search or the last Twinkie and a pair of sisters striving to get to an amusement park join forces in a trek across a zombie-filled America.", LocalDate.of(2009, 1, 1), Set.of(Genre.ACTION, Genre.HORROR, Genre.COMEDY), null);
        Movie onceUpon = createMovie("Once Upon a Time... in Hollywood", "A faded television actor and his stunt double strive to achieve fame and success in the final years of Hollywood's Golden Age in 1969 Los Angeles.", LocalDate.of(2019, 1, 1), Set.of(Genre.COMEDY, Genre.DRAMA), null);

        Movie matrix = createMovie("Matrix", "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence.", LocalDate.of(1999, 1, 1), Set.of(Genre.ACTION, Genre.SCIFI), null);
        Movie cloudAtlas = createMovie("Cloud Atlas", "An exploration of how the actions of individual lives impact one another in the past, present and future, as one soul is shaped from a killer into a hero, and an act of kindness ripples across centuries to inspire a revolution.", LocalDate.of(2012, 1, 1), Set.of(Genre.ACTION, Genre.DRAMA, Genre.MYSTERY), null);
        Movie wolfwalkers = createMovie("Wolfwalkers", "A young apprentice hunter and her father journey to Ireland to help wipe out the last wolf pack. But everything changes when she befriends a free-spirited girl from a mysterious tribe rumored to transform into wolves by night.", LocalDate.of(2020, 1, 1), Set.of(Genre.ANIMATION, Genre.FAMILY, Genre.ADVENTURE), null);

        Movie hatefulEight = createMovie("The Hateful Eight", "In the dead of a Wyoming winter, a bounty hunter and his prisoner find shelter in a cabin currently inhabited by a collection of nefarious characters.", LocalDate.of(2016, 1, 21), Set.of(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY), null);
        Movie greenBook = createMovie("Green Book", "A working-class Italian-American bouncer becomes the driver of an African-American classical pianist on a tour of venues through the 1960s American South.", LocalDate.of(2018, 11, 16), Set.of(Genre.BIOGRAPHY, Genre.COMEDY, Genre.DRAMA), null);
        Movie yourName = createMovie("Your Name", "Two strangers find themselves linked in a bizarre way. When a connection forms, will distance be the only thing to keep them apart?", LocalDate.of(2016, 8, 26), Set.of(Genre.ANIMATION, Genre.DRAMA, Genre.FANTASY), null);

        Movie johnWick = createMovie("John Wick", "An ex-hit-man comes out of retirement to track down the gangsters that killed his dog and took everything from him.", LocalDate.of(2014, 10, 24), Set.of(Genre.ACTION, Genre.CRIME, Genre.THRILLER), null);
        Movie theBigLebowski = createMovie("The Big Lebowski", "Jeff \"The Dude\" Lebowski, mistaken for a millionaire of the same name, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.", LocalDate.of(1998, 3, 6), Set.of(Genre.COMEDY, Genre.CRIME), null);
        Movie pulpFiction = createMovie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", LocalDate.of(1994, 10, 14), Set.of(Genre.CRIME, Genre.DRAMA, Genre.COMEDY), null);

        Movie interstellar = createMovie("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", LocalDate.of(2014, 11, 7), Set.of(Genre.ADVENTURE, Genre.DRAMA, Genre.SCIFI), null);
        Movie lotr1 = createMovie("The Lord of the Rings: The Fellowship of the Ring", "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", LocalDate.of(2001, 12, 19), Set.of(Genre.ACTION, Genre.ADVENTURE, Genre.DRAMA), null);
        Movie lotr2 = createMovie("The Lord of the Rings: The Two Towers", "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.", LocalDate.of(2002, 12, 18), Set.of(Genre.ACTION, Genre.ADVENTURE, Genre.DRAMA), null);
        Movie lotr3 = createMovie("The Lord of the Rings: The Return of the King", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", LocalDate.of(2003, 12, 17), Set.of(Genre.ACTION, Genre.ADVENTURE, Genre.DRAMA), null);

        addActors(zombieland, emmaStone);
        addActors(onceUpon, bradPitt);
        addDirectedMovies(rubenFleischer, zombieland);

        addDirectedMovies(quentinTarantino, onceUpon, hatefulEight, pulpFiction);
        addActedInMovies(quentinTarantino, pulpFiction);

        addActedInMovies(keanuReeves, matrix, johnWick);
        addActedInMovies(laurenceFishburne, matrix, johnWick);

        addDirectedMovies(lanaWachowski, matrix, cloudAtlas);
        addDirectedMovies(lillyWachowski, matrix, cloudAtlas);

        addActors(cloudAtlas, tomHanks, hughGrant);

        addDirectedMovies(tommMoore, wolfwalkers);
        addActedInMovies(honorKneafsey, wolfwalkers);

        addActedInMovies(samuelJackson, hatefulEight, pulpFiction);

        addActedInMovies(kurtRussel, hatefulEight);

        peterFarrelly.addDirectedMovie(greenBook);

        viggoMortensen.addActsInMovie(greenBook);

        mahershalaAli.addActsInMovie(greenBook);

        addActors(yourName, ryunosukeKamiki, moneKamishiraishi);
        addDirectedMovies(makotoShinkai, yourName);

        addDirectedMovies(chadStahelski, johnWick);

        joelCoen.addDirectedMovie(theBigLebowski);
        ethanCoen.addDirectedMovie(theBigLebowski);

        addActors(theBigLebowski, jeffBridges, johnGoodman);

        johnTravolta.addActsInMovie(pulpFiction);

        matthew.addActsInMovie(interstellar);

        addActors(lotr1, elijah, ian, viggoMortensen);
        addActors(lotr2, elijah, ian, viggoMortensen);
        addActors(lotr3, elijah, ian, viggoMortensen);

        chris.addDirectedMovie(interstellar);
        addDirectedMovies(chris, interstellar);
        addDirectedMovies(peter, lotr1, lotr2, lotr3);

        updatePersons(bradPitt, quentinTarantino, emmaStone, rubenFleischer, keanuReeves, lanaWachowski, lillyWachowski,
                laurenceFishburne, tomHanks, hughGrant, tommMoore, honorKneafsey, samuelJackson, kurtRussel, peterFarrelly);
        updatePersons(viggoMortensen, mahershalaAli, makotoShinkai, ryunosukeKamiki, moneKamishiraishi, chadStahelski,
                joelCoen, ethanCoen, jeffBridges, johnGoodman, johnTravolta, matthew, elijah, ian, chris, peter);


        User u1 = createUser("Rado", "rado@email.com", "radoheslo");
        User u2 = createUser("Kiko", "kiko@email.com", "kikoheslo");
        User u3 = createUser("Papi", "papi@email.com", "papiheslo");
        User u4 = createUser("Jevo", "jevo@email.com", "jevoheslo");

        u4.setAdmin(true);
        userService.update(u4);

        Rating r1 = createRating(zombieland, u1, 1, 1, 1, 1, 1);
        Rating r2 = createRating(onceUpon, u1, 4, 4, 4, 4, 4);
        Rating r3 = createRating(zombieland, u2, 1, 2, 3, 4, 5);
        Rating r4 = createRating(onceUpon, u2, 3, 3, 3, 3, 3);
        Rating r5 = createRating(zombieland, u3, 2, 2, 2, 2, 2);
        Rating r6 = createRating(onceUpon, u3, 3, 3, 3, 3, 3);
        Rating r7 = createRating(zombieland, u4, 1, 1, 2, 2, 2);
        Rating r8 = createRating(onceUpon, u4, 4, 3, 3, 3, 4);
    }

    private Person createPerson(String name, LocalDate dateOfBirth, String bio) {
        Person person = new Person(name, dateOfBirth, bio);
        return personService.create(person);
    }

    private Movie createMovie(String title, String bio, LocalDate year, Set<Genre> genres, byte[] graphics) {
        Movie movie = new Movie(title, bio, year, genres, graphics);
        return movieService.create(movie);
    }

    private User createUser(String username, String emailAddress, String password) {
        User user = new User(username, emailAddress);
        return userService.register(user, password);
    }

    private Rating createRating(Movie movie, User user, int originality, int soundtrack,
                                int narrative, int cinematography, int depth) {
        Rating rating = new Rating(movie, user, originality, soundtrack, narrative, cinematography, depth);
        return ratingService.create(rating);
    }

    private void addActedInMovies(Person actor, Movie... movies) {
        for (Movie movie : movies) {
            actor.addActsInMovie(movie);
        }
    }

    private void addDirectedMovies(Person director, Movie... movies) {
        for (Movie movie : movies) {
            director.addDirectedMovie(movie);
        }
    }

    private void addActors(Movie movie, Person... actors) {
        for (Person actor: actors) {
            actor.addActsInMovie(movie);
        }
    }

    private void updatePersons(Person... persons) {
        for (Person person : persons) {
            personService.update(person);
        }
    }
}
