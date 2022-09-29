import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function PublicationComment(props){
    const {open, publication, onClose}=props
    const classes=useStyles()

    return (
        <Dialog
            onClose={onClose} open={open} className={classes.root}>
            <DialogTitle onClose={onClose}>
                导师周报评语
            </DialogTitle>
            <form className="dialogForm">
                <TextField
                    InputProps={{readOnly:true}}
                    multiline
                    rows={15}
                    label="导师周报评语"
                    fullWidth
                    margin="normal"
                    name="comment"
                    value={publication.comment}
                    variant="outlined"/>
                <Box my={2}>
                    <Button
                        fullWidth
                        color="primary"
                        size="large"
                        onClick={()=>{
                            onClose()
                        }}
                        variant="contained"
                        >关闭</Button>
                </Box>
            </form>
        </Dialog>
    )
}
