import React, { useState, useEffect } from 'react';
import {NavLink, useLocation} from "react-router-dom";
import axios from "axios";
import {AddActorLink} from "./AddActorLink";
import {AddDirectorLink} from "./AddDirectorLink";
import {Box, Button, Card, CardContent, Grid, Typography} from "@material-ui/core";
import {formatYear} from "../utils/Common";
import BrokenImageIcon from '@material-ui/icons/BrokenImage';
import {backendURL} from "../Constants";

/**
 * @author Kristian Tkacik, Jiri Papousek
 */
function MovieList({ movies, scores, token, setMovies, setScores, setTitle}) {
    let location = useLocation();

    if (movies.length === 0) {
        return <p>No results found</p>;
    }

    const noImage = () => {
        return (
            <Grid container direction="column" style={{
                "display": "flex",
                "width": "95%",
                "height": "95%",
                "margin": "5px",
                "alignItems": "center",
                "justifyContent": "center",
                "backgroundColor": "white"
            }}>
                <BrokenImageIcon style={{fontSize: 150}}/>
                <Typography>No graphics.</Typography>
            </Grid>
        )
    }

    const handleNewRecommend = async (id, title) => {
        setTitle(title);
        await axios.get(`http://${backendURL}:8080/pa165/rest/movies/${id}/recommendations?amount=10`)
            .then(res => {
                setMovies(res.data);
                return axios.all(res.data.map(
                    movie => axios.get(`http://${backendURL}:8080/pa165/rest/movies/${movie.id}/overall-score`).then(res => res.data)));
            })
            .then(res => {
                setScores(res);
            })
    }

    return (
        <Box mt={1}>
            <Grid container spacing={1}>
                {movies.map(({id, title, bio, releaseYear, genres, directors, actors, graphics}, index) => (
                    <Grid item xs={12}>
                        <Card fullwidth style={{backgroundColor: "#e6e6e6"}}>
                            <CardContent style={{"paddingBottom": "16px"}}>
                                <Grid container>
                                    <Grid item container direction="column" xs={9} justify="space-between">
                                        <Grid item>
                                            <Typography gutterBottom variant="h5" align="left" component="h2">
                                                {title}
                                            </Typography>
                                        </Grid>
                                        <Grid item>
                                            <Typography variant="body2" color="textSecondary" align="left"
                                                        component="p">
                                                {<p><b>Score: </b> {scores[index]} / 5</p>}
                                                {<p><b>Description: </b> {bio}</p>}
                                                {<p><b>Release year: </b>{formatYear(releaseYear)}</p>}
                                                {<p>
                                                    <b>Genres: </b>{genres.map(genre => genre.toString().slice(0, 1) + genre.toString().slice(1, genre.length).toLowerCase()).join(', ')}
                                                </p>}
                                                {<p><b>Directed
                                                    by: </b>{directors.map(director => director.name).join(', ')}</p>}
                                                {<p><b>Actors: </b>{actors.map(actor => actor.name).join(', ')}</p>}
                                            </Typography>
                                        </Grid>
                                        <Grid item container spacing={2}>
                                            {AddActorLink(id, title, token)}
                                            {AddDirectorLink(id, title, token)}
                                            <Grid item>
                                                <Button variant="contained"
                                                        onClick={handleNewRecommend.bind(this, id, title)}>
                                                    <NavLink exact
                                                             activeClassName="active"
                                                             to={{
                                                                 pathname: '/get-recommendations',
                                                                 state: {id: id, title: title}
                                                             }}
                                                             style={{textDecoration: 'none', color: 'black'}}>
                                                        Search for movies like this
                                                    </NavLink>
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={3} justify="center">
                                        {graphics
                                            ? <img src={"data:image/jpg;base64," + graphics}
                                                   style={{"maxWidth": "95%", "margin": "5px"}}/>
                                            : noImage()
                                        }
                                    </Grid>
                                </Grid>
                            </CardContent>

                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}

export function GetRecommendations() {
    let location = useLocation();

    const [movies, setMovies] = useState([]);
    const [scores, setScores] = useState([]);
    const [title, setTitle] = useState([]);

    useEffect(async () => {
        setTitle(location.state.title)
        await axios.get(`http://${backendURL}:8080/pa165/rest/movies/${location.state.id}/recommendations?amount=10`)
            .then(res => {
                setMovies(res.data);
                return axios.all(res.data.map(
                    movie => axios.get(`http://${backendURL}:8080/pa165/rest/movies/${movie.id}/overall-score`).then(res => res.data)));
            })
            .then( res => {
                setScores(res);
            })
    }, []);

    return (
        <div>
            <Box mb={2}>
                <Typography variant="h4">Recommendations for {title}</Typography>
            </Box>
            <div>
                <MovieList movies={movies} scores={scores} token={location.state.token}
                           setMovies={setMovies} setScores={setScores} setTitle={setTitle} />
            </div>
        </div>
    );
}
