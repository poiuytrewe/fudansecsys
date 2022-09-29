import {Box, Button, Dialog, DialogTitle, makeStyles, Typography} from "@material-ui/core";
import {Formik} from "formik";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_UPDATE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ConfirmForm(props){
    const {open, onClose, detail}=props
    const classes=useStyles()

    const donePermission= detail.missionStatus===2//这里是看这个申请是否是提交申请，如果不是提交，就是延期

    const handleClose=()=>{
        if(donePermission){
            postFetch({
                url:MNG_PROCESS_UPDATE,
                values:{
                    id:detail.id,
                    missionStatus:1
                },
                successCallback:()=>{
                    postFetch({
                        url:MNG_ADD_BULLETIN_URL,
                        values:{
                            stuId:detail.stuId,
                            title:"你的任务完成申请未通过",
                            content:"很遗憾，你的["+detail.category+"]的完成申请未通过，请继续完善"
                        },
                        successCallback:()=>{
                            onClose()
                        }
                    })
                }
            })
        }
        else{
            const date=new Date()
            const newYear=date.getFullYear()
            const newMonth=date.getMonth()+1
            let status;
            if(newYear>detail.year || (newYear===detail.year && newMonth>detail.month)){//如果现在的年份比预计完成的年份要大，或者年份一样，但现在的月份比预计完成的月份要大
                status=0//说明已经逾期了
            }
            else {
                status=1
            }
            postFetch({
                url:MNG_PROCESS_UPDATE,
                values:{
                    id:detail.id,
                    missionStatus:status
                },
                successCallback:()=>{
                    postFetch({
                        url:MNG_ADD_BULLETIN_URL,
                        values:{
                            stuId:detail.stuId,
                            title:"你的任务延期申请未通过",
                            content:"很遗憾，你的["+detail.category+"]的延期申请未通过，请与导师讨论后重新提交"
                        },
                        successCallback:()=>{
                            onClose()
                        }
                    })
                }
            })
        }
    }

    return (
        <Dialog open={open} className={classes.root}>
            <DialogTitle>申请确认</DialogTitle>
            <Formik
            initialValues={detail}
            onSubmit={(values => {
                if(donePermission){//处理完成申请
                    postFetch({
                        url:MNG_PROCESS_UPDATE,
                        values:{
                            id:detail.id,
                            missionStatus:3
                        },
                        successCallback:()=>{
                            alertBox({text:"已同意申请",severity:"success"})
                            onClose()
                        }
                    })
                }
                else{//处理延期申请
                    postFetch({
                        url:MNG_PROCESS_UPDATE,
                        values:{
                            id:detail.id,
                            missionStatus:1,
                            year:detail.wishYear,
                            month:detail.wishMonth,
                            isDelay:1
                        },
                        successCallback:()=>{
                            alertBox({text:"已同意申请",severity:"success"})
                            onClose()
                        }
                    })
                }
            })}>{({
                handleSubmit
            })=>(
                <form onSubmit={handleSubmit} className="dialogForm">
                    {donePermission? (
                        <Typography align="center">
                            您的学生{detail.stuName}提交了{detail.category}的完成申请，是否同意该申请？
                        </Typography>
                    ):(
                        <Typography align="center">
                            您的学生{detail.stuName}希望将{detail.category}任务的完成时间延期到{detail.wishYear}年{detail.wishMonth}月，是否同意改申请？
                        </Typography>
                    )}

                    <Box my={2}>
                        <Button
                            fullWidth
                            color="primary"
                            size="large"
                            type="submit"
                            variant="contained"
                        >同意</Button>
                        <Button
                            fullWidth
                            color="secondary"
                            size="large"
                            onClick={()=>{
                                handleClose()
                            }}
                            variant="contained"
                        >不同意</Button>
                    </Box>
                </form>
            )}
            </Formik>
        </Dialog>
    )
}
