import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import React from "react";
import {Formik} from "formik";
import {postFetch} from "../../../../base";
import {MNG_UPDATE_SCORE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import confirmModal from "../../../../components/ConfirmModal";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function ScoreEditForm(props){
    const {open, onClose, scoreDetail}=props
    const classes=useStyles()

    return (
        <Dialog
            onClose={onClose} open={open} className={classes.root}>
            <DialogTitle
                onClose={onClose}>
                修改季度评语
            </DialogTitle>
            <Formik
                initialValues={scoreDetail}
                onSubmit={(values) => {
                    const cor = confirmModal({
                        title:"确定要对这次的季度自评做出修改吗？",
                        handleCorfirm:()=>{
                            cor.close()
                            postFetch({
                                url:MNG_UPDATE_SCORE,
                                values:{
                                    id:scoreDetail.id,
                                    comment:values?.comment,
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
                                name="comment"
                                placeholde="自评请尽量简短"
                                value={values.comment}
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
