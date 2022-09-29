import {Box, Button, ButtonGroup, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_DELETE, MNG_PROCESS_UPDATE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";

const useStyles=makeStyles((theme)=>({
    root:{
        padding: theme.spacing(3),
    }
}))

export default function ProcessUndoneForm(props){
    const classes=useStyles()
    const {open, onClose, mission, missionDetail}=props
    const predictTime=`${missionDetail.year}年${missionDetail.month}月`

    const handleDelete=()=>{//处理删除请求
        const cor=confirmModal({
            title:"确定要删除这个任务吗",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_DELETE,
                    values:{
                        missionList:mission,
                        count:missionDetail.count,
                        id:missionDetail.id
                    },
                    successCallback:()=>{
                      alertBox({text:"删除成功",severity:"success"})
                        onClose()
                    }
                })
            }
        })
    }

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle onClose={onClose}>
                未完成的任务详情
            </DialogTitle>

            <Formik
                initialValues={{}}
                onSubmit={(values)=>{
                    if(!values?.predictDate || values?.predictDate===""){
                        alertBox({text:"没有进行任何修改",severity:"error"})
                        return
                    }
                    const preMission=mission.find((m)=>m.count===missionDetail.count-1)
                    const postMission=mission.find((m)=>m.count===missionDetail.count+1)
                    const newYear=parseInt(values?.predictDate.slice(0,4))//预计完成年份
                    const newMonth=parseInt(values?.predictDate.slice(5,7))//预计完成月份
                    if(!preMission){//前置任务不存在
                        if(newYear>postMission.year || (newYear===postMission.year && newMonth>postMission.month)){//后置任务不满足条件
                            alertBox({text:"修改的预计完成时间不能晚于后置任务",severity:"error"})
                            return
                        }
                    }
                    else if(!postMission){//后置任务不错在
                        if(newYear<preMission.year || (newYear===preMission.year && newMonth<preMission.month)) {//前置任务不满足条件
                            alertBox({text: "修改的预计完成时间不能早于前置任务", severity: "error"})
                            return
                        }
                    }
                    else {
                        if(newYear<preMission.year || (newYear===preMission.year && newMonth<preMission.month)){
                            alertBox({text:"修改的预计完成时间不能早于前置任务",severity:"error"})
                            return
                        }
                        else if(newYear>postMission.year || (newYear===postMission.year && newMonth>postMission.month)){
                            alertBox({text:"修改的预计完成时间不能晚于后置任务",severity:"error"})
                            return
                        }
                    }
                    const cor=confirmModal({
                        title:"确定要修改预计完成时间吗",
                        handleCorfirm:()=>{
                            cor.close()
                            postFetch({
                                url:MNG_PROCESS_UPDATE,
                                values:{
                                    id:missionDetail.id,
                                    year:newYear,
                                    month:newMonth,
                                },
                                successCallback:()=>{
                                    alertBox({text:"修改成功",severity:"success"})
                                    onClose()
                                    postFetch({
                                        url:MNG_ADD_BULLETIN_URL,
                                        values:{
                                            stuId:missionDetail.stuId,
                                            title:"你有项任务的预计完成时间被修改，请即使查看",
                                            content:"你的["+missionDetail.missionName+"]任务的预计完成时间已被修改，具体时间请到培养过程模块查看"
                                        }
                                    })
                                }
                            })
                        }
                    })
                }}
            >
                {({handleSubmit,handleChange,handleBlur,values})=>(
                        <form onSubmit={handleSubmit} className="dialogForm">
                            <TextField
                                label="任务名称"
                                fullWidth
                                margin="normal"
                                name="missionName"
                                value={missionDetail.missionName}
                                onBlur={handleBlur}
                                variant="outlined"
                                onChange={handleChange}
                            />
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
                            <TextField
                                InputProps={{readOnly:true}}
                                label="预计完成时间"
                                fullWidth
                                margin="normal"
                                name="predictTime"
                                value= {predictTime}
                                variant="outlined"/>
                            <TextField
                                label="修改预计完成时间"
                                name="predictDate"
                                type="month"
                                value={values?.predictDate}
                                margin="normal"
                                fullWidth
                                variant="outlined"
                                InputLabelProps={{shrink:true}}
                                onBlur={handleBlur}
                                onChange={handleChange}
                            />
                            <Box my={2}>
                                <ButtonGroup
                                    fullWidth
                                    color="primary"
                                    size="large"
                                    variant="outlined"
                                >
                                    <Button type="submit"
                                    >提交修改</Button>
                                    <Button
                                        onClick={()=>{
                                            handleDelete()
                                        }}
                                    >删除</Button>
                                </ButtonGroup>
                                <Button
                                    fullWidth
                                    coloe="secondary"
                                    size="large"
                                    onClick={()=>{
                                        onClose()
                                    }}
                                    variant="contained"
                                    >关闭</Button>
                            </Box>
                        </form>

            )}</Formik>
        </Dialog>
    )
}
