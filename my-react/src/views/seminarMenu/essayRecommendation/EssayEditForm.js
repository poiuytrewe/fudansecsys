import React from "react";
import { Formik } from "formik";
import { postFetch } from "src/base";
import {Box, Button, TextField, Dialog, makeStyles, DialogTitle} from "@material-ui/core";
import {ADD_ARTICLE_URL, EDIT_ARTICLE_URL, GET_ALL_SEMINAR_URL, MNG_ADD_BULLETIN_URL} from "src/settings";
import alertBox from "../../../components/AlertBox";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3)
    },
}));

export default function EssayEditForm(props){
    const {open, onClose, articleDetail} =props;
    const classes=useStyles();

    const handleClose=()=>{ //关闭窗口
        onClose();
    }

    return (
        <Dialog open={open} onClose={handleClose} className={classes.root}>
            <DialogTitle onClose={handleClose}>
                {articleDetail?.id? "编辑文章" : "添加文章"}
            </DialogTitle>
            <Formik initialValues={articleDetail}
                    onSubmit={(values)=>{
                        postFetch({
                            url: articleDetail?.id? EDIT_ARTICLE_URL : ADD_ARTICLE_URL,
                            values,
                            successCallback:()=>{
                                fetch(`${GET_ALL_SEMINAR_URL}?limit=1`)
                                    .then((res)=>res.json())
                                    .catch((error)=>console.error(error))
                                    .then((response)=>{
                                        if(response?.data[0]?.speakerStuId){
                                            postFetch({
                                                url:MNG_ADD_BULLETIN_URL,
                                                values:{
                                                    stuId:response?.data[0]?.speakerStuId,
                                                    title:"推荐文章已发布",
                                                    content:"推荐文章已发布，请及时阅读文章并上传演讲资源"
                                                }
                                            })
                                        }
                                    })
                                alertBox({ text: "操作成功", severity: "success" });
                                handleClose();
                            }
                        })
                    }}
            >
                {({
                    errors,
                    handleBlur,
                    handleChange,
                    handleSubmit,
                    isSubmitting,
                    touched,
                    values,
                })=>(
                    <form onSubmit={handleSubmit} className="dialogForm">
                        <TextField
                            fullWidth
                            label="论文演讲时间"
                            name="date"
                            type="date"
                            margin="normal"
                            value={values?.date}
                            onChange={handleChange}
                            InputLabelProps={{shrink:true}}
                        />
                        <TextField
                            label="论文名称"
                            fullWidth
                            margin="normal"
                            name="title"
                            value={values?.title}
                            variant="outlined"
                            onBlur={handleBlur}
                            onChange={handleChange}
                        />
                        <TextField
                            multiline
                            rows={3}
                            label="论文链接"
                            fullWidth
                            margin="normal"
                            name="content"
                            value={values?.content}
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
                )}
            </Formik>
        </Dialog>
    )
}
