import React from "react";
import {makeStyles} from "@material-ui/core";
// import theme from "../../theme";

const ContentStyleForPage=makeStyles((theme)=>({
    root: {
        // flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(2),
    },
    header: {
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        paddingBottom: theme.spacing(2),
        "& .MuiTextField-root": {
            width: "150px",
        },
        "& .MuiButton-root": {
            width: "150px",
            height: "40px",
        },
    },
    Pagination: {
        padding:theme.spacing(2),
        '& .MuiPagination-ul':{
            justifyContent: 'center',
        }
    }
}))

export default ContentStyleForPage
