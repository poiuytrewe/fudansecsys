import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";

const useStyles=makeStyles((theme)=>({
    root:{
        padding: theme.spacing(3),
    }
}))

export  default  function SeminarSectionDetailForm(props){
    const {open, onClose, sectionDetail}=props
    const classes=useStyles()

    return (
        <Dialog onClose={onClose} open={open} className={classes.root}>
            <DialogTitle onClose={onClose}>
                推荐论文列表
            </DialogTitle>
            <form className="dialogForm">
                <TextField
                    fullWidth
                    multiline
                    rows={5}
                    label="推荐文章"
                    margin="normal"
                    value={sectionDetail.paperRec}
                    InputProps={{readOnly:true}}
                    variant="outlined"
                 />
                <Box my={2}>
                    <Button
                        color="secondary"
                        fullWidth
                        size="large"
                        onClick={onClose}
                        variant="contained"
                        >退出</Button>
                </Box>
            </form>
        </Dialog>

    )
}
