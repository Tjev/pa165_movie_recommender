import React, {useState} from 'react';
import axios from "axios";
import {useLocation} from "react-router-dom";
import {Box, Button, Grid, TextField, Typography} from "@material-ui/core";

/**
 * @author Jiri Papousek
 */
function PersonList({ persons, movieId }) {

    const handleAddDirector = async (e) => {
        e.preventDefault();
        const movie = await axios.get(`http://localhost:8080/pa165/rest/movies/${movieId}`).catch(console.log);

        movie.data.directors.push({id: e.target.value});

        return await axios.put('http://localhost:8080/pa165/rest/movies/update',
            movie.data)
            .then(
                response => JSON.stringify(response))
            .then(
                () => alert("Director successfully added"))
            .catch((msg) => alert(msg))
    }

    if (persons.length === 0) {
        return <p>No results found</p>;
    }
    return (
        <ul>
            {persons.map(({ id, name, bio, dateOfBirth }) => (
                <li key={id}>
                    <div>
                        <h2>{name}</h2>
                    </div>
                    {<p><b>Bio: </b> {bio}</p>}
                    {<p><b>Date of birth: </b>{dateOfBirth}</p>}
                    <button value={id} onClick={handleAddDirector}>Add director</button>
                </li>
            ))}
        </ul>
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
                setPersons(data);
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
                        <Grid item>
                            <TextField value={personName} onInput={e => setPersonName(e.target.value)} placeholder="Enter person name"/>
                        </Grid>
                        <Grid item>
                            <Button variant="contained" color="primary" type="submit">Search</Button>
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