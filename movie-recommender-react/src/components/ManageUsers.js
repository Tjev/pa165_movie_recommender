import React, {useEffect, useState} from 'react';
import {getUserId} from "../utils/Common";
import axios from "axios";

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
        <li key={id}>
            <div>
                <h2>{username}</h2>
            </div>
            {<p><b>Email: </b>{emailAddress}</p>}
            {<p><b>Admin: </b>{(admin).toString()}</p>}
            <button onClick={() => handleDisable(id)}>Disable</button>
        </li>
    )

    const renderUsers = (otherUsers) => {
        return (
            <div>
                <h1>System users:</h1>
                <ul>
                    {otherUsers.map(user => renderUser(user))}
                </ul>
            </div>
        )
    }

    let others = users.filter(user => (user.id !== getUserId()));
    return renderUsers(others);
}