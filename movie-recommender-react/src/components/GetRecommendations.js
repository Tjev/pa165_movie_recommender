import React, { useState, useEffect } from 'react';
import {NavLink, useLocation} from "react-router-dom";
import axios from "axios";
import {AddActorLink} from "./AddActorLink";
import {AddDirectorLink} from "./AddDirectorLink";

/**
 * @author Kristian Tkacik, Jiri Papousek
 */
function MovieList({ movies, scores, token, setMovies, setScores}) {
    let location = useLocation();

    if (movies.length === 0) {
        return <p>No results found</p>;
    }

    const handleNewRecommend = async (e) => {
        await axios.get(`http://localhost:8080/pa165/rest/movies/${e.target.value}/recommendations?amount=10`)
            .then(res => {
                setMovies(res.data);
                return axios.all(res.data.map(
                    movie => axios.get(`http://localhost:8080/pa165/rest/movies/${movie.id}/overall-score`).then(res => res.data)));
            })
            .then( res => {
                setScores(res);
            })
    }

    return (
        <ul>
            {movies.map(({ id, title, bio, releaseYear, genres, directors, actors }, index) => (
                <li key={id}>
                    <div>
                        <h2>{title}</h2>
                    </div>
                    {<p><b>Score: </b> {scores[index]} / 5</p>}
                    {<p><b>Bio: </b> {bio}</p>}
                    {<p><b>Release year: </b>{releaseYear}</p>}
                    {<p><b>Genres: </b>{genres.map(genre => genre.toString().slice(0, 1) + genre.toString().slice(1, genre.length).toLowerCase()).join(', ')}</p>}
                    {<p><b>Directed by: </b>{directors.map(director => director.name).join(', ')}</p>}
                    {<p><b>Actors: </b>{actors.map(actor => actor.name).join(', ')}</p>}
                    {AddActorLink}
                    {AddDirectorLink(id, title, token)}
                    <NavLink exact activeClassName="active" to={{
                        pathname:'/get-recommendations',
                        state: {id: id, title: title}
                    }} >
                        <button value={id} type="button" onClick={handleNewRecommend}>Search for movies like this</button>
                    </NavLink>
                </li>
            ))}
        </ul>
    );
}

export function GetRecommendations() {
    let location = useLocation();

    const [movies, setMovies] = useState([]);
    const [scores, setScores] = useState([]);

    useEffect(async () => {
        await axios.get(`http://localhost:8080/pa165/rest/movies/${location.state.id}/recommendations?amount=10`)
            .then(res => {
                setMovies(res.data);
                return axios.all(res.data.map(
                    movie => axios.get(`http://localhost:8080/pa165/rest/movies/${movie.id}/overall-score`).then(res => res.data)));
            })
            .then( res => {
                setScores(res);
            })
    }, []);

    return (
        <div>
            <h1>Recommendations for {location.state.title}</h1>
            <div>
                <MovieList movies={movies} scores={scores} token={location.state.token} setMovies={setMovies} setScores={setScores} />
            </div>
        </div>
    );
}
