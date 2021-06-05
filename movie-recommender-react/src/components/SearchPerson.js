import React, {useState} from 'react';
import { theme } from "../utils/Common";
import {
    Box,
    Button,
    Card,
    CardActionArea,
    CardContent,
    Grid,
    MuiThemeProvider,
    TextField,
    Typography
} from "@material-ui/core";

/**
 * @author Kristian Tkacik, Jiri Papousek
 */
function PersonList({ persons }) {
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
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
            ))}
        </Grid>
        </Box>
    );
}

export function SearchPerson() {
    const [personName, setPersonName] = useState('');
    const [persons, setPersons] = useState([]);

    const handleSubmit = async (e) => {
        if (!personName) return;
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
                <div className="search-wrapper">
                    <Box mb={2}>
                        <Typography variant="h4">Search Person</Typography>
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
                    <PersonList persons={persons} />
                </div>
        </div>
    );
}
