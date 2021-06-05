import {getAdminStatus} from "../utils/Common";
import {NavLink} from "react-router-dom";
import {Button, Grid} from "@material-ui/core";
import React from "react";

export function AddDirectorLink(id, title, token, directors) {
    if (token && getAdminStatus()) {
        return (
            <Grid item>
                <Button variant="contained" type="button">
                    <NavLink exact
                             activeClassName="active"
                             to={{
                                 pathname:'/add-director',
                                 state: {id: id, title: title, directors: directors}
                             }}
                             style={{ textDecoration: 'none', color: "black" }}>
                        Add director
                    </NavLink>
                </Button>
            </Grid>
        );
    }
}

