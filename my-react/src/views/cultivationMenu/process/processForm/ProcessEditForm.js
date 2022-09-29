import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import * as Yup from "yup";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_PROCESS_UPDATE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))
export default function ProcessEditForm(props){
   const classes=useStyles()
    const {open, onClose, missionDetail}=props

    const time= missionDetail.month<10?`${missionDetail.year}-0${missionDetail.month}`:`${missionDetail.year}-${missionDetail.month}`

   return (
       <Dialog open={open} onClose={onClose} className={classes.root}>
           <DialogTitle onClose={onClose}>
               编辑学生任务
           </DialogTitle>
           <Formik initialValues={missionDetail}
                   onSubmit={(values)=>{
                       const newYear=parseInt(values.predictTime.slice(0,4))//预计完成年份
                       const newMonth=parseInt(values.predictTime.slice(5,7))//预计完成月份
                       const cor=confirmModal({
                           title:"确定要做出修改吗？",
                           handleCorfirm:()=>{
                               cor.close()
                               postFetch({
                                   url:MNG_PROCESS_UPDATE,
                                   values:{
                                       id:missionDetail.id,
                                       category:values?.category,
                                       missionStatus:values?.missionStatus,
                                       year:newYear,
                                       month:newMonth
                                   },
                                   successCallback:()=>{
                                       alertBox({text:"修改成功",severity:"success"})
                                       onClose()
                                   }
                               })
                           }
                       })
                   }}
                   >{({handleSubmit,handleChange,handleBlur,values})=>(
                       <form onSubmit={handleSubmit} className="dialogForm">
                           <TextField
                               label="任务"
                               fullWidth
                               margin="normal"
                               name="category"
                               value={values?.category}
                               onChange={handleChange}
                               onBlur={handleBlur}
                               variant="outlined"/>
                           <TextField
                               label="任务状态"
                               fullWidth
                               select
                               margin="normal"
                               name="missionStatus"
                               value={values?.missionStatus}
                               onChange={handleChange}
                               onBlur={handleBlur}
                               SelectProps={{native:true}}
                               variant="outlined">
                               <option value={0}>已逾期</option>
                               <option value={1}>未完成</option>
                               <option value={2}>已提交</option>
                               <option value={3}>已完成</option>
                               <option value={4}>提交延期申请</option>
                           </TextField>
                           <TextField
                               label="预计完成时间"
                               fullWidth
                               InputLabelProps={{shrink:true}}
                               type="month"
                               margin="normal"
                               name="predictTime"
                               value={values?.predictTime}
                               onBlur={handleBlur}
                               onChange={handleChange}
                               variant="outlined"/>

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
           )
           }</Formik>
       </Dialog>
   )
}
