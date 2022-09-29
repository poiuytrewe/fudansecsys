import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_GROUP_UPDATE, MNG_PROCESS_UPDATE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import {useState} from "react";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessDelayForm(props){//导师处理逾期任务
    const classes=useStyles()
    const {open, onClose, missionDetail, teacherStuId}=props
    const predictTime=`${missionDetail.year}年${missionDetail.month}月`
    const [delayTime,setDelayTime]=useState(1)

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle onClose={onClose}>
                任务延期申请
            </DialogTitle>
            <Formik initialValues={missionDetail}
                    onSubmit={(values)=>{
                        // if(!values?.delayTime || values?.delayTime===0){
                        //     alertBox({text:"请选择延期时间",severity:"error"})
                        //     return
                        // }
                            let newYear
                            let newMonth
                            if(12-delayTime-missionDetail.month<0){//说明到了新的一年
                                newYear=missionDetail.year+1
                                newMonth=delayTime+missionDetail.month-12
                            }
                            else{
                                newYear=missionDetail.year
                                newMonth=delayTime + missionDetail.month
                            }
                            const cor=confirmModal({
                                title:"确定要提交吗",
                                handleCorfirm:()=>{
                                    cor.close()
                                    postFetch({
                                        url:MNG_PROCESS_UPDATE,
                                        values:{
                                            id:missionDetail.id,
                                            missionStatus:4,//提交某一个任务的延期处理
                                            wishYear:newYear,
                                            wishMonth:newMonth
                                        },
                                        successCallback:()=>{
                                            alertBox({text:"延期申请已提交，请等待导师回复",severity:"success"})
                                            postFetch({
                                                url:MNG_ADD_BULLETIN_URL,
                                                values:{
                                                    stuId:teacherStuId,
                                                    title:"您的学生"+missionDetail.stuName+"提交了任务延期申请",
                                                    content:missionDetail.stuName+"提交了["+missionDetail.category+"]任务的延期申请，请到培养过程板块处理"
                                                }
                                            })
                                            onClose()
                                        }
                                    })
                                }
                            })
                    }}>
                {({values,handleSubmit,handleBlur,handleChange})=>(
                    <form onSubmit={handleSubmit} className="dialogForm">
                        <TextField
                            InputProps={{readOnly:true}}
                            label="任务"
                            fullWidth
                            margin="normal"
                            name="category"
                            value={missionDetail.category}
                            variant="outlined"/>
                        <TextField
                            InputProps={{readOnly:true}}
                            label="预计完成时间"
                            fullWidth
                            margin="normal"
                            name="predictTime"
                            value= {predictTime}
                            variant="outlined"/>
                        <TextField
                            select
                            label="任务推迟时间(/月)"
                            fullWidth
                            margin="normal"
                            name="delayTime"
                            onBlur={handleBlur}
                            onChange={(e)=>{
                                setDelayTime(parseInt(e.target.value))
                            }}
                            SelectProps={{
                                native:true
                            }}
                            value={delayTime}
                            variant="outlined"
                        >
                            {/*<option value={0}>0</option>*/}
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
