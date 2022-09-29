import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../../layouts/Context";
import ContentStyleForPage from "../../../../../components/Style/ContentStyleForPage";
import {
    MNG_GET_ALL_PROCESS_DELAY,
    MNG_PROCESS_GROUP_APPROVE,
    MNG_PROCESS_UPDATE
} from "../../../../../settings";
import alertBox from "../../../../../components/AlertBox";
import confirmModal from "../../../../../components/ConfirmModal";
import {postFetch} from "../../../../../base";
import {Checkbox} from "@mui/material";
import {Button, Divider, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@material-ui/core";
import FormControlLabel from "@mui/material/FormControlLabel";
import ProcessDelay from "../../processForm/processApplyForm/ProcessDelay";

export default function ProcessForTeaApplyDelay(props){
    const {length, onRefresh}=props
    const {userInfo}=useContext(UserContext)
    const classes=ContentStyleForPage()
    const [refresh,setRefresh]=useState(false)
    const [check,setCheck]=useState(new Array(length).fill(false))
    const [onlineAgree, setOnlineAgree]=useState([])
    const [allDelay,setAllDelay]=useState([])
    const [disOpen,setDisOpen]=useState(false)
    const [list,setList]=useState({})
    const [allCheck, setAllCheck]=useState(false)

    const getAllDelay=()=>{
        fetch(`${MNG_GET_ALL_PROCESS_DELAY}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setAllDelay(res?.data || [])
            })
    }

    const handleAgreeAll=()=>{
        if(onlineAgree.length===0){
            alertBox({text:"没有选择任何任务，无法一键提交", severity:"error"})
            return
        }
        const cor=confirmModal({
            title:"确定要同意选中的延期任务申请吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_GROUP_APPROVE,
                    values:{
                        list:onlineAgree,
                        status:1,
                        isDelay:1
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
            title:"确认同意这一个任务的延期申请吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_UPDATE,
                    values:{
                        id:mission.id,
                        missionStatus:1,
                        isDelay: 1,
                        year:mission.wishYear,
                        month:mission.wishMonth
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
        getAllDelay()//获取所有的某导师名下的延期申请
    },[refresh])

    useEffect(()=>{
        if (allCheck == true){
            setCheck(new Array(length).fill(true))
            setOnlineAgree(allDelay)
        }
        else{
            setCheck(new Array(length).fill(false))
            setOnlineAgree([])
        }
    },[allCheck])

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Checkbox checked={allCheck} onChange={(event, checked)=>{
                    if(checked===true){
                        setAllCheck(checked)
                    }
                    if(checked===false){
                        setAllCheck(false)
                    }
                }}>全选</Checkbox>

                <Typography align="center" color="textPrimary" size="small" >
                    处理学生任务延期申请
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
                    {allDelay.map((list,index)=>(
                        <TableRow>
                            <TableCell align="center">
                                <FormControlLabel control={<Checkbox checked={check[index]} onChange={(e,checked)=>{
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
                                }}/> } />
                            </TableCell>
                            <TableCell>{list.stuName}</TableCell>
                            <TableCell align="center">
                                【{list.stuName}】想把【{list.category}】的完成时间延期到{list.wishYear}年{list.wishMonth}月
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

            <ProcessDelay open={disOpen}
                          onClose={()=>{
                              setDisOpen(false)
                          }}
                          onRefresh={()=>{
                              onRefresh()
                              setRefresh((p)=>!p)
                          }}
                          list={list}/>

        </div>
    )
}
