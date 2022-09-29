import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import {postFetch} from "../../../../base";
import {MNG_UPDATE_SCORE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import React from "react";
import confirmModal from "../../../../components/ConfirmModal";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function TeaCommentForTeaForm(props){
    const {open, onClose, scoreDetail}=props
    const classes=useStyles()

    return (
        <Dialog open={open} className={classes.root}>
            <DialogTitle>
                {scoreDetail.teaComment ? "修改导师评价" : "添加导师评价"}
            </DialogTitle>
            <Formik
                initialValues={scoreDetail}
                onSubmit={(values) => {
                    const cor = confirmModal({
                        title:"确定要提交导师评语吗？",
                        handleCorfirm:()=>{
                            cor.close()
                            postFetch({
                                url:MNG_UPDATE_SCORE,
                                values:{
                                    id:scoreDetail.id,
                                    teaComment:values?.teaComment
                                },
                                successCallback:()=>{
                                    alertBox({ text: "操作成功", severity: "success" });
                                    onClose()
                                }
                            })
                        }
                    })
                }}
            >
                {({
                      handleBlur,
                      handleChange,
                      handleSubmit,
                      values,
                  }) => (
                    <form
                        onSubmit={handleSubmit} className="dialogForm">
                        <TextField
                            multiline
                            rows={15}
                            label="评语"
                            fullWidth
                            margin="normal"
                            name="teaComment"
                            value={values.teaComment}
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
