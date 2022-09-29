import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../layouts/Context";
import {
    Button,
    Divider,
    Fab, Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
    Tooltip
} from "@material-ui/core";
import {useHistory} from "react-router-dom";
import {
    MNG_ADD_BULLETIN_URL,
    MNG_BASE_PROCESS_INIT,
    MNG_GET_ALL_USER,
    MNG_GET_PROCESS_LIST, MNG_PROCESS_DELETE, MNG_PROCESS_UPDATE
} from "../../../settings";
import {postFetch} from "../../../base";
import alertBox from "../../../components/AlertBox";
import AddIcon from "@mui/icons-material/Add";
import ProcessAddForm from "./processForm/ProcessAddForm";
import ProcessDetailForm from "./processForm/ProcessDetailForm";
import confirmModal from "../../../components/ConfirmModal";
import ProcessPartAddForm from "./processForm/ProcessPartAddForm";
import ProcessDelayForm from "./processForm/ProcessDelayForm";
import ConfirmForm from "./processForm/ConfirmForm";

const TYPES=[
    {
        value:1,
        name:"学术科研",
    },
    {
        value:2,
        name:"专业技术",
    },
    {
        value:3,
        name:"思政服务"
    }
]

const DELAY={
    0:"否",
    1:"是",
}

const MISSIONSTATUS={
    0:"已延期",
    1:"未完成",
    2:"已提交",
    3:"已完成"
}
export default function ProcessBaseForStu(props){
    const {userDetail}=props
    const classes=ContentStyleForPage()
    const {userInfo}=useContext(UserContext)
    const [type,setType]=useState(1)
    const [refresh,setRefresh]=useState(false)
    const [baseMission,setBaseMission]=useState([])
    const [teacherStuId,setTeacherStuId]=useState("")
    const [addOpen,setAddOpen]=useState(false)
    const [detailOpen,setDetailOpen]=useState(false)
    const [missionDetail,setMissionDetail]=useState({})
    const [partAddOpen,setPartAddOpen]=useState(false)
    const [delayOpen,setDelayOpen]=useState(false)
    const [index,setIndex]=useState(0)
    const [confirmDetail,setConfirmDetail]=useState({})
    const [confirmForm,setConfirmForm]=useState(false)
    const history=useHistory()
    const category=2

    const mentorPermission= userDetail.mentor===userInfo.name
    const selfPermission= userDetail.stuId===userInfo.stuId

    const queryTeacher=()=>{//查询老师stuId
        fetch(`${MNG_GET_ALL_USER}?name=${userDetail?.mentor}&limit=1`)
            .then((r)=>r.json())
            .catch((error)=>console.error(error))
            .then((r)=>{
                setTeacherStuId(r?.data[0]?.stuId || "")
            })
    }

    const getBaseMission=()=>{
        fetch(`${MNG_GET_PROCESS_LIST}?stuId=${userDetail.stuId}&category=${2}&tinyCategory=${type}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setBaseMission(res?.data || [])
            })
    }

    const handleUpload=(mission,index)=>{//处理提交完成申请
        if(mission.missionStatus!==1){
            alertBox({text:"提交错误，这个任务不是可提交状态",severity:"error"})
            return
        }
        if(baseMission[index-1] && baseMission[index-1].missionStatus && baseMission[index-1].missionStatus!==3){//前一个任务没有到完成阶段，不能提交
            if(baseMission[index-1].year!==mission.year || baseMission[index-1].month!==mission.month){
                alertBox({text:"提交错误，上一个任务还没有完成",severity:"error"})
                return
            }
        }
        const cor=confirmModal({
            title:"确定要提交该任务的完成申请吗",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_PROCESS_UPDATE,
                    values:{
                        id:mission.id,
                        missionStatus:2
                    },
                    successCallback:()=>{
                        alertBox({text:"提交成功，请等待导师的回复",severity:"success"})
                        postFetch({//向导师发送邮件
                            url:MNG_ADD_BULLETIN_URL,
                            values:{
                                stuId:teacherStuId,
                                title:"您的学生提交了基础任务的完成申请",
                                content:"您的学生["+userDetail.name+"]提交了["+TYPES.find((t)=>t.value===type).name+"]板块的基础任务的完成申请，请到培养过程模块进行处理"
                            }
                        })
                        setRefresh((p)=>!p)
                    }
                })
            }
        })
    }

    const handleDelete=(missionId)=>{
        const cor=confirmModal({
            title:"确定要删除这一条任务吗",
            handleCorfirm:()=>{
                cor.close()
                fetch(`${MNG_PROCESS_DELETE}?id=${missionId}`,{method:"POST"})
                    .then(()=>{
                        alertBox({text:"删除成功",severity:"success"})
                        setRefresh((p)=>!p)
                    })
            }
        })
    }

    useEffect(()=>{
        queryTeacher()
        fetch(`${MNG_GET_PROCESS_LIST}?stuId=${userDetail.stuId}&category=${2}&tinyCategory=${type}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setBaseMission(res?.data || [])
                fetch(`${MNG_GET_PROCESS_LIST}?stuId=${userDetail.stuId}&category=${2}&tinyCategory=${type}`)
                    .then((res)=>res.json())
                    .catch((error)=>console.log(error))
                    .then((res)=>{
                        setBaseMission(res?.data || [])
                        let confMission=res?.data.find((m)=>m.missionStatus===2)
                        if(confMission){
                            setConfirmDetail(confMission)
                            setConfirmForm(true)
                        }
                    })
            })
    },[])

    useEffect(()=>{
        getBaseMission()
    },[refresh])

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <TextField
                    select
                    label="板块类型"
                    size="small"
                    value={type}
                    onChange={(e) => {
                        setType(parseInt(e.target.value))
                        setRefresh((p)=>!p)
                    }}
                    SelectProps={{
                        native: true,
                    }}
                    variant="outlined"
                >{TYPES.map((t)=>(
                    <option value={t.value}>
                        {t.name}
                    </option>
                    )
                )}
                </TextField>

                {((selfPermission || mentorPermission) && baseMission.length===0) && (
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{//点击初始化
                            postFetch({
                                url:MNG_BASE_PROCESS_INIT,
                                values:{
                                    stuId:userDetail.stuId,
                                    name:userDetail.name,
                                },
                                successCallback:()=>{
                                    alertBox({text:"初始化成功",severity:"success"})
                                    setRefresh((p)=>!p)
                                }
                            })
                        }}
                    >初始化任务列表</Button>
                )}

                {!selfPermission && (
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{
                            history.goBack()
                        }}
                        >返回</Button>
                )}
            </div>

            <Divider/>
            {mentorPermission && (
                <Tooltip title="点击增加新任务">
                    <Fab color="primary" aria-label="add" onClick={()=>{
                        setAddOpen(true)
                    }} >
                        <AddIcon/>
                    </Fab>
                </Tooltip>
            )}

            <Paper>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">任务</TableCell>
                            <TableCell align="center">任务详细内容</TableCell>
                            <TableCell>任务状态</TableCell>
                            <TableCell align="center">是否已延期</TableCell>
                            <TableCell>预计完成时间</TableCell>
                            {(mentorPermission || selfPermission) && (
                                <TableCell align="center">操作</TableCell>
                            )}
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {baseMission.map((mission,index)=>(
                            <TableRow>
                                <TableCell align="center">{mission.missionName}</TableCell>
                                <TableCell align="center">
                                    {mission.missionDetail? (
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                setDetailOpen(true)
                                                setMissionDetail(mission)
                                            }}
                                        >查看详细任务内容</Button>
                                    ):(
                                        mentorPermission ?
                                            <Tooltip title="点击添加任务内容">
                                                <Fab size="small" color="primary" aria-label="add" onClick={()=>{
                                                    setPartAddOpen(true)
                                                    setMissionDetail(mission)
                                                }}>
                                                    <AddIcon/>
                                                </Fab>
                                            </Tooltip>
                                            :"等待导师添加"
                                    )}
                                </TableCell>
                                <TableCell>
                                    {mission.missionStatus && MISSIONSTATUS[mission.missionStatus]}
                                </TableCell>
                                <TableCell align="center">
                                    {DELAY[mission.isDelay]}
                                </TableCell>
                                <TableCell>{mission.year && `${mission.year}年${mission.month}月`}</TableCell>
                                {mentorPermission && (
                                    <TableCell align="center">
                                        {mission.missionDetail &&
                                            (<>
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        onClick={() => {
                                                            if(mission.missionStatus!==0 && mission.missionStatus!==1){
                                                                alertBox({text:"任务状态无法延期",severity:"error"})
                                                                return
                                                            }
                                                            setDelayOpen(true)
                                                            setMissionDetail(mission)
                                                            setIndex(index)
                                                        }}
                                                    >延期</Button>
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        onClick={()=>{
                                                            handleDelete(mission.id)
                                                        }}
                                                    >删除</Button></>
                                            )
                                        }
                                    </TableCell>
                                )}
                                {selfPermission && (
                                    <TableCell align="center">
                                        {mission.missionDetail && (
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="text"
                                                onClick={()=>{
                                                    handleUpload(mission,index)
                                                }}
                                            >提交完成申请</Button>
                                        )}
                                    </TableCell>
                                )}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>

            <ProcessDetailForm open={detailOpen}
                               onClose={()=>{
                                   setDetailOpen(false)
                                   setRefresh((p)=>!p)
                               }}
                               missionDetail={missionDetail} />

            <ProcessAddForm open={addOpen}
                            onClose={()=>{
                                setAddOpen(false)
                                setRefresh((p)=>!p)
                            }}
                            userDetail={userDetail}
                            category={category}
                            tinyCategory={type} />

            <ProcessPartAddForm open={partAddOpen}
                                onClose={()=>{
                                    setPartAddOpen(false)
                                    setRefresh((p)=>!p)
                                }}
                                missionDetail={missionDetail}
            />

            <ProcessDelayForm open={delayOpen}
                               onClose={()=>{
                                   setDelayOpen(false)
                                   setRefresh((p)=>!p)
                               }}
                               mission={baseMission}
                               missionDetail={missionDetail}
                              index={index}
            />
            <ConfirmForm open={confirmForm} //教师处理学生提交请求的
                         onClose={()=>{
                             setConfirmForm(false)
                             setRefresh((p)=>!p)
                         }}
                         detail={confirmDetail}/>
        </div>
    )
}
