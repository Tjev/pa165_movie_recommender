import React, { useState } from 'react';
import {NavLink} from "react-router-dom";
import axios from "axios";
import {AddDirectorLink} from "./AddDirectorLink";
import {AddActorLink} from "./AddActorLink";

/**
 * @author Kristian Tkacik, Jiri Papousek
 */
function MovieList({ movies, scores, token }) {

    if (movies.length === 0) {
        return <p>No results found</p>;
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
                    {AddActorLink(id, title, token)}
                    {AddDirectorLink(id, title, token)}
                    <NavLink exact activeClassName="active" to={{
                        pathname:'/get-recommendations',
                        state: {id: id, title: title, token: token}
                    }} >
                        <button type="button">Search for movies like this</button>
                    </NavLink>
                </li>
            ))}
        </ul>
    );
}

export function SearchMovie({token}) {
    const [title, setTitle] = useState('');
    const [movies, setMovies] = useState([]);
    const [scores, setScores] = useState([]);

    const handleSubmit = async (e) => {
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
                <h1>Search movie:</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        Title:
                        <input type="text"
                               value={title}
                               onChange={e => setTitle(e.target.value)}
                               name="title"
                               placeholder="Enter movie title"/>
                    </label>
                    <input type="submit" value="Search" />
                </form>
            </div>
            <div>
                <MovieList movies={movies} scores={scores} token={token}/>
            </div>
        </div>
    );
}
