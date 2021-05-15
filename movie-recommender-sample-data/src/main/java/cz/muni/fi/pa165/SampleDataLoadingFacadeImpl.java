package cz.muni.fi.pa165;

import javax.inject.Inject;

import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.PersonService;
import cz.muni.fi.pa165.service.RatingService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

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
    public void loadData() throws IOException { }
}
