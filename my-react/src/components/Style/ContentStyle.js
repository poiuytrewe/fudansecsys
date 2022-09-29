import {makeStyles} from "@material-ui/core";

const ContentStyle=makeStyles((theme)=>({
    header: {
        display: "flex",
        justifyContent: "space-between",
        padding: "16px",
        "& p": {
            lineHeight: 2,
        },
    },
    Pagination: {
        padding: theme.spacing(2),
        '& .MuiPagination-ul': {
            justifyContent: 'center',
        }
    },
}))

export default ContentStyle
