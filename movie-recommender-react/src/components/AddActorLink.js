import {getAdminStatus} from "../utils/Common";
import {NavLink} from "react-router-dom";
import {Button} from "@material-ui/core";
import React from "react";

export function AddActorLink(id, title, token) {
    if (token && getAdminStatus()) {
        return (<NavLink exact
                         activeClassName="active"
                         to={{
                             pathname:'/add-actor',
                             state: {id: id, title: title}
                         }}
                         style={{ textDecoration: 'none' }}>
            <Button variant="contained" type="button">Add actor</Button>
        </NavLink>);
    }
}
