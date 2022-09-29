import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessDetailForm(props){ //学生用的
    const {open, onClose, missionDetail}=props
    const classes=useStyles()


    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle
                onClose={onClose}>
                任务详情
            </DialogTitle>

            <form className="dialogForm">
                <TextField
                    InputProps={{readOnly:true}}
                    label="任务"
                    fullWidth
                    margin="normal"
                    name="missionName"
                    value={missionDetail.missionName}
                    variant="outlined"/>
                <TextField
                    InputProps={{readOnly:true}}
                    label="任务详情"
                    fullWidth
                    margin="normal"
                    multiline
                    rows={4}
                    name="missionDetail"
                    value={missionDetail.missionDetail}
                    variant="outlined"/>

                <Box my={2}>
                    <Button
                        fullWidth
                        color="secondary"
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
