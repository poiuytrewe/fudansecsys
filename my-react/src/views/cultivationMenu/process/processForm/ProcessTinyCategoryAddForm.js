import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import * as Yup from "yup";
import alertBox from "../../../../components/AlertBox";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_ADD_TINY_PROCESS_CATEGORY, MNG_PROCESS_ADD} from "../../../../settings";

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

export default function ProcessTinyCategoryAddForm(props){
    const classes=useStyles()
    const {open, type, studyType, onClose}=props

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle onClose={onClose}>
                添加任务类别
            </DialogTitle>
            <Formik
                initialValues={{}}
                validationSchema={Yup.object().shape({
                    processCategory:Yup.string().required("请输入具体的类别名称"),
                })}
                onSubmit={(values)=>{
                    const cor=confirmModal({
                        title:"确定要添加这个类别吗",
                        handleCorfirm:()=>{
                            cor.close()
                            postFetch({
                                url:MNG_ADD_TINY_PROCESS_CATEGORY,
                                values:{
                                    type:type,
                                    studyType:studyType,
                                    processCategory:values?.processCategory
                                },
                                successCallback:()=>{
                                    alertBox({text:"添加成功",severity:"success"})
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
                                variant="outlined"
                                onBlur={handleBlur}
                                InputProps={{readOnly:true}}
                                />
                        )}
                        <TextField
                            label="对应学生类型"
                            fullWidth
                            margin="normal"
                            value={STUDYTYPE[studyType]}
                            onBlur={handleBlur}
                            variant="outlined"
                            InputProps={{readOnly:true}}
                        />

                        <TextField
                            error={Boolean(touched.processCategory && errors.processCategory)}
                            label="任务类别"
                            name="processCategory"
                            value={values?.processCategory}
                            margin="normal"
                            fullWidth
                            variant="outlined"
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
        </Dialog>)
}
