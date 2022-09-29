import ContentStyle from "../../../components/Style/ContentStyle";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../layouts/Context";
import {
    Box,
    Button,
    Card,
    Divider, Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@material-ui/core";
import {
    MNG_ADD_BULLETIN_URL, MNG_GET_ALL_USER,
    MNG_GET_PROCESS_LIST,
    MNG_PROCESS_DELETE, MNG_PROCESS_GROUPDONE,
    MNG_PROCESS_UPDATE
} from "../../../settings";
import {useHistory} from "react-router-dom";
import confirmModal from "../../../components/ConfirmModal";
import {deleteFetch, postFetch} from "../../../base";
import alertBox from "../../../components/AlertBox";
import ProcessDelayForm from "./processForm/ProcessDelayForm";
import ProcessEditForm from "./processForm/ProcessEditForm";
import {Checkbox} from "@mui/material";
import ProcessGroupDelayForm from "./processForm/ProcessGroupDelayForm";

const STATUS={
    0: "已逾期",
    1: "未完成",
    2: "已提交",
    3: "已完成",
    4: "提交延期申请"
}
const TYPES={
    0:"否",
    1:"是"
}

export default function ProcessForStu(props){//进来的可以是导师，管理员和自己，先设定三种身份
    const {userDetail}=props
    const classes=ContentStyle()
    const history=useHistory()
    const {userInfo}=useContext(UserContext)
    const [mission, setMission]=useState([])//所有的任务
    const [refresh, setRefresh]=useState(false)//刷新
    const [teacherStuId,setTeacherStuId]=useState("")//老师的stuId
    const [confirmForm,setConfirmForm]=useState(false)//确认窗口是否打开
    const [confirmDetail,setConfirmDetail]=useState({})//确认窗口的具体信息
    const [type,setType]=useState(1)//板块的种类，一共三种
    const [studyType,setStudyType]=useState(1)
    const [groupDelayOpen,setGroupDelayOpen]=useState(false)
    const [delayOpen,setDelayOpen]=useState(false)//延期窗口是否打开
    const [missionDetail,setMissionDetail]=useState({})//任务细则
    const [editOpen,setEditOpen]=useState(false)
    const [groupDelay,setGroupDelay]=useState([])
    let groupList=[] //groupList是需要批量处理的list

    const selfPermission= userInfo.stuId===userDetail.stuId//自己权限
    const mngPermission= userInfo.isEducateMng===1//管理员权限
    const mentorPermission= userInfo.name===userDetail.mentor//导师权限

    const getAllMission=async ()=>{//获取属于这个学生的所有任务
        try{
            let response=await fetch(
                `${MNG_GET_PROCESS_LIST}?stuName=${userDetail.name}&stuId=${userDetail.stuId}`
            )
            return await response.json()
        }catch (error){
            console.log(error)
        }
    }

    const queryTeacher=()=>{//获取老师的stuId
        fetch(`${MNG_GET_ALL_USER}?name=${userDetail?.mentor}&limit=1`)
            .then((r)=>r.json())
            .catch((error)=>console.error(error))
            .then((r)=>{
                setTeacherStuId(r?.data[0]?.stuId || "")
            })
    }

    useEffect(()=>{
        queryTeacher()
        getAllMission().then((res)=>{
            setMission(res?.data || [])
        })
    },[refresh])

    const handleDelete=(id)=>{
            const cor=confirmModal({
                title:"确定要删除任务吗？",
                handleCorfirm:()=>{
                    cor.close()
                    deleteFetch({
                        url:`${MNG_PROCESS_DELETE}?id=${id}`,
                        successCallback:()=>{
                            alertBox({text:"删除成功",severity:"success"})
                            setRefresh((prev)=>!prev)
                        }
                    })
                }
            })
    }

    const handleDone=(mis,index)=>{//上传完成申请
        if(mis.missionStatus===0){
            alertBox({text:"任务已逾期，请先提交延期申请",severity:"error"})
            return
        }
        if(mis.missionStatus===2){
            alertBox({text:"已提交，正在等待申请结果",severity:"error"})
            return;
        }
        if(mis.missionStatus===3){
            alertBox({text:"已经完成任务",severity:"error"})
            return;
        }
        if(mis.missionStatus===4){
            alertBox({text:"延期申请还未处理完毕",severity:"error"})
            return;
        }
        const cor=confirmModal({
            title:"确定要提交完成任务的请求吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_UPDATE,
                    values:{
                        id:mis.id,
                        missionStatus:2,
                    },
                    successCallback:()=>{
                        postFetch({
                            url:MNG_ADD_BULLETIN_URL,
                            values:{
                                stuId:teacherStuId,
                                title:"您的学生"+mis.stuName+"提交了关于"+mis.category+"的完成任务申请",
                                content:"您的学生"+mis.stuName+"提交了关于"+mis.category+"的完成任务申请，请到相应的模块同意或拒绝请求"
                            }
                        })
                        alertBox({text:"申请成功",severity:"success"})
                        setRefresh((p)=>!p)
                    }
                })
            }
        })
    }

    const handleDelay=(mis)=>{//处理延期申请
        if(mis.missionStatus===2){
            alertBox({text:"该任务已提交完成申请",severity:"error"})
            return
        }
        if(mis.missionStatus===3){
            alertBox({text:"该任务已完成",severity:"error"})
            return
        }
        if(mis.missionStatus===4){
            alertBox({text:"该任务已经提交延期申请，请耐心等待",severity:"error"})
            return
        }
        setMissionDetail(mis)
        setDelayOpen(true)
    }

    const handleGroupDone=()=>{
        if(groupList.length===0){//未选中任务
            alertBox({text:"未选中任何任务，无法批量提交",severity:"error"})
            return
        }
        let groupList0=groupList.filter((list)=>{
            return list.missionStatus!==1
        })
        if(groupList0.length!==0){
            alertBox({text:"选中的任务不可提交，请检查任务状态",severity:"error"})
            return
        }
        const cor=confirmModal({
            title: "确定要提交选中任务的完成申请吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_GROUPDONE,
                    values:{
                        groupList
                    },
                    successCallback:()=>{
                        postFetch({
                            url:MNG_ADD_BULLETIN_URL,
                            values:{
                                stuId:teacherStuId,
                                title:"您的学生"+groupList[0].stuName+"批量提交了任务完成申请",
                                content:"您的学生"+groupList[0].stuName+"批量提交了任务完成申请，请到相应模块处理"
                            }
                        })
                        alertBox({text:"批量提交成功",severity:"success"})
                        window.location.reload()
                    }
                })
            }
        })
    }

    const handleGroupDelay=()=>{
        if(groupList.length===0){//没有选中
            alertBox({text:"未选中任何任务，无法批量延期",severity:"error"})
            return
        }
        let groupList0=groupList.filter((list)=>{
            return list.missionStatus!==1
        })
        if(groupList0.length!==0){//只有任务状态为未完成的才能延期，其余的不行
            alertBox({text:"选中的任务不可延期，请检查任务状态",severity:"error"})
            return
        }
        setGroupDelay(groupList)
        setGroupDelayOpen(true)
    }

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        {!selfPermission && userDetail.name+"的"}培养过程
                    </Typography>
                    {mngPermission && (
                        <Button
                            color="primary"
                            size="small"
                            variant="outlined"
                            href={`/app/studyPlan/mngStuConfig/${userDetail.stuId}`}
                        >重新配置该学生的培养过程</Button>
                    )}
                    {selfPermission ?(
                        <Box >
                            <Button
                                color="primary"
                                size="small"
                                variant="outlined"
                                onClick={()=>{
                                    handleGroupDone()
                                }}>一键提交完成申请</Button>
                            <Button
                                color="primary"
                                size="small"
                                variant="outlined"
                                onClick={()=>{
                                    handleGroupDelay()
                                }}>一键申请延期</Button>
                        </Box>
                    ): (
                        <Button
                            color="primary"
                            size="small"
                            variant="outlined"
                            onClick={()=>{
                                history.goBack()
                            }}>返回</Button>
                    )}
                </Box>

                <Divider/>
                <Box minWidth={800}>
                    {mentorPermission && (
                        <Paper>
                            <Typography color="textPrimary" size="middle">
                                姓名：{userDetail.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学号：{userDetail.stuId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;导师：{userDetail.mentor}<br/>
                                论文：{userDetail.papers}<br/>
                                项目：{userDetail.projects}<br/>
                                服务：{userDetail.services}<br/>
                                专利：{userDetail.patents}
                            </Typography>
                        </Paper>
                    )}
                    <Divider/>
                    <Table>
                        <TableHead>
                            <TableRow>
                                {selfPermission && (<TableCell></TableCell>)}
                                <TableCell>任务</TableCell>
                                <TableCell align="center">任务类别</TableCell>
                                <TableCell align="center">预计完成时间</TableCell>
                                <TableCell>任务状态</TableCell>
                                <TableCell align="center">是否已延期</TableCell>
                                {(selfPermission || mngPermission) && (
                                    <TableCell align="center">操作</TableCell>
                                )}
                            </TableRow>
                        </TableHead>

                        <Divider/>

                        <TableBody>
                            {mission.map((mis,index)=>(
                                <TableRow>
                                    {selfPermission && (//多选框，可以进行批量提交
                                        <TableCell>
                                            <Checkbox defaultChecked={false} color="primary"
                                                      onChange={(event, checked)=>{
                                                          if(checked===true){
                                                              groupList.push(mis)
                                                          }
                                                          if(checked===false){
                                                              groupList=groupList.filter((list)=>{
                                                                  return list.id!==mis.id
                                                              })
                                                          }
                                                      }}/>
                                        </TableCell>
                                    )}
                                    <TableCell>{mis.category}</TableCell>
                                    <TableCell align="center">{mis.tinyCategory}</TableCell>
                                    <TableCell align="center">{mis.year}年{mis.month}月</TableCell>
                                    <TableCell>{STATUS[mis.missionStatus]}</TableCell>
                                    <TableCell align="center">{TYPES[mis.isDelay]}</TableCell>
                                    {(selfPermission || mngPermission) && (
                                        <TableCell align="center">
                                            {selfPermission && (
                                                <>
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        handleDelay(mis)
                                                    }}>申请延期</Button>
                                                <Button
                                                color="primary"
                                                size="small"
                                                variant="text"
                                                onClick={()=>{handleDone(mis,index)}}>提交完成申请</Button>
                                                </>
                                            )}
                                            {mngPermission && (
                                                <>
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        setMissionDetail(mis)
                                                        setEditOpen(true)
                                                    }}>修改</Button>
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        onClick={()=>{
                                                            handleDelete(mis.id)
                                                        }}>删除</Button>
                                                </>

                                            )}
                                        </TableCell>
                                    )}
                                </TableRow>))}
                        </TableBody>
                    </Table>
                </Box>
            </Card>

            <ProcessDelayForm open={delayOpen}
                              onClose={()=>{
                                  setDelayOpen(false)
                                  setRefresh((p)=>!p)
                              }}
                              teacherStuId={teacherStuId}
                              missionDetail={missionDetail}/>
            <ProcessGroupDelayForm open={groupDelayOpen}
                                  onClose={()=>{
                                      window.location.reload()
                                  }}
                                  teacherStuId={teacherStuId}
                                  groupList={groupDelay}/>
            <ProcessEditForm open={editOpen}
                             onClose={()=>{
                                 setEditOpen(false)
                                 setRefresh((p)=>!p)
                             }}
                             missionDetail={missionDetail}/>
        </div>
    )
}
