import React, {useState} from 'react';
import axios from "axios";
import {Box, Button, Typography, TextField, Grid, FormLabel, MenuItem} from "@material-ui/core";
import BrokenImageIcon from '@material-ui/icons/BrokenImage';
import {backendURL} from "../Constants";

export function CreateMovie() {
    const allMovieGenres = ["ACTION", "ADULT", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY", "CRIME", "DOCUMENTARY",
        "DRAMA", "FAMILY", "FANTASY", "NOIR", "HISTORY","HORROR", "MUSICAL", "MYSTERY", "NEWS", "REALITY", "ROMANCE",
        "SCIFI", "SPORT", "THRILLER", "WAR", "WESTERN"];

    const [movieTitle, setMovieTitle] = useState('');
    const [movieBio, setMovieBio] = useState('');
    const [releaseYear, setReleaseYear] = useState('1895-12-28');
    const [movieGenre, setMovieGenre] = useState(allMovieGenres[0]);
    const [movieGenres, setMovieGenres] = useState([]);
    const [isFormInvalid, setIsFormInvalid] = useState(false);
    const [movieGraphicsURL, setMovieGraphicsURL] = useState('');
    const [movieGraphicsFile, setMovieGraphicsFile] = useState(null);

    const reader = new FileReader();

    const validate = () => {
        if (movieTitle !== "" && releaseYear !== "" && movieGenres.length !== 0) {
            setIsFormInvalid(false);
        } else {
            setIsFormInvalid(true);
        }
    }

    const toBase64 = file => new Promise((resolve, reject) => {
        reader.onload = () => {resolve(reader.result.split(',')[1])};
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (isFormInvalid) {
            return;
        }

        let serializedGraphics = '';
        if (movieGraphicsFile) {
            serializedGraphics = await toBase64(movieGraphicsFile);
        }

        return await axios.post(`http://${backendURL}:8080/pa165/rest/movies/create`,
            {
                title: movieTitle,
                bio: movieBio,
                releaseYear: releaseYear,
                genres: movieGenres,
                graphics: serializedGraphics,
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

    const handleFileUpload = (e) => {
        let file = e.target.files[0]
        if (!file) {
            return;
        }
        let fileURL = URL.createObjectURL(file);
        setMovieGraphicsFile(file);
        setMovieGraphicsURL(fileURL);
    }

    const noImage = () => {
        return (
            <Grid container direction="column" style={{"display": "flex", "width": "95%", "height": "95%", "margin": "5px", "alignItems": "center", "justifyContent": "center", "backgroundColor": "white"}}>
                <BrokenImageIcon style={{fontSize: 150}}/>
                <Typography>No graphics.</Typography>
            </Grid>
        )
    }

    return (
        <div className="create-movie-wrapper">

            <Box mb={2}>
                <Typography variant="h4">Create movie</Typography>
            </Box>

            <form onSubmit={handleSubmit}>

                <Grid container spacing={2}>
                    <Grid item container direction="column" spacing={2} xs={9}>
                        <Grid item container alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Title: </FormLabel>
                            </Grid>
                            <Grid item xs={10} style={{"margin-top": "7px"}}>
                                <TextField type="text"
                                    value={movieTitle}
                                    onChange={e => setMovieTitle(e.target.value)}
                                    fullWidth
                                    name="title"
                                    error={isFormInvalid && movieTitle === ""}
                                    helperText={isFormInvalid && movieTitle === "" ? "Title cannot be empty!" : " "}
                                    inputStyle={{"textAlign": "center"}}
                                />
                            </Grid>
                        </Grid>

                        <Grid item container alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Bio (optional): </FormLabel>
                            </Grid>
                            <Grid item xs={10} style={{"margin-top": "7px"}}>
                                <TextField type="text"
                                   value={movieBio}
                                   onChange={e => setMovieBio(e.target.value)}
                                   fullWidth
                                   name="bio"
                                   helperText=" "
                                />
                            </Grid>
                        </Grid>

                        <Grid item container alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Release date: </FormLabel>
                            </Grid>
                            <Grid item xs={10} style={{"margin-top": "7px"}}>
                                <TextField type="date"
                                   value={releaseYear}
                                   onChange={e => setReleaseYear(e.target.value)}
                                   fullWidth
                                   error={isFormInvalid && releaseYear === ""}
                                   helperText={isFormInvalid && releaseYear === "" ? "Release date cannot be empty!" : " "}/>
                            </Grid>
                        </Grid>

                        <Grid item container spacing={2} alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Add genres: </FormLabel>
                            </Grid>
                            <Grid item style={{"margin-top": "7px"}}>
                                <TextField
                                    select
                                    value={movieGenre} onChange={e => setMovieGenre(e.target.value)}
                                    error={isFormInvalid && movieGenres.length === 0}
                                    helperText={isFormInvalid && movieGenres.length === 0 ? "At least one genre!" : " "}>
                                    {allMovieGenres.map((option, i) =>
                                        <MenuItem key={i} value={option}>
                                            {reformatMovieGenre(option)}
                                        </MenuItem>
                                    )}
                                </TextField>
                            </Grid>

                            <Grid item>
                                <Button variant="contained" onClick={handleAddGenre} disabled={movieGenres.includes(movieGenre.toUpperCase())}>
                                    Add genre
                                </Button>
                            </Grid>
                        </Grid>

                        <Grid item container spacing={2} alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Chosen genres: </FormLabel>
                            </Grid>
                            <Grid item container xs={8} justify="flex-start">
                                <Typography>
                                    {movieGenres.length > 0
                                    ? movieGenres.map(genre => reformatMovieGenre(genre)).join(', ')
                                    : "(Choose at least one genre)"}
                                </Typography>
                            </Grid>
                            <Grid item xs={2}>
                                <Button variant="contained" onClick={handleClearGenres} disabled={movieGenres.length === 0}>
                                    Clear genres
                                </Button>
                            </Grid>
                        </Grid>

                    </Grid>

                    <Grid item container direction="column" xs={3} justify="flex-start">
                        <Grid item>
                            <input
                            type="file"
                            id="contained-file-button"
                            accept=".jpeg, .png, .jpg"
                            onChange={handleFileUpload}
                            hidden
                            />
                            <label htmlFor="contained-file-button">
                                <Button variant="contained" color="primary" component="span" style={{"marginBottom": "15px"}}>
                                    Upload graphics
                                </Button>
                            </label>
                        </Grid>
                        <Grid item justify="center">
                            { movieGraphicsURL
                            ? <img src={movieGraphicsURL} style={{"max-width": "95%", "margin": "5px"}}/>
                            : noImage()
                            }
                        </Grid>
                    </Grid>
                </Grid>

                <Box xs={12} justify="center" spacing={2} style={{"marginTop": "15px"}}>
                    <Button variant="contained" color="primary" type="submit" onClick={validate}>
                        Submit
                    </Button>
                </Box>

            </form>
        </div>
    );
}
