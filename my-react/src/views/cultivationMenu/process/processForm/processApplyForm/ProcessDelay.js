import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import {useState} from "react";
import ProcessGroupDelay from "./ProcessGroupDelay";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessDelay(props){//不同意任务完成，任务要延期
    const classes=useStyles()
    const {open, onClose, onRefresh, list}=props
    const [delayTime,setDelayTime]=useState(1)
    const [groupOpen,setGroupOpen]=useState(false)

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle>任务延期</DialogTitle>
            <Formik initialValues={list} onSubmit={()=>{
                setGroupOpen(true)
            }}>
                {({handleBlur,handleSubmit})=>(
                    <form onSubmit={handleSubmit} className="dialogForm">
                        <TextField
                            select
                            label={list.missionStatus==2 ? "任务推迟时间(/月)":(
                                list.missionStatus==4 && "请选择合适的推迟时间(/月)"
                            )}
                            fullWidth
                            margin="normal"
                            name="delayTime"
                            onBlur={handleBlur}
                            onChange={(e)=>{
                                setDelayTime(parseInt(e.target.value))
                            }}
                            SelectProps={{native:true}}
                            value={delayTime}
                            variant="outlined">
                            <option value={1}>1</option>
                            <option value={2}>2</option>
                            <option value={3}>3</option>
                            <option value={4}>4</option>
                            <option value={5}>5</option>
                            <option value={6}>6</option>
                        </TextField>
                        <Box my={2}>
                            <Button
                                fullWidth
                                color="primary"
                                size="large"
                                type="submit"
                                variant="contained"
                            >提交</Button>
                            <Button
                                fullWidth
                                color="secondary"
                                size="large"
                                onClick={()=>onClose()}
                                variant="contained"
                            >取消</Button>
                        </Box>
                    </form>
                )}
            </Formik>

            <ProcessGroupDelay open={groupOpen}
                               delayTime={delayTime}
                               onClose={()=>{
                                   setGroupOpen(false)
                                   onClose()
                               }}
                               onRefresh={()=>{
                                   onRefresh()
                               }}
                               list={list}/>
        </Dialog>
    )
}
