import {Box, Button, Dialog, DialogTitle, makeStyles, Typography} from "@material-ui/core";
import React from "react";
import confirmModal from "../../../../../components/ConfirmModal";
import {postFetch} from "../../../../../base";
import {MNG_PROCESS_GROUP_UPDATE, MNG_PROCESS_UPDATE} from "../../../../../settings";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessGroupDelay(props){
   const classes=useStyles()
    const {open, onClose, list, delayTime, onRefresh}=props

    const handleYes=()=>{
       const cor=confirmModal({
           title:"确定将这个任务及以后的任务延期"+delayTime+"个月吗？",
           handleCorfirm:()=>{
               cor.close()
               postFetch({
                   url:MNG_PROCESS_GROUP_UPDATE,
                   values:{
                       stuId:list.stuId,
                       list:list,
                       delayTime:delayTime
                   },
                   successCallback:()=>{
                       onClose()
                       onRefresh()
                   }
               })
           }
       })
    }

    const handleNo=()=>{
        let newYear
        let newMonth
        if(12-delayTime-list.month<0){//说明到了新的一年
            newYear=list.year+1
            newMonth=delayTime+list.month-12
        }
        else {
            newYear = list.year
            newMonth=delayTime + list.month
        }
       const cor=confirmModal({
           title:"确定要将这一个任务延期"+delayTime+"个月吗？",
           handleCorfirm:()=>{
               cor.close()
               postFetch({
                   url:MNG_PROCESS_UPDATE,
                   values:{
                       id:list.id,
                       missionStatus:1,
                       year:newYear,
                       month:newMonth,
                       status:1
                   },
                   successCallback:()=>{
                       onClose()
                       onRefresh()
                   }
               })
           }
           }
       )
    }

    return(
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle>任务延期</DialogTitle>
            <form className="dialogForm">
                <Typography variant="h4" color="textPrimary" >
                    是否将这个任务的后期所有任务，均延期同样的时间？
                </Typography>
                <Box my={3}>
                    <Button
                        fullWidth
                        color="primary"
                        variant="contained"
                        onClick={()=>{
                            handleYes()
                        }}
                        size="large">是</Button>
                    <Button
                        fullWidth
                        color="secondary"
                        size="large"
                        onClick={()=>{
                            handleNo()
                        }}
                        variant="contained"
                    >否</Button>
                    <Button
                        fullWidth
                        color="secondary"
                        size="large"
                        onClick={()=>{
                            onClose()
                        }}
                        variant="contained">取消</Button>
                </Box>
            </form>
        </Dialog>
    )
}
