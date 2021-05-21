import React, { useState } from "react";
import "./Login.css";
import PropTypes from 'prop-types';
import { Redirect } from 'react-router'


export function Logout({token, setToken}) {
    setToken(null);

    return <Redirect to="/login"/>;
}

Logout.propTypes = {
    setToken: PropTypes.func.isRequired
}
