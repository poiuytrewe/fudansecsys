import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import {MNG_ADD_PROCESS_CATEGORY, MNG_UPDATE_PROCESS_CATEGORY} from "../../../../settings";
import {postFetch} from "../../../../base";
import alertBox from "../../../../components/AlertBox";
import confirmModal from "../../../../components/ConfirmModal";
import * as Yup from "yup";

const useStyles=makeStyles((theme)=>({
    root:{
        padding: theme.spacing(3),
    }
}))

const TYPES={
    1:"学术科研",
    2:"专业技术",
    3:"思政服务"
}

const STUDYTYPE={
    1:"学硕",
    2:"专硕",
    3:"学工"
}

export default function ProcessAddForm(props){
   const classes=useStyles()
    const {open, onClose, type, studyType, tinyCategory, categoryDetail}=props

   return (
       <Dialog open={open} onClose={onClose} className={classes.root}>
           <DialogTitle onClose={onClose}>
               {categoryDetail.id? "编辑任务":"添加任务"}
           </DialogTitle>
           <Formik
               initialValues={categoryDetail}
               validationSchema={Yup.object().shape({
                   categoryMission:Yup.string().required("请输入具体的任务名称"),
               })}
               onSubmit={(values)=>{
                   let newYear= values.year ?values.year:1
                   let newMonth= values.month ?values.month:1
                   const cor=confirmModal({
                       title:categoryDetail?"确定要修改这个任务吗":"确定要添加这个任务吗",
                       handleCorfirm:()=>{
                           cor.close()
                           postFetch({
                               url:categoryDetail.id? MNG_UPDATE_PROCESS_CATEGORY:MNG_ADD_PROCESS_CATEGORY,
                               values:{
                                   id:values?.id,
                                   type:type,
                                   studyType:studyType,
                                   processCategory:tinyCategory,
                                   categoryMission:values?.categoryMission,
                                   year:newYear,
                                   month:newMonth
                               },
                               successCallback:()=>{
                                   alertBox({text:"操作成功",severity:"success"})
                                   onClose()
                               }
                           })
                       }
                   })
           }}>
               {({handleChange,handleSubmit,handleBlur,values,errors,touched})=>(
                   <form onSubmit={handleSubmit} className="dialogForm">
                       {type!==0 && (
                           <TextField
                               label="板块类型"
                               fullWidth
                               margin="normal"
                               value={TYPES[type]}
                               onBlur={handleBlur}
                               variant="outlined"
                               InputProps={{readOnly:true}}/>
                       )}
                       <TextField
                           label="对应学生类型"
                           fullWidth
                           margin="normal"
                           value={STUDYTYPE[studyType]}
                           onBlur={handleBlur}
                           variant="outlined"
                           InputProps={{readOnly:true}}/>

                       <TextField
                           label="任务类别"
                           fullWidth
                           margin="normal"
                           value={tinyCategory}
                           onBlur={handleBlur}
                           variant="outlined"
                           InputProps={{readOnly:true}}/>

                       <TextField
                           error={Boolean(touched.categoryMission && errors.categoryMission)}
                           label="任务"
                           fullWidth
                           margin="normal"
                           name="categoryMission"
                           value={values?.categoryMission}
                           onChange={handleChange}
                           onBlur={handleBlur}
                           variant="outlined"/>


                       <TextField
                           select
                           error={Boolean(touched.year && errors.year)}
                           label="预计在第几学年完成"
                           fullWidth
                           margin="normal"
                           name="year"
                           value={values?.year}
                           onChange={handleChange}
                           onBlur={handleBlur}
                           variant="outlined"
                           SelectProps={{native:true}}
                       >
                           <option value={1}>1</option>
                           <option value={2}>2</option>
                           <option value={3}>3</option>
                           <option value={4}>4</option>
                           <option value={5}>5</option>
                           <option value={6}>6</option>
                       </TextField>

                       <TextField
                           select
                           label="预计完成的月份"
                           fullWidth
                           margin="normal"
                           name="month"
                           value={values?.month}
                           onChange={handleChange}
                           onBlur={handleBlur}
                           variant="outlined"
                           SelectProps={{native:true}}
                       >
                           <option value={1}>1</option>
                           <option value={2}>2</option>
                           <option value={3}>3</option>
                           <option value={4}>4</option>
                           <option value={5}>5</option>
                           <option value={6}>6</option>
                           <option value={7}>7</option>
                           <option value={8}>8</option>
                           <option value={9}>9</option>
                           <option value={10}>10</option>
                           <option value={11}>11</option>
                           <option value={12}>12</option>
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
