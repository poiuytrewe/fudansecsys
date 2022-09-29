import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_ADD_SCORE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import React from "react";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

const values={
    year:null,
    season:null,
    stuId:null,
    mentor:null,
    comment:null
}

export default function ScoreAddForm(props){
    const {open, userDetail, onClose, teacherStuId}=props
    const classes=useStyles()

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle onClose={onClose}>
                新建季度评分
            </DialogTitle>

            <Formik
                initialValues={values}
                onSubmit={(values)=>{
                    if (!values?.year || !values?.season || !values?.comment){
                        alertBox({text:"请完整填写后再提交", severity:"error"})
                    }
                    else {
                        if (parseInt(values?.season)==0){
                            alertBox({text:"请重新选择季度", severity:"error"})
                        }
                        else {
                            const cor=confirmModal({
                                title:"只有一次提交机会，请检查无误再确认提交",
                                handleCorfirm:()=>{
                                    cor.close()
                                    postFetch({
                                        url:MNG_ADD_SCORE,
                                        values:{
                                            stuId:userDetail.stuId,
                                            stuName: userDetail.name,
                                            stuType :userDetail.type,
                                            mentor:userDetail.mentor,
                                            year:values?.year,
                                            season:values?.season,
                                            comment:values?.comment
                                        },
                                        successCallback:()=>{
                                            alertBox({ text: "操作成功", severity: "success" });
                                            postFetch({
                                                url:MNG_ADD_BULLETIN_URL,
                                                values:{
                                                    stuId:teacherStuId,
                                                    title:"学生的季度自评已完成",
                                                    content:"您的学生["+userDetail.name+"]的"+values.year+"年第"+values.season+"季度的自评已经完成，请及时为他的季度表现评分"
                                                }
                                            })
                                            onClose()
                                        }
                                    })
                                }
                            })
                        }
                    }
                }}
            >{({handleBlur,handleChange,handleSubmit,values,
                           })=>(
                               <form onSubmit={handleSubmit}  className="dialogForm">
                                   <TextField
                                       label="年份"
                                       fullWidth
                                       placeholder="请填写阿拉伯数字"
                                       margin="normal"
                                       name="year"
                                       variant="outlined"
                                       value={values?.year}
                                       onBlur={handleBlur}
                                       onChange={handleChange}
                                   />
                                   <TextField
                                       select
                                       fullWidth
                                       label="季度"
                                       name="season"
                                       margin="normal"
                                       onChange={handleChange}
                                       value={values?.season}
                                       variant="outlined"
                                       SelectProps={{
                                           native: true,
                                       }}
                                   >
                                       <option value={0}>0</option>
                                       <option value={1}>1</option>
                                       <option value={2}>2</option>
                                       <option value={3}>3</option>
                                       <option value={4}>4</option>
                                   </TextField>
                                   <TextField
                                       multiline
                                       rows={4}
                                       label="评语"
                                       fullWidth
                                       margin="normal"
                                       name="comment"
                                       value={values?.comment}
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
                                       >
                                           提交
                                       </Button>
                                       <Button
                                           fullWidth
                                           color="secondary"
                                           size="large"
                                           onClick={()=>{
                                               onClose()
                                           }}
                                           variant="contained"
                                       >取消</Button>
                                   </Box>
                               </form>

            )}</Formik>

        </Dialog>
    )
}
