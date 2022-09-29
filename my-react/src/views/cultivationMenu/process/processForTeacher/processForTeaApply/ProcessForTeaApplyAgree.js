import ContentStyleForPage from "../../../../../components/Style/ContentStyleForPage";
import {
    Button,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@material-ui/core";
import React, {useContext, useEffect, useState} from "react";
import {Checkbox} from "@mui/material";
import confirmModal from "../../../../../components/ConfirmModal";
import {MNG_GET_ALL_PROCESS_APPROVE, MNG_PROCESS_GROUP_APPROVE, MNG_PROCESS_UPDATE} from "../../../../../settings";
import {UserContext} from "../../../../../layouts/Context";
import {postFetch} from "../../../../../base";
import alertBox from "../../../../../components/AlertBox";
import ProcessAffirmDelay from "../../processForm/processApplyForm/ProcessAffirmDelay";

export default function ProcessForTeaApplyAgree(props){
    const {length, onRefresh}=props
    const {userInfo}=useContext(UserContext)
    const classes=ContentStyleForPage()
    const [refresh,setRefresh]=useState(false)
    const [check,setCheck]=useState(new Array(length).fill(false))
    const [onlineAgree, setOnlineAgree]=useState([])
    const [allApprove,setAllApprove]=useState([])
    const [disOpen,setDisOpen]=useState(false)
    const [list,setList]=useState({})
    const [allCheck, setAllCheck]=useState(false)

    const getAllApprove= async ()=>{//获取该导师手下的所有已提交任务
        try{
            let response=await fetch(`${MNG_GET_ALL_PROCESS_APPROVE}?name=${userInfo.name}`)
            return await response.json()
        }
        catch (error){
            console.log(error)
        }
    }

    const handleAgreeAll=()=>{
        if(onlineAgree.length===0){
            alertBox({text:"没有选择任何任务，无法一键提交", severity:"error"})
            return
        }
        const cor=confirmModal({
            title:"确定要同意选中的完成任务申请吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_GROUP_APPROVE,
                    values:{
                        list:onlineAgree,
                        status:3,
                        isDelay:0
                    },
                    successCallback:()=>{
                        alertBox({text:"提交成功",severity:"success"})
                        onRefresh()
                        setRefresh((p)=>!p)
                        setAllCheck(false)
                    }
                })
            }
        })
    }

    const handleOneAgree=(mission)=>{
        const cor=confirmModal({
            title:"确认同意这一个完成任务的申请吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_UPDATE,
                    values:{
                        id:mission.id,
                        missionStatus:3
                    },
                    successCallback:()=>{
                        alertBox({text:"提交成功",severity:"success"})
                        onRefresh()
                        setRefresh((p)=>!p)
                    }

                })
            }
        })
    }

    useEffect(()=>{
        getAllApprove().then((res)=>{
            setAllApprove(res?.data || [])
        })
    },[refresh])

    useEffect(()=>{
        if (allCheck == true){
            setCheck(new Array(length).fill(true))
            setOnlineAgree(allApprove)
        }
        else {
            setCheck(new Array(length).fill(false))
            setOnlineAgree([])
        }
    },[allCheck])

    return (
            <div className={classes.root}>
                    <div className={classes.header}>
                        <Checkbox checked={allCheck} onChange={(event, checked)=>{
                            if(checked===true){
                              setAllCheck(true)
                            }
                            if(checked===false){
                                setAllCheck(false)
                            }
                        }}>全选</Checkbox>

                        <Typography align="center" color="textPrimary" size="small" >
                            处理学生任务完成申请
                        </Typography>

                        <Button
                            size="small"
                            color="primary"
                            variant="outlined"
                            onClick={()=>{
                                handleAgreeAll()
                            }}>一键同意</Button>
                    </div>

                <Divider/>

                <Table>
                    <TableHead>
                        <TableCell></TableCell>
                        <TableCell>申请人</TableCell>
                        <TableCell align="center">申请内容</TableCell>
                        <TableCell>预计完成时间</TableCell>
                        <TableCell align="center">操作</TableCell>
                    </TableHead>
                    <TableBody>
                        {allApprove.map((list,index)=>(
                            <TableRow>
                                <TableCell align="center">
                                    <Checkbox checked={check[index]} onChange={(e,checked)=>{
                                        if(checked===true){
                                            setCheck([...check.slice(0,index),true,...check.slice(index+1,length)])
                                            setOnlineAgree([...onlineAgree,list])
                                        }
                                        if(checked==false){
                                            setCheck([...check.slice(0,index),false,...check.slice(index+1,length)])
                                            setOnlineAgree([...onlineAgree.filter((a)=>{
                                                return a.id!==list.id
                                            })])
                                        }
                                    }}/>
                                </TableCell>
                                <TableCell>{list.stuName}</TableCell>
                                <TableCell align="center">
                                    【{list.stuName}】提交了【{list.category}】任务的完成申请
                                </TableCell>
                                <TableCell>{list.year}.{list.month}</TableCell>
                                <TableCell align="center">
                                    <Button
                                        size="small"
                                        variant="text"
                                        color="primary"
                                        onClick={()=>{handleOneAgree(list)}}>同意</Button>
                                    <Button
                                        size="small"
                                        variant="text"
                                        color="primary"
                                        onClick={()=>{
                                            setDisOpen(true)
                                            setList(list)
                                        }}>不同意</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>

                <ProcessAffirmDelay open={disOpen}
                                    onRefresh={()=>{
                                        onRefresh()
                                        setRefresh((p)=>!p)
                                    }}
                                    onClose={()=>{
                                        setDisOpen(false)
                                    }}
                                    list={list}/>

            </div>
    )
}
