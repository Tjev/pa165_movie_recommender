import {Box, Typography} from "@material-ui/core";
import React from "react";

export function HomePage() {
    return (
        <div>
            <Box mb={2}>
                <Typography variant="h4">Movie Recommender</Typography>
            </Box>
            <div>
                <img src='https://www.fi.muni.cz/usr/pelikan/FI.gif' alt="Spinning fi muni logo..." />
            </div>
        </div>
    )
}