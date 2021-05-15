package cz.muni.fi.pa165;

import javax.inject.Inject;

import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.PersonService;
import cz.muni.fi.pa165.service.RatingService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
        Person p1 = createPerson("Brad Pitt", LocalDate.of(1963, 12, 18), "William Bradley Pitt is an American actor and film producer. He has received multiple awards, including two Golden Globe Awards and an Academy Award for his acting, in addition to another Academy Award, another Golden Globe Award and a Primetime Emmy Award as a producer under his production company, Plan B Entertainment.");
        Person p2 = createPerson("Quentin Tarantino", LocalDate.of(1963, 3, 27), "Quentin Jerome Tarantino is an American film director, screenwriter, producer, and actor. His films are characterized by nonlinear storylines, dark humor, stylized violence, extended dialogue, ensemble casts, references to popular culture, alternate history, and neo-noir.");
        Person p3 = createPerson("Emma Stone", LocalDate.of(1988, 11, 6), "Emily Jean \"Emma\" Stone is an American actress. The recipient of various accolades, including an Academy Award, a British Academy Film Award, and a Golden Globe Award, she was the world's highest-paid actress in 2017.");
        Person p4 = createPerson("Ruben Fleischer", LocalDate.of(1974, 10, 31), "Ruben Samuel Fleischer is an American film director, film producer, television producer, music video director, and commercial director who lives in Los Angeles.");

        Movie m1 = createMovie("Zombieland", "A shy student trying to reach his family in Ohio, a gun-toting bruiser in search or the last Twinkie and a pair of sisters striving to get to an amusement park join forces in a trek across a zombie-filled America.", LocalDate.of(2009, 1, 1), Set.of(Genre.ACTION, Genre.HORROR), null);
        Movie m2 = createMovie("Once Upon a Time... in Hollywood", "A faded television actor and his stunt double strive to achieve fame and success in the final years of Hollywood's Golden Age in 1969 Los Angeles.", LocalDate.of(2019, 1, 1), Set.of(Genre.COMEDY, Genre.DRAMA), null);

        m1.addActor(p3);
        m2.addActor(p1);

        m1.addDirector(p4);
        m2.addDirector(p2);

        User u1 = createUser("Rado", "rado@email.com", "radoheslo");
        User u2 = createUser("Kiko", "kiko@email.com", "kikoheslo");
        User u3 = createUser("Papi", "papi@email.com", "papiheslo");
        User u4 = createUser("Jevo", "jevo@email.com", "jevoheslo");

        u4.setAdmin(true);
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
}
