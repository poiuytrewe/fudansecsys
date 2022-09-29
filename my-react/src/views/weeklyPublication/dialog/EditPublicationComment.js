import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import {postFetch} from "../../../base";
import alertBox from "../../../components/AlertBox";
import React from "react";
import {UPDATE_WEEKLYPUBLICATION} from "../../../settings";
import confirmModal from "../../../components/ConfirmModal";
const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function EditPublicationComment(props){
    const {open, publication, onClose}=props
    const classes=useStyles()

    return (
        <Dialog open={open} className={classes.root}>
            <DialogTitle>
                {publication.comment ? "修改周报评语" : "添加周报评语"}
            </DialogTitle>

            <Formik initialValues={publication} onSubmit={(values)=>{
                const cor=confirmModal({
                    title:publication.comment? "确认要修改周报评语吗？" : "确认要添加周报评语吗？",
                    handleCorfirm:()=>{
                        cor.close()
                        postFetch({
                            url:UPDATE_WEEKLYPUBLICATION,
                            values:{
                                id:publication.id,
                                comment:values?.tempComment,
                                tempComment: values?.tempComment
                            },
                            successCallback:()=>{
                                alertBox({text:"操作成功",severity:"success"})
                                onClose()
                            }
                        })
                    }
                })
            }}>
                {({
                    handleBlur,handleChange,handleSubmit,values
                  })=>(
                      <form
                          onSubmit={handleSubmit} className="dialogForm">
                          <TextField
                              multiline
                              rows={15}
                              label="导师周报评语"
                              fullWidth
                              margin="normal"
                              name="tempComment"
                              value={values.tempComment}
                              variant="outlined"
                              onBlur={handleBlur}
                              onChange={handleChange}/>
                          <Box my={2}>
                              <Button
                                  fullWidth
                                  color="primary"
                                  size="large"
                                  type="submit"
                                  variant="contained">提交</Button>
                              <Button
                                  fullWidth
                                  color="secondary"
                                  size="large"
                                  onClick={()=>{
                                      postFetch({
                                          url:UPDATE_WEEKLYPUBLICATION,
                                          values:{
                                              id:publication.id,
                                              tempComment:values.tempComment
                                          },
                                          successCallback:()=>{
                                              alertBox({text:"已为您中途保存评论",severity:"success"})
                                              onClose()
                                          }
                                      })}}
                                  variant="contained"
                              >取消</Button>
                          </Box>
                      </form>
                )}
            </Formik>
        </Dialog>
    )
}
