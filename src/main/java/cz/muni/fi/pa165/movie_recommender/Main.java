package cz.muni.fi.pa165.movie_recommender;

import cz.muni.fi.pa165.movie_recommender.entity.Genre;
import cz.muni.fi.pa165.movie_recommender.entity.Movie;
import cz.muni.fi.pa165.movie_recommender.entity.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
                InMemoryDatabaseSpring.class);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Movie m = new Movie();
        m.setTitle("film");
        em.persist(m);

        Person p = new Person("ahoj", null, null);
        em.persist(p);

        //p.addActsInMovie(m);

        em.getTransaction().commit();

        Movie found = em.find(Movie.class, m.getId());
        System.out.println(found.getTitle());

        Person foundP = em.find(Person.class, p.getId());
        System.out.println(foundP.getName());
        //System.out.println(foundP.getActsInMovies());

        appContext.close();
    }
}
