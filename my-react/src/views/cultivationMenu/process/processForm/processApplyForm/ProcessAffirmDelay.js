import {Box, Button, Dialog, DialogTitle, makeStyles, Typography} from "@material-ui/core";
import React, {useState} from "react";
import ProcessDisagreeForm from "./ProcessDisagreeForm";
import ProcessDelay from "./ProcessDelay";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessAffirmDelay(props){//确认是否延期
    const classes=useStyles()
    const {open, onClose, onRefresh, list}=props //list是不同意的任务详情
    const [disagreeOpen,setDisagreeOpen]=useState(false)
    const [delayOpen,setDelayOpen]=useState(false)

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle>拒绝完成任务申请</DialogTitle>
            <form className="dialogForm">
                <Typography variant="h4" color="textPrimary" >
                    您点击了拒绝任务完成申请，请问需要延期该任务吗？
                </Typography>
                <Box my={2}>
                    <Button
                        fullWidth
                        color="primary"
                        variant="contained"
                        onClick={()=>{
                            setDelayOpen(true)
                        }}
                        size="large">是</Button>
                    <Button
                        fullWidth
                        color="secondary"
                        size="large"
                        onClick={()=>{
                            setDisagreeOpen(true)
                        }}
                        variant="contained"
                    >否</Button>
                </Box>
            </form>

            <ProcessDelay open={delayOpen}
                          onClose={()=>{
                              onClose()
                              setDelayOpen(false)
                          }}
                          onRefresh={()=>onRefresh()}
                          list={list}/>

            <ProcessDisagreeForm open={disagreeOpen}
                                 onClose={()=>{//onClose的意思是关闭这个对话框
                                     onClose()
                                     setDisagreeOpen(false)
                                 }}
                                 onRefresh={()=>{//onRefresh的意思是刷新界面
                                     onRefresh()
                                 }}
                                 list={list}/>
        </Dialog>
    )
}
