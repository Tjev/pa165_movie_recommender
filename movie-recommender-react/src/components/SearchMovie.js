import React, {useEffect, useState} from 'react';
import {NavLink} from "react-router-dom";
import axios from "axios";
import {AddDirectorLink} from "./AddDirectorLink";
import {AddActorLink} from "./AddActorLink";
import {getUserId} from "../utils/Common";
import {Box, Button, Card, CardContent, Grid, TextField, Typography} from "@material-ui/core";

/**
 * @author Kristian Tkacik, Jiri Papousek
 */
function MovieList({ movies, scores, token }) {
    const [ratedMoviesIDs, setRatedMoviesIDs] = useState([]);

    useEffect(() => {
        if (!token) {
            return;
        }
        const getRatings = async () => {
            return await axios.get(`http://localhost:8080/pa165/rest/ratings/find-by-user?id=${getUserId()}`)
                .catch(console.log);
        }
        getRatings().then(ratings => {setRatedMoviesIDs(ratings.data.map(x => x.movie.id))});
    }, [])

    if (movies.length === 0) {
        return <p>No results found</p>;
    }
    return (
        <Box mt={1}>
            <Grid container  spacing={1}>
                {movies.map(({ id, title, bio, releaseYear, genres, directors, actors }, index) => (
                <Grid item xs={12}>
                    <Card fullwidth style={{backgroundColor: "#e6e6e6"}}>
                        <CardContent>
                            <Typography gutterBottom variant="h5" align="left" component="h2">
                                {title}
                            </Typography>
                            <Typography variant="body2" color="textSecondary" align="left" component="p">
                                {<p><b>Score: </b> {scores[index]} / 5</p>}
                                {<p><b>Bio: </b> {bio}</p>}
                                {<p><b>Release year: </b>{releaseYear}</p>}
                                {<p><b>Genres: </b>{genres.map(genre => genre.toString().slice(0, 1) + genre.toString().slice(1, genre.length).toLowerCase()).join(', ')}</p>}
                                {<p><b>Directed by: </b>{directors.map(director => director.name).join(', ')}</p>}
                                {<p><b>Actors: </b>{actors.map(actor => actor.name).join(', ')}</p>}
                                {AddActorLink(id, title, token)}
                                {AddDirectorLink(id, title, token)}
                                {token &&
                                    <Button variant="contained">
                                        <NavLink exact
                                                 activeClassName="active"
                                                 to={{
                                                     pathname:'/get-recommendations',
                                                     state: {id: id, title: title, token: token}
                                                 }}
                                                 style={{ textDecoration: 'none', color: 'black' }}>
                                            Search for movies like this
                                        </NavLink>
                                    </Button>
                                }
                                {token &&
                                    <Button variant="contained" disabled={ratedMoviesIDs.includes(id)}>
                                        <NavLink
                                                 activeClassName="active"
                                                 to={{
                                                     pathname:'/create-rating',
                                                     state: {id: id, title: title}
                                                 }}
                                                 style={{ textDecoration: 'none', color: 'black' }}>
                                            Rate this movie
                                        </NavLink>
                                    </Button>
                                }
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
            ))}
            </Grid>
        </Box>
    );
}

export function SearchMovie({token}) {
    const [title, setTitle] = useState('');
    const [movies, setMovies] = useState([]);
    const [scores, setScores] = useState([]);

    const handleSubmit = async (e) => {
        if (!title) return;
        e.preventDefault();
        await axios.get(`http://localhost:8080/pa165/rest/movies/find-by-title?title=${title}`)
            .then(res => {
                setMovies(res.data);
                return axios.all(res.data.map(
                    movie => axios.get(`http://localhost:8080/pa165/rest/movies/${movie.id}/overall-score`).then(res => res.data)));
            })
            .then( res => {
                setScores(res);
            });
    }

    return (
        <div>
            <div className="search-wrapper">
                <Box mb={2}>
                    <Typography variant="h4">Search Movie</Typography>
                </Box>
                <form onSubmit={handleSubmit}>
                    <Grid
                        container
                        direction="row"
                        justify="center"
                        alignItems="center"
                        spacing={2}
                    >
                        <Grid item>
                            <TextField value={title} onInput={e => setTitle(e.target.value)} placeholder="Enter movie title"/>
                        </Grid>
                        <Grid item>
                            <Button variant="contained" color="primary" type="submit">Search</Button>
                        </Grid>
                    </Grid>
                </form>
            </div>
            <div>
                <MovieList movies={movies} scores={scores} token={token}/>
            </div>
        </div>
    );
}
