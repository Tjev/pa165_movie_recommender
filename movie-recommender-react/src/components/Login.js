import React, { useState } from "react";
import axios from "axios";
import "./Login.css";
import PropTypes from 'prop-types';
import { Redirect } from 'react-router'
import {Button, Grid, FormLabel, TextField, Typography, Box} from "@material-ui/core";
import PasswordField from 'material-ui-password-field'

export default function Login({token, setToken}) {

    const [emailAddress, setEmail] = useState('');
    const [password, setPassword] = useState('');

    if (token) {
        return <Redirect to="/search-person"/>
    }

    const HandleSubmit = async e => {
        e.preventDefault();
        await axios.post('http://localhost:8080/pa165/rest/users/auth', {emailAddress, password})
        .then((data) => {
            setToken(data.data);
        })
        .catch(error => {
            if (error.response && (error.response.status === 401 || error.response.status === 404)) {
                alert("Invalid email or password. Try again.")
                return;
            }
            alert("Some server-related problems occurred. Please try again.");
        });
    }

    return (
        <div className="login-wrapper">
            <Box mb={2}>
                <Typography variant="h4">Movie Recommender</Typography>
            </Box>

            <form onSubmit={HandleSubmit}>
                <Grid container spacing={2}>

                    <Grid item xs={2}>
                        <FormLabel>Email: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <TextField type="email"
                                   value={emailAddress}
                                   onChange={e => setEmail(e.target.value)}
                                   fullWidth
                                   name="email"/>
                    </Grid>

                    <Grid item xs={2}>
                        <FormLabel>Password: </FormLabel>
                    </Grid>
                    <Grid item xs={10}>
                        <PasswordField value={password}
                                       onInput={e => setPassword(e.target.value)}
                                       fullWidth
                                       name="password"/>
                    </Grid>

                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" type="submit">Login</Button>
                    </Grid>

                </Grid>
            </form>
        </div>
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
}
