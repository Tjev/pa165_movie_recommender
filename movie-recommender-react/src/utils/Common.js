import { useState } from 'react';
import jwtDecode from "jwt-decode";

function getJwtItem(key) {
    const jwtToken = sessionStorage.getItem('token');
    return (jwtToken) ? jwtDecode(jwtToken)[key] : null;
}

export const getUser = () => {
    const jwtToken = sessionStorage.getItem('token');
    return (jwtToken) ? jwtDecode(jwtToken)['username'] : null;
    //TODO return getJwtItem('username');
}

export const getAdminStatus = () => {
    const jwtToken = sessionStorage.getItem('token');
    return (jwtToken) ? jwtDecode(jwtToken)['admin'] : null;
}

export const getUserId = () => {
    const jwtToken = sessionStorage.getItem('token');
    return (jwtToken) ? jwtDecode(jwtToken)['id'] : null;
}

export const getEmail = () => {
    const jwtToken = sessionStorage.getItem('token');
    return (jwtToken) ? jwtDecode(jwtToken)['email'] : null;
}

export function useToken() {

    const getToken = () => {
        const tokenString = sessionStorage.getItem('token');
        return tokenString;
    };

    const [token, setToken] = useState(getToken());

    const saveToken = (userToken) => {
        // userToken is set to null on Logout
        if (userToken === null) {
            sessionStorage.removeItem('token')
        } else {
            sessionStorage.setItem('token', JSON.stringify(userToken));
        }
        setToken(userToken);
    };

    return {
        setToken: saveToken,
        token
    }
}