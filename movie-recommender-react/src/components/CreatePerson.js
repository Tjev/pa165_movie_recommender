import React, {useState} from 'react';
import axios from "axios";
import {Box, Button, FormLabel, Grid, TextField, Typography} from "@material-ui/core";
import {backendURL} from "../Constants";

/**
 * @author Kristian Tkacik
 */
export function CreatePerson() {
    const [personName, setPersonName] = useState('');
    const [personBio, setPersonBio] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [isFormInvalid, setIsFormInvalid] = useState(false);

    const validate = () => {
        if (personName !== "" && personBio !== "") {
            setIsFormInvalid(false);
        } else {
            setIsFormInvalid(true);
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        return await axios.post(`http://${backendURL}:8080/pa165/rest/persons/create`,
            {
            name: personName,
            bio: personBio,
            dateOfBirth: dateOfBirth})
            .then(
                response => JSON.stringify(response))
            .then(() => alert("Person added successfully"))
            .catch((msg) => console.log(msg))
    }

    return (
        <div className="create-person-wrapper">
            <Box mb={2}>
                <Typography variant="h4">Create Person</Typography>
            </Box>

            <form onSubmit={handleSubmit} autoComplete="off">

                <Grid container spacing={2}>
                    <Grid item container direction="column" spacing={2} xs={11}>

                        <Grid item container alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Name: </FormLabel>
                            </Grid>
                            <Grid item xs={10} style={{"margin-top": "7px"}}>
                                <TextField
                                    type="text"
                                    value={personName}
                                    onChange={e => setPersonName(e.target.value)}
                                    fullWidth
                                    name="name"
                                    placeholder="Enter person name"
                                    error={isFormInvalid && personName === ""}
                                    helperText={isFormInvalid && personName === "" ? "Empty field!" : " "}
                                />
                            </Grid>
                        </Grid>

                        <Grid item container alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Bio: </FormLabel>
                            </Grid>
                            <Grid item xs={10} style={{"margin-top": "7px"}}>
                                <TextField
                                    value={personBio}
                                    onChange={e => setPersonBio(e.target.value)}
                                    name="bio"
                                    multiline
                                    rows={4}
                                    variant="outlined"
                                    placeholder="Enter person bio"
                                    fullWidth
                                    error={isFormInvalid && personBio === ""}
                                    helperText={isFormInvalid && personBio === "" ? "Empty field!" : " "}
                                />
                            </Grid>
                        </Grid>

                        <Grid item container alignItems="center">
                            <Grid item container xs={2} justify="flex-start">
                                <FormLabel>Date of birth: </FormLabel>
                            </Grid>
                            <Grid item xs={10} style={{"margin-top": "7px"}}>
                                <TextField
                                    type="date"
                                    value={dateOfBirth}
                                    onChange={e => setDateOfBirth(e.target.value)}
                                    fullWidth
                                />
                            </Grid>
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
