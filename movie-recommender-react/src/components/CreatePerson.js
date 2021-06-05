import React, {useState} from 'react';
import axios from "axios";
import {Box, Button, FormLabel, Grid, TextField, Typography} from "@material-ui/core";

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
        return await axios.post('http://localhost:8080/pa165/rest/persons/create',
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

                    <Grid item xs={2}>
                        <FormLabel>Name: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField type="text"
                                   value={personName}
                                   onChange={e => setPersonName(e.target.value)}
                                   fullWidth
                                   name="name"
                                   placeholder="Enter person name"
                                   error={isFormInvalid && personName === ""}
                                   helperText={isFormInvalid && personName === "" ? "Empty field!" : " "} />
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Bio: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField value={personBio}
                                   onChange={e => setPersonBio(e.target.value)}
                                   name="bio"
                                   multiline
                                   rows={4}
                                   placeholder="Enter person bio"
                                   fullWidth
                                   error={isFormInvalid && personBio === ""}
                                   helperText={isFormInvalid && personBio === "" ? "Empty field!" : " "} />
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Date of birth: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField type="date"
                                   value={dateOfBirth}
                                   onChange={e => setDateOfBirth(e.target.value)}
                                   fullWidth />
                    </Grid>

                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" type="submit" onClick={validate}>
                            Submit
                        </Button>
                    </Grid>

                </Grid>
            </form>
        </div>
    );
}
