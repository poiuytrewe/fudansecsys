import {makeStyles} from "@material-ui/core";
// import theme from "../../theme";

const PageView=makeStyles((theme)=>({
    root: {
        // flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
    },
    formRoot: {
        "& .MuiTextField-root": {
            margin: theme.spacing(1.5),
            width: "25ch",
        },
    },
    margin: {
        margin: theme.spacing(1.5),
    },
}))

export default PageView
