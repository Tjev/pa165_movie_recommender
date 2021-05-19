import React, { useState } from 'react';
import {NavLink} from "react-router-dom";

function MovieList({ movies }) {
    if (movies.length === 0) {
        return <p>No results found</p>;
    }
    return (
        <ul>
            {movies.map(({ id, title, bio, releaseYear, genres, directors, actors }) => (
                <li key={id}>
                    <div>
                        <h2>{title}</h2>
                    </div>
                    {<p><b>Bio: </b> {bio}</p>}
                    {<p><b>Release year: </b>{releaseYear}</p>}
                    {<p><b>Genres: </b>{genres.map(genre => genre.toString().slice(0, 1) + genre.toString().slice(1, genre.length).toLowerCase()).join(', ')}</p>}
                    {<p><b>Directed by: </b>{directors.map(director => director.name).join(', ')}</p>}
                    {<p><b>Actors: </b>{actors.map(actor => actor.name).join(', ')}</p>}
                    <NavLink exact activeClassName="active" to={{
                        pathname:'/add-actor',
                        state: {id: id, title: title, }
                    }} >
                        <button type="button">Add actor</button>
                    </NavLink>
                    <NavLink exact activeClassName="active" to={{
                        pathname:'/add-director',
                        state: {id: id, title: title, directors: directors}
                    }} >
                        <button type="button">Add director</button>
                    </NavLink>
                </li>
            ))}
        </ul>
    );
}

export function SearchMovie() {
    const [title, setTitle] = useState('');
    const [movies, setMovies] = useState([]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await fetch(`http://localhost:8080/pa165/rest/movies/find-by-title?title=${title}`)
            .then(res => res.json())
            .then((data) => {
                setMovies(data);
            })
            .catch(console.log);
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
                <MovieList movies={movies} />
            </div>
        </div>
    );
}
