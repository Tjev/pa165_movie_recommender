import React, {useState} from 'react';
import DatePicker from 'react-date-picker'
import axios from "axios";

export function CreateMovie() {
    const allMovieGenres = ["ACTION", "ADULT", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY", "CRIME", "DOCUMENTARY",
        "DRAMA", "FAMILY", "FANTASY", "NOIR", "HISTORY","HORROR", "MUSICAL", "MYSTERY", "NEWS", "REALITY", "ROMANCE",
        "SCIFI", "SPORT", "THRILLER", "WAR", "WESTERN"];

    const [movieTitle, setMovieTitle] = useState('');
    const [movieBio, setMovieBio] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [movieGenre, setMovieGenre] = useState(allMovieGenres[0]);
    const [movieGenres, setMovieGenres] = useState([]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        return await axios.post('http://localhost:8080/pa165/rest/movies/create',
            {
                title: movieTitle,
                bio: movieBio,
                releaseYear: releaseYear,
                genres: movieGenres,
            })
            .then(
                response => JSON.stringify(response))
            .then(
                () => alert("Movie added successfully"))
            .catch((msg) => alert(msg));
    }

    const handleAddGenre = e => {
        e.preventDefault();
        if (!movieGenres.includes(movieGenre)) {
            setMovieGenres([...movieGenres, movieGenre.toUpperCase()]);
        }
    }

    const handleClearGenres = e => {
        e.preventDefault();
        setMovieGenres([]);
    }

    const Options = ({ options }) => {
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
                    <tr>
                        <td>
                            <label>Genres:</label>
                        </td>
                        <td>
                            <p style={{textAlign: "left"}}>
                                {movieGenres.length > 0
                                    ? movieGenres.map(genre => genre.toString().slice(0, 1).toUpperCase() + genre.toString().slice(1, genre.length).toLowerCase()).join(', ')
                                    : "(Choose at least one genre)"}</p>
                            <select value={movieGenre} onChange={e => setMovieGenre(e.target.value)}>
                                <Options options={allMovieGenres}/>
                            </select>
                            <button onClick={handleAddGenre}>Add genre</button>
                            <button onClick={handleClearGenres}>Clear genres</button>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="Submit" />
            </form>
        </div>
    )

}