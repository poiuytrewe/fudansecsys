import {
    AppBar,
    Box,
    Button,
    Dialog,
    makeStyles,
    Slide,
    Table,
    TableBody, TableCell, TableHead,
    TableRow,
    Toolbar,
    Typography
} from "@material-ui/core";
import React, {useState} from "react";
import {Checkbox} from "@mui/material";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import {MNG_ADD_BULLETIN_URL, MNG_PROCESS_GROUP_APPROBE_DELAY, MNG_PROCESS_GROUP_APPROVE} from "../../../../settings";
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

export default function ProcessForTeachDelay(props){
    const classes=useStyles()
    const {open, onClose, delayList}=props
    const [check,setCheck]=useState(false)

    const Transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="up" ref={ref} {...props} />;
    });

    let onlineAgree=[]

    // const handleAgree=()=>{
    //     const cor=confirmModal({
    //         title:"未勾选的任务默认为不同意，确认要提交吗？",
    //         handleCorfirm:()=>{
    //             cor.close()
    //             postFetch({
    //                 url:MNG_PROCESS_GROUP_APPROVE,
    //                 values:{
    //                     list:onlineAgree,
    //                     status:1,
    //                     isDelay:1
    //                 },
    //                 successCallback:()=>{
    //                     postFetch({
    //                         url:MNG_PROCESS_GROUP_APPROVE,
    //                         values:{
    //                             list:onlineDisAgree,
    //                             status:1
    //                         },
    //                         successCallback:()=>{
    //                             alertBox({text:"已完成处理",severity:"success"})
    //                             onClose()
    //                         }
    //                     })
    //                 }
    //             })
    //             if(onlineDisAgree.length!==0){
    //                 let i=0
    //                 onlineDisAgree.map((process,index)=>{
    //                     if(index===0){//第一个
    //                         postFetch({
    //                             url:MNG_ADD_BULLETIN_URL,
    //                             values:{
    //                                 stuId:process.stuId,
    //                                 title:"你有任务延期申请未通过",
    //                                 content:"你有任务延期申请未通过，请及时查看"
    //                             }
    //                         })
    //                     }
    //                     if(onlineDisAgree[i].stuId!==process.stuId){//此时就说明换了
    //                         i=index
    //                         postFetch({
    //                             url:MNG_ADD_BULLETIN_URL,
    //                             values:{
    //                                 stuId:process.stuId,
    //                                 title:"你有任务延期申请未通过",
    //                                 content:"你有任务延期申请未通过，请及时查看"
    //                             }
    //                         })
    //                     }
    //                 })
    //             }
    //         }
    //     })
    // }

    return (
        <Dialog open={open}  fullScreen TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
                <Toolbar>
                    <Checkbox defaultChecked={false} onChange={(event, checked)=>{
                        if(checked===true){
                            onlineAgree.push(delayList)
                            setCheck(true)
                        }
                        if(checked==false){
                            onlineAgree=[]
                            setCheck(false)
                        }
                    }} color="secondary"/>全选

                    <Typography align="center" color="inherit" className={classes.title}>
                        批量处理学生任务延期申请
                    </Typography>

                    <Button autoFocus color="inherit" onClick={()=>{
                    }}>一键同意</Button>
                </Toolbar>
            </AppBar>
            <Box>
                <Table>
                    <TableHead>
                        <TableCell></TableCell>
                        <TableCell>申请人</TableCell>
                        <TableCell align="center">申请内容</TableCell>
                        <TableCell align="center">操作</TableCell>
                    </TableHead>
                    <TableBody>
                        {delayList.map((list)=>(
                            <TableRow>
                                <TableCell align="center">
                                    <Checkbox defaultChecked={check} onChange={(event,checked)=>{
                                        if(checked===true){
                                            onlineAgree.push(list)
                                        }
                                        if(checked===false){
                                            onlineAgree=onlineAgree.filter((a)=>{
                                                return a.id!==list.id
                                            })
                                        }
                                    }} />
                                </TableCell>
                                <TableCell>{list.stuName}</TableCell>
                                <TableCell align="center">
                                    【{list.stuName}】希望将【{list.category}】推迟到{list.wishYear}年{list.wishMonth}月
                                </TableCell>
                                <TableCell align="center">
                                    <Button
                                        size="small"
                                        variant="outlined"
                                        color="primary"
                                        onClick={()=>{}}>同意</Button>
                                    <Button
                                        size="small"
                                        variant="outlined"
                                        color="primary"
                                        onClick={()=>{}}>不同意</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Box>
        </Dialog>
    )
}
