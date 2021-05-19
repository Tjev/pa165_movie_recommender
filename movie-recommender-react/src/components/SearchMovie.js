import React, { useState } from 'react';

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
                <ul>
                    {movies.length === 0 ? "No results found" : movies.map((movie, i) => <li key={i}>{JSON.stringify({
                        title: movie.title,
                        bio: movie.bio
                    })}</li>)}
                </ul>
            </div>
        </div>
    );
}
