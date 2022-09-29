import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import React from "react";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function ScoreStudentComment(props){
    const {open, onClose, scoreDetail}=props
    const classes=useStyles()

    return (
        <Dialog open={open} className={classes.root}>
            <DialogTitle>
                学生自评信息
            </DialogTitle>
                    <form className="dialogForm">

                            <TextField
                                InputProps={{
                                    readOnly:true
                                }}
                                multiline
                                rows={15}
                                label="评语"
                                fullWidth
                                margin="normal"
                                name="comment"
                                value={scoreDetail.comment}
                                variant="outlined"
                            />
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
