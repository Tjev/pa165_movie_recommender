import React, {useState} from "react";
import {Redirect, useLocation} from "react-router-dom";
import axios from "axios";
import {getUserId} from "../utils/Common";
import {Box, Button, FormLabel, Grid, TextField, Typography, MenuItem} from "@material-ui/core";
import {backendURL} from "../Constants";

/**
 * @author Radoslav Chudovsky
 */
const RatingSelect = ({rating, setter}) => {
    const values = [1, 2, 3, 4, 5]

    return (
        <TextField select name={3} value={rating} onChange={e => setter(e.target.value)}>
            {values.map((option, i) => <MenuItem key={i} value={option}>{option}</MenuItem>)}
        </TextField>
    )
}

export function CreateRating() {
    let location = useLocation();

    const [originality, setOriginality] = useState(1);
    const [soundtrack, setSoundtrack] = useState(1);
    const [narrative, setNarrative] = useState(1);
    const [cinematography, setCinematography] = useState(1);
    const [depth, setDepth] = useState(1);

    const [submitted, setSubmitted] = useState(false);

    if (submitted) {
        return <Redirect to="/search-movie"/>;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        await axios.post(`http://${backendURL}:8080/pa165/rest/ratings/create`,
            {
                user: {id: getUserId()},
                movie: {id: location.state.id},
                originality: originality,
                soundtrack: soundtrack,
                narrative: narrative,
                cinematography: cinematography,
                depth: depth
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(
                () => {
                    alert("Rating added successfully");
                    setSubmitted(true);
                })
            .catch((msg) => console.log(msg))
    }

    return (
        <div className="create-rating-wrapper">
            <Box mb={2}>
                <Typography variant="h4">Create Rating for {location.state.title}</Typography>
            </Box>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    <Grid item xs={5} />
                    <Grid item xs={1}>
                        <FormLabel>Originality: </FormLabel>
                    </Grid>
                    <Grid item xs={1}>
                        <RatingSelect rating={originality} setter={setOriginality} />
                    </Grid>
                    <Grid item xs={5} />

                    <Grid item xs={5} />
                    <Grid item xs={1}>
                        <FormLabel>Soundtrack: </FormLabel>
                    </Grid>
                    <Grid item xs={1}>
                        <RatingSelect rating={soundtrack} setter={setSoundtrack} />
                    </Grid>
                    <Grid item xs={5} />

                    <Grid item xs={5} />
                    <Grid item xs={1}>
                        <FormLabel>Narrative: </FormLabel>
                    </Grid>
                    <Grid item xs={1}>
                        <RatingSelect rating={narrative} setter={setNarrative} />
                    </Grid>
                    <Grid item xs={5} />

                    <Grid item xs={5} />
                    <Grid item xs={1}>
                        <FormLabel>Cinematography: </FormLabel>
                    </Grid>
                    <Grid item xs={1}>
                        <RatingSelect rating={cinematography} setter={setCinematography} />
                    </Grid>
                    <Grid item xs={5} />

                    <Grid item xs={5} />
                    <Grid item xs={1}>
                        <FormLabel>Depth: </FormLabel>
                    </Grid>
                    <Grid item xs={1}>
                        <RatingSelect rating={depth} setter={setDepth} />
                    </Grid>
                    <Grid item xs={5} />

                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" type="submit">
                            Submit
                        </Button>
                    </Grid>

                </Grid>
            </form>
        </div>
    )
}
