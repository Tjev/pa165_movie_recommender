import { useState } from 'react';
import jwtDecode from "jwt-decode";
import {makeStyles} from "@material-ui/core/styles";
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles'
import { colors } from '@material-ui/core'
import moment from 'moment'

function getJwtItem(key) {
    const jwtToken = sessionStorage.getItem('token');
    return (jwtToken) ? jwtDecode(jwtToken)[key] : null;
}

export const getUser = () => {
    return getJwtItem('username');
}

export const getAdminStatus = () => {
    return getJwtItem('admin');
}

export const getUserId = () => {
    return getJwtItem('id');
}

export const getEmail = () => {
    return getJwtItem('email');
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

export function formatDate(date) {
    return moment(date).format('Do MMMM YYYY');
}

export function formatYear(date) {
    return moment(date).format('YYYY');
}

export const theme = createMuiTheme({
    palette: {
        primary: {
            main: '#ecc113'
        },
        secondary: {
            main: '#e6e6e6'
        }
    }
});