import React, {useEffect, useState} from 'react';
import {getUserId} from "../utils/Common";
import axios from "axios";
import {Box, Button, Card, CardContent, Grid, Typography} from "@material-ui/core";

export function ManageUsers() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const getUsers = async () => {
            return await axios.get(`http://localhost:8080/pa165/rest/users`)
                .catch(console.log);
        }
        getUsers().then(users => setUsers(users.data));
    }, [])

    const handleDisable = async (userId) => {
        await axios.post(`http://localhost:8080/pa165/rest/users/disable?id=${userId}`, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        setUsers((prev) => prev.filter((user) => user.id !== userId));
    }

    const renderUser = ({id, username, emailAddress, admin}) => (
        <Grid item xs={12}>
            <Card fullwidth style={{backgroundColor: "#e6e6e6"}}>
                <CardContent>
                    <Typography gutterBottom variant="h5" align="left" component="h2">
                        {username}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" align="left" component="p">
                        {<p><b>Email: </b>{emailAddress}</p>}
                        {<p><b>Admin: </b>{(admin).toString()}</p>}
                        <Button variant="contained" onClick={() => handleDisable(id)}>Disable</Button>
                    </Typography>
                </CardContent>
            </Card>
        </Grid>
    )

    const renderUsers = (otherUsers) => {

        return (
            <div>
                <Box mb={2}>
                    <Typography variant="h4">User Management</Typography>
                </Box>
                <Box mt={1}>
                    <Grid container  spacing={1}>
                        {otherUsers.map(user => renderUser(user))}
                    </Grid>
                </Box>
            </div>
        )
    }

    let others = users.filter(user => (user.id !== getUserId()));
    return renderUsers(others);
}