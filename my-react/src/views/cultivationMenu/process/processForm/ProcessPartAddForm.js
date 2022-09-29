import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_ADD, MNG_PROCESS_UPDATE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import * as Yup from 'yup';

const useStyles=makeStyles((theme)=>({
    root:{
        padding: theme.spacing(3),
    }
}))

export default function ProcessPartAddForm(props){
    const {open, onClose, missionDetail}=props
    const classes=useStyles()

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle onClose={onClose}>
                添加任务
            </DialogTitle>
            <Formik
                initialValues={missionDetail}
                validationSchema={Yup.object().shape({
                    missionDetail:Yup.string().
                        required("请输入具体的任务内容"),
                    predictDate:Yup.string().
                        required("请属于预定的完成时间")
                })}
                onSubmit={(values)=>{
                    const newDate=new Date()
                    const nYear=newDate.getFullYear()//当前的年份
                    const nMonth=newDate.getMonth()+1//当前的月份
                    const newYear=parseInt(values?.predictDate.slice(0,4))//预计完成年份
                    const newMonth=parseInt(values?.predictDate.slice(5,7))//预计完成月份
                    if(newYear<nYear || (newYear===nYear && newMonth<nMonth)){
                        alertBox({text:"预计完成时间不能是过去的时间",severity:"error"})
                        return
                    }
                    const cor=confirmModal({
                        title:"确定要添加这个任务吗",
                        handleCorfirm:()=>{
                            cor.close()
                            postFetch({
                                url:MNG_PROCESS_UPDATE,
                                values:{
                                    id:missionDetail.id,
                                    missionName:values?.missionName,
                                    missionDetail:values?.missionDetail,
                                    year: newYear,
                                    month: newMonth,
                                    missionStatus:1,
                                    isDelay:0
                                },
                                successCallback:()=>{
                                    alertBox({text:"操作成功",severity:"success"})
                                    postFetch({
                                        url:MNG_ADD_BULLETIN_URL,
                                        values:{
                                            stuId:missionDetail.stuId,
                                            title:"新的培养过程任务已发布",
                                            content:"你有新的任务，请及时查看并按时完成"
                                        }
                                    })
                                    onClose()
                                }
                            })
                        }
                    })
                }}>
                {({handleChange,handleSubmit,handleBlur,values,errors,touched})=>(
                    <form onSubmit={handleSubmit} className="dialogForm">
                        <TextField
                            InputProps={{readOnly:true}}
                            label="任务名称"
                            fullWidth
                            margin="normal"
                            name="missionName"
                            value={values?.missionName}
                            onBlur={handleBlur}
                            variant="outlined"
                            onChange={handleChange}
                        />

                        <TextField
                            error={Boolean(touched.missionDetail && errors.missionDetail)}
                            label="任务内容"
                            fullWidth
                            multiline
                            rows={4}
                            margin="normal"
                            name="missionDetail"
                            value={values?.missionDetail}
                            onBlur={handleBlur}
                            variant="outlined"
                            onChange={handleChange}/>

                        <TextField
                            error={Boolean(touched.predictDate && errors.predictDate)}
                            label="预计完成时间"
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
