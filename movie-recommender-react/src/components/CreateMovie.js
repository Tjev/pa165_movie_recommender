import React, {useState} from 'react';
import DatePicker from 'react-date-picker'
import axios from "axios";

export function CreateMovie() {
    const [movieTitle, setMovieTitle] = useState('');
    const [movieBio, setMovieBio] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [movieGenre, setMovieGenre] = useState('');
    const [movieGenres, setMovieGenres] = useState([]);

    let allMovieGenres = ["ACTION", "ADVENTURE", "ADULT"];

    const handleSubmit = async (e) => {
        e.preventDefault();
        return await axios.post('http://localhost:8080/pa165/rest/movies/create',
            {
                title: movieTitle,
                bio: movieBio,
                releaseYear: releaseYear})
            .then(
                response => JSON.stringify(response))
            .then(
                () => alert("Movie added successfully"))
            .catch((msg) => alert(msg))
    }

    const handleAddGenre = async (e) => {
        e.preventDefault();
        if (!movieGenres.includes(movieGenre)) {
            movieGenres.push(movieGenre)
        }
        setMovieGenres(movieGenres)
        console.log(movieGenres);
    }

    const handleClearGenres = async (e) => {
        e.preventDefault();
        setMovieGenres([]);
    }

    function Options({ options }) {
        return (
            options.map((option, i) =>
                <option key={i} value={option.toString().toLowerCase()}>
                    {option.toString()}
                </option>)
        );
    }

    return (
        <div className="create-movie-wrapper">
            <h1>Create movie:</h1>
            <form onSubmit={handleSubmit}>
                <table>
                    <tr>
                        <td>
                            <label>
                                Title:
                            </label>
                        </td>
                        <td>
                            <input type="text"
                                   value={movieTitle}
                                   onChange={e => setMovieTitle(e.target.value)}
                                   name="title"
                                   placeholder="Enter movie"
                                   style={{width: 700}} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Bio:
                            </label>
                        </td>
                        <td>
                            <textarea value={movieBio}
                                      onChange={e => setMovieBio(e.target.value)}
                                      name="bio"
                                      placeholder="Enter movie bio"
                                      style={{width: 700, height: 100}} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Release year:
                            </label>
                        </td>
                        <td>
                            <DatePicker value={releaseYear} onChange={setReleaseYear} />
                        </td>
                    </tr>
                </table>
                <label>
                    Genres:
                    <p>{movieGenres.map(genre => genre.toString().slice(0, 1) + genre.toString().slice(1, genre.length).toLowerCase()).join(', ')}</p>
                    <select value={movieGenre} onChange={e => setMovieGenre(e.target.value)}>
                        <Options options={allMovieGenres}/>
                    </select>
                </label>
                <button onClick={handleAddGenre}>Add genre</button>
                <button onClick={handleClearGenres}>Clear genres</button>
                <input type="submit" value="Submit" />
            </form>
        </div>
    )

}