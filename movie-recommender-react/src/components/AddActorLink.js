import {getAdminStatus} from "../utils/Common";
import {NavLink} from "react-router-dom";
import {Button, Grid} from "@material-ui/core";
import React from "react";

export function AddActorLink(id, title, token) {
    if (token && getAdminStatus()) {
        return (
            <Grid item>
                <Button variant="contained" type="button">
                    <NavLink exact
                             activeClassName="active"
                             to={{
                                 pathname:'/add-actor',
                                 state: {id: id, title: title}
                             }}
                             style={{ textDecoration: 'none', color: "black" }}>
                        Add actor
                    </NavLink>
                </Button>
            </Grid>
        );
    }
}
