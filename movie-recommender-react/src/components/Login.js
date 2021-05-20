import React, { useState } from "react";
import axios from "axios";
import "./Login.css";
import PropTypes from 'prop-types';


export default function Login({ setToken }) {
    const [emailAddress, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async e => {
        e.preventDefault();
        const token = await axios
        .post('http://localhost:8080/pa165/rest/users/auth', {emailAddress, password})
        .then((data) => {
            console.log("Token =>", data);
            setToken(data.data);
        })
        .catch(error => {
            if (error.response && (error.response.status === 401 || error.response.status === 404)) {
                alert("Invalid email or password. Try again.")
                return;
            }
            alert(error);
            //alert("Some server-related problems occurred. Please try again.");
        });
    }

    return (
        <div className="login-wrapper">
            <h1>Log in:</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Email:
                    <input type="email"
                           value={emailAddress}
                           onChange={e => setEmail(e.target.value)}
                           name="email"
                           placeholder="Email"/>
                </label>
                <label>
                    Password:
                    <input type="password"
                           value={password}
                           onChange={e => setPassword(e.target.value)}
                           name="password"
                           placeholder="Password"/>
                </label>
                <input type="submit" value="Login" />
            </form>
        </div>
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
}
