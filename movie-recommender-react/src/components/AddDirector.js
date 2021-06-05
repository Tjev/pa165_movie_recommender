import React, {useReducer, useState} from 'react';
import axios from "axios";
import {Redirect, useLocation} from "react-router-dom";
import {Box, Button, Card, CardContent, Grid, TextField, Typography} from "@material-ui/core";

/**
 * @author Jiri Papousek
 */
function PersonList({ persons, movieId }) {

    const [submitted, setSubmitted] = useState(false);

    if (submitted) {
        return <Redirect to="/search-movie"/>;
    }

    const handleAddDirector = async (id) => {
        const movie = await axios.get(`http://localhost:8080/pa165/rest/movies/${movieId}`).catch(console.log);

        movie.data.directors.push({id: id});

        return await axios.put('http://localhost:8080/pa165/rest/movies/update',
            movie.data)
            .then(
                response => JSON.stringify(response))
            .then(
                () => {
                    alert("Director successfully added");
                    setSubmitted(true);
                })
            .catch((msg) => alert(msg))
    }

    if (persons.length === 0) {
        return <p>No results found</p>;
    }

    return (
        <Box mt={1}>
            <Grid container  spacing={1}>
                {persons.map(({ id, name, bio, dateOfBirth }) => (
                    <Grid item xs={12}>
                        <Card fullwidth style={{backgroundColor: "#e6e6e6"}}>
                            <CardContent>
                                <Typography gutterBottom variant="h5" align="left" component="h2">
                                    {name}
                                </Typography>
                                <Typography variant="body2" color="textSecondary" align="left" component="p">
                                    {<p><b>Bio: </b> {bio}</p>}
                                    {<p><b>Date of birth: </b>{dateOfBirth}</p>}
                                    <Button variant="contained" onClick={handleAddDirector.bind(this, id)}>Add director</Button>
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}


export function AddDirector() {
    let location = useLocation();

    const [personName, setPersonName] = useState('');
    const [persons, setPersons] = useState([]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await fetch(`http://localhost:8080/pa165/rest/persons/find-by-name?name=${personName}`)
            .then(res => res.json())
            .then((data) => {
                let directorIds = location.state.directors.map(director => director.id);
                let candidates = data.filter(person => {
                    return !(directorIds.includes(person.id));
                });
                setPersons(candidates);
            })
            .catch(console.log);
    }

    return (
        <div>
            <div className="add-director-wrapper">
                <Box mb={2}>
                    <Typography variant="h4">Search for New Director</Typography>
                </Box>
                <form onSubmit={handleSubmit}>
                    <Grid
                        container
                        direction="row"
                        justify="center"
                        alignItems="center"
                        spacing={2}
                    >
                        <Grid item xs={11}>
                            <TextField fullWidth value={personName} onInput={e => setPersonName(e.target.value)} placeholder="Enter person name"/>
                        </Grid>
                        <Grid item xs={1}>
                            <Button fullWidth variant="contained" color="primary" type="submit">Search</Button>
                        </Grid>

                    </Grid>
                </form>
            </div>
            <div>
                <PersonList persons={persons} movieId={location.state.id}/>
            </div>
        </div>
    );
}