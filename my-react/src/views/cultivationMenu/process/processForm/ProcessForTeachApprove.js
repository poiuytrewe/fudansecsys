import {
    AppBar, Box, Button,
    Dialog,
    DialogTitle, Divider, IconButton,
    makeStyles, Slide,
    Table,
    TableBody,
    TableCell,
    TableRow,
    Typography
} from "@material-ui/core";
import {Formik} from "formik";
import React, {useContext, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {Checkbox, List, Toolbar} from "@mui/material";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_GROUP_APPROVE} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";

const useStyles = makeStyles((theme) => ({
    appBar: {
        position: "relative",
    },
    title: {
        marginLeft: theme.spacing(2),
        flex: 1,
    },
}));

export default function ProcessForTeachApprove(props){//导师批量批准
    const classes=useStyles()
    const {open, onClose, approveList}=props

    const Transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="up" ref={ref} {...props} />;
    });

    let onlineAgree=approveList//这个的意思是导师同意的任务，因为默认是全选，所以默认同意的任务是全部
    let onlineDisAgree=[]//这个的意思就是导师不同意的任务

    const handleAgree=()=>{
        const cor=confirmModal({
            title:"未勾选的任务默认为不同意，确认要提交吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({//首先先给同意的发
                    url: MNG_PROCESS_GROUP_APPROVE ,
                    values:{
                        list:onlineAgree,
                        status:3,//同意的missionStatus是3
                    },
                    successCallback:()=>{
                        postFetch({
                            url: MNG_PROCESS_GROUP_APPROVE,
                            values:{
                                list:onlineDisAgree,
                                status:1
                            },
                            successCallback:()=>{
                                alertBox({text:"已完成处理",severity:"success"})
                                onClose()
                            }
                        })
                    }
                })
                if(onlineDisAgree.length!==0){
                    let i=0
                    onlineDisAgree.map((process,index)=>{
                        if(index===0){//第一个
                            postFetch({
                                url:MNG_ADD_BULLETIN_URL,
                                values:{
                                    stuId:process.stuId,
                                    title:"你有任务完成申请未通过",
                                    content:"你有任务完成申请未通过，请及时查看"
                                }
                            })
                        }
                        if(onlineDisAgree[i].stuId!==process.stuId){//此时就说明换了
                            i=index
                            postFetch({
                                url:MNG_ADD_BULLETIN_URL,
                                values:{
                                    stuId:process.stuId,
                                    title:"你有任务完成申请未通过",
                                    content:"你有任务完成申请未通过，请及时查看"
                                }
                            })
                        }
                    })
                }
            }
        })
    }

    return (
        <Dialog open={open}  fullScreen TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
                <Toolbar>
                    <Typography align="center" color="inherit" className={classes.title}>
                        批量处理学生申请
                    </Typography>

                    <Button autoFocus color="inherit" onClick={()=>{
                        handleAgree()
                    }}>同意</Button>
                </Toolbar>
            </AppBar>
            <Box>
                <Table>
                    <TableBody>
                        {approveList.map((list)=>(
                            <TableRow>
                                <TableCell align="center">
                                    <Checkbox defaultChecked={true} onChange={(event,checked)=>{
                                        if(checked===true){
                                            onlineAgree.push(list)
                                            onlineDisAgree=onlineDisAgree.filter((a)=>{
                                                return a.id!==list.id
                                            })
                                        }
                                        if(checked===false){
                                            onlineAgree=onlineAgree.filter((a)=>{
                                                return a.id!==list.id
                                            })
                                            onlineDisAgree.push(list)
                                        }
                                    }}/>
                                </TableCell>
                                <TableCell align="center">
                                    【{list.stuName}】提交了【{list.category}】的完成申请
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Box>
        </Dialog>
    )
}
