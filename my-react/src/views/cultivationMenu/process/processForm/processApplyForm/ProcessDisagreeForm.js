import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import React from "react";
import confirmModal from "../../../../../components/ConfirmModal";
import {postFetch} from "../../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_UPDATE} from "../../../../../settings";

const useStyles=makeStyles((theme)=>({
    root:{
        padding:theme.spacing(3),
    }
}))

export default function ProcessDisagreeForm(props){
    const {open, onClose, onRefresh, list}=props
    const classes=useStyles()

    return (
        <Dialog open={open} onClose={onClose} className={classes.root}>
            <DialogTitle>{list.missionStatus===2 ? "拒绝任务完成申请":"拒绝任务延期申请"}</DialogTitle>
            <Formik initialValues={list} onSubmit={(values)=>{
                const cor=confirmModal({
                    title: list.missionStatus===2 ? "确认要拒绝这个任务完成申请吗?":"确认要拒绝这个任务延期申请吗?",
                    handleCorfirm:()=>{
                        cor.close()
                        postFetch({
                            url:MNG_PROCESS_UPDATE,
                            values:{
                                id:list.id,
                                missionStatus:1
                            },
                            successCallback:()=>{
                                postFetch({
                                    url:MNG_ADD_BULLETIN_URL,
                                    values:{
                                        stuId:list.stuId,
                                        title:list.missionStatus===2 ? "你的【"+list.category+"】完成申请未通过":"你的【"+list.category+"】延期申请未通过",
                                        content:values?.reason? "申请未通过的理由是【"+values?.reason+"】，请及时修改":"导师未表明拒绝理由，如有异议，请找导师"
                                    },
                                    successCallback:()=>{
                                        onClose()
                                        onRefresh()
                                    }
                                })
                            }
                        })
                    }
                })
            }}
                    >{({values,handleBlur,handleSubmit,handleChange})=>(
                        <form onSubmit={handleSubmit} className="dialogForm">
                            <TextField
                                multiline
                                rows={4}
                                label="请填写拒绝的理由"
                                fullWidth
                                margin="normal"
                                name="reason"
                                variant="outlined"
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values?.reason}
                                />
                            <Box my={2}>
                                <Button
                                    fullWidth
                                    color="primary"
                                    variant="contained"
                                    type="submit"
                                    size="large">确认</Button>
                                <Button
                                    fullWidth
                                    color="secondary"
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
