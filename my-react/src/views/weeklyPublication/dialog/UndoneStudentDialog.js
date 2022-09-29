import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function UndoneStudentDialog(props){
    const {open, list, onClose}=props
    const classes=useStyles()

    const getNameLine=()=>{
        let nameLine=""
        list.map((l,index)=>{
            let s= index==0 ? l.name : "，"+l.name
            nameLine=nameLine+s
        })
        return nameLine
    }

    return (
        <Dialog
            onClose={onClose} open={open} className={classes.root}>
            <DialogTitle onClose={onClose}>
                未提交周报名单
            </DialogTitle>
            <form className="dialogForm">
                <TextField
                    InputProps={{readOnly:true}}
                    multiline
                    rows={15}
                    fullWidth
                    value={getNameLine()}
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
                        variant="contained">关闭</Button>
                </Box>
            </form>
        </Dialog>
    )
}
