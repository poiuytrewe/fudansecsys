import React from "react";
import {makeStyles} from "@material-ui/core";

const ViewStyle=makeStyles((theme)=>({
    root: {
        backgroundColor: theme.palette.background.dark,
            minHeight: "100%",
            paddingBottom: theme.spacing(3),
            paddingTop: theme.spacing(3),
    },
}))

export default ViewStyle
