import React, { useState } from "react";
import "./Login.css";
import PropTypes from 'prop-types';

async function loginUser(credentials) {
    return fetch('http://localhost:8080/pa165/rest/users/auth', {
        method: 'POST',
        mode: 'no-cors',
        body: JSON.stringify(credentials)}).then(data => data.json());
}

export function Login({ setToken }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async e => {
        e.preventDefault();
        const token = await loginUser({ email, password });
        setToken(token);
    }

    return (
        <div className="login-wrapper">
            <h1>Log in:</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Email:
                    <input type="email"
                           value={email}
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
