import React, {useState} from 'react';
import axios from "axios";
import {Box, Button, Typography, TextField, Grid, FormLabel, MenuItem} from "@material-ui/core";

export function CreateMovie() {
    const allMovieGenres = ["ACTION", "ADULT", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY", "CRIME", "DOCUMENTARY",
        "DRAMA", "FAMILY", "FANTASY", "NOIR", "HISTORY","HORROR", "MUSICAL", "MYSTERY", "NEWS", "REALITY", "ROMANCE",
        "SCIFI", "SPORT", "THRILLER", "WAR", "WESTERN"];

    const [movieTitle, setMovieTitle] = useState('');
    const [movieBio, setMovieBio] = useState('');
    const [releaseYear, setReleaseYear] = useState('1895-12-28');
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
        if (!movieGenres.includes(movieGenre.toUpperCase())) {
            setMovieGenres([...movieGenres, movieGenre.toUpperCase()]);
            setMovieGenre(allMovieGenres[0])
        }
    }

    const handleClearGenres = e => {
        e.preventDefault();
        setMovieGenres([]);
    }

    function reformatMovieGenre(genreToReformat) {
        return genreToReformat.toString().slice(0, 1).toUpperCase()
            + genreToReformat.toString().slice(1, genreToReformat.length).toLowerCase();
    }

    return (
        <div className="create-movie-wrapper">
            <Box mb={2}>
                <Typography variant="h4">Create movie</Typography>
            </Box>

            <form onSubmit={handleSubmit}>
                <Grid container spacing={2}>

                    <Grid item xs={2}>
                        <FormLabel>Title: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField type="text"
                                   value={movieTitle}
                                   onChange={e => setMovieTitle(e.target.value)}
                                   fullWidth
                                   name="title"/>
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Bio (optional): </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField type="text"
                                   value={movieBio}
                                   onChange={e => setMovieBio(e.target.value)}
                                   fullWidth
                                   name="bio"/>
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Release date: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField type="date"
                                   value={releaseYear}
                                   onChange={e => setReleaseYear(e.target.value)}
                                   fullWidth/>
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Add genre: </FormLabel>
                    </Grid>
                    <Grid item xs={4}>
                        <TextField select value={movieGenre} onChange={e => setMovieGenre(e.target.value)}>
                            {allMovieGenres.map((option, i) =>
                                <MenuItem key={i} value={option}>
                                    {reformatMovieGenre(option)}
                                </MenuItem>
                            )}
                        </TextField>

                    </Grid>
                    <Grid item xs={6}>
                        <Button variant="contained" onClick={handleAddGenre} fullWidth
                                disabled={movieGenres.includes(movieGenre.toUpperCase())}>
                            Add genre
                        </Button>
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Chosen genres: </FormLabel>
                    </Grid>
                    <Grid item xs={5}>
                        <Typography>{movieGenres.length > 0
                            ? movieGenres.map(genre => reformatMovieGenre(genre)).join(', ')
                            : "(Choose at least one genre)"}</Typography>
                    </Grid>
                    <Grid item xs={5}>
                        <Button variant="contained" onClick={handleClearGenres} disabled={movieGenres.length === 0}>
                            Clear genres
                        </Button>
                    </Grid>

                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" fullWidth type="submit">Submit</Button>
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}
