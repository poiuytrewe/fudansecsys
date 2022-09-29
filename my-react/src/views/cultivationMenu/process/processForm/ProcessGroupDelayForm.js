import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import {useState} from "react";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import alertBox from "../../../../components/AlertBox";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_GROUPDELAY} from "../../../../settings";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessGroupDelayForm(props){
    const classes=useStyles()
    const [delayTime, setDelaTime]=useState(1)
    const {open, onClose, groupList, teacherStuId}=props

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle>批量延期申请</DialogTitle>
            <Formik initialValues={groupList}
                    onSubmit={()=>{
                        const cor=confirmModal({
                            title:"确定要提交批量延期申请吗？",
                            handleCorfirm:()=>{
                                cor.close()
                                postFetch({
                                    url: MNG_PROCESS_GROUPDELAY ,
                                    values:{
                                        groupList:groupList,
                                        delayTime:delayTime
                                    },
                                    successCallback:()=>{
                                        alertBox({text:"批量延期申请已提交，请等待导师回复",severity:"success"})
                                        postFetch({
                                            url:MNG_ADD_BULLETIN_URL,
                                            values:{
                                                stuId:teacherStuId,
                                                title:"您的学生"+groupList[0].stuName+"批量提交了任务延期申请",
                                                content:groupList[0].stuName+"批量提交了任务的延期申请，请到培养过程板块处理"
                                            }
                                        })
                                        onClose()
                                    }
                                })
                            }
                        })
                    }}>
                {({handleSubmit,handleChange,handleBlur})=>(
                    <form onSubmit={handleSubmit} className="dialogForm">
                        <TextField
                            select
                            label="任务推迟时间(/月)"
                            fullWidth
                            margin="normal"
                            name="delayTime"
                            onBlur={handleBlur}
                            onChange={(e)=>{
                                setDelaTime(parseInt(e.target.value))
                            }}
                            SelectProps={{
                                native:true
                            }}
                            value={delayTime}
                            variant="outlined"
                            >
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
        </Dialog>
    )
}
