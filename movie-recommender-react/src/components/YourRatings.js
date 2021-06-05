import React, {useEffect, useState} from 'react';
import {getUserId, getUser} from "../utils/Common";
import axios from "axios";
import {Box, Button, Card, CardContent, Grid, Typography} from "@material-ui/core";

/**
 * @author Kristian Tkacik
 */
export function YourRatings() {
    const [ratings, setRatings] = useState([]);

    useEffect(() => {
        const getRatings = async () => {
            return await axios.get(`http://localhost:8080/pa165/rest/ratings/find-by-user?id=${getUserId()}`)
                .catch(console.log);
        }
        getRatings().then(ratings => setRatings(ratings.data));
    }, [])

    const handleDelete = async (ratingId) => {
        await axios.delete(`http://localhost:8080/pa165/rest/ratings/remove`, {
            data: {id: ratingId},
            headers: {
                'Content-Type': 'application/json'
            }
        })
        setRatings((prev) => prev.filter((rating) => rating.id !== ratingId));
    }

    return (
        <div>
            <Box mb={2}>
                <Typography variant="h4">{getUser()}'s ratings:</Typography>
            </Box>
            {ratings.length === 0 && <p>You do not have any ratings</p>}
            <Box mt={1}>
                <Grid container  spacing={1}>
                    {ratings.map(({ id, movie, originality, soundtrack, narrative, cinematography, depth }) => (
                        <Grid item xs={12}>
                            <Card fullwidth style={{backgroundColor: "#e6e6e6"}}>
                                <CardContent>
                                    <Typography gutterBottom variant="h5" align="left" component="h2">
                                        {movie.title}
                                    </Typography>

                                    <Typography variant="body2" color="textSecondary" align="left" component="p">
                                        {<p><b>Originality: </b>{originality}</p>}
                                        {<p><b>Soundtrack: </b>{soundtrack}</p>}
                                        {<p><b>Narrative: </b>{narrative}</p>}
                                        {<p><b>Cinematography: </b>{cinematography}</p>}
                                        {<p><b>Depth: </b>{depth}</p>}
                                        <Button variant="contained" onClick={() => handleDelete(id)}>Delete</Button>
                                    </Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </Box>
        </div>
    );
}
