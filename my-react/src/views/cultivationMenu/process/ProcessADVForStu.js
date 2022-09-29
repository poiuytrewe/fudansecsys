import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import {
    Box, Button,
    Divider, Fab, makeStyles,
    Paper, Table, TableBody, TableCell, TableHead, TableRow,
    TextField, Tooltip, Typography
} from "@material-ui/core";
import {
    MNG_ADD_BULLETIN_URL,
    MNG_GET_ALL_USER,
    MNG_GET_PROCESS_LIST,
    MNG_PROCESS_DELETE, MNG_PROCESS_UPDATE,
    MNG_UPDATE_USER_URL
} from "../../../settings";
import React, {useContext, useEffect, useState} from "react";
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import alertBox from "../../../components/AlertBox";
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";
import {UserContext} from "../../../layouts/Context";
import AddIcon from "@mui/icons-material/Add";
import ProcessDetailForm from "./processForm/ProcessDetailForm";
import ProcessAddForm from "./processForm/ProcessAddForm";
import ProcessDelayForm from "./processForm/ProcessDelayForm";
import ConfirmForm from "./processForm/ConfirmForm";

const TEXT= {
    1: "完成以下两类标准之一：  1.作为主要参与者，协助其他学生发表1区论文，包括NDSS：" +
        "  （1）.每篇1区论文仅考虑1位主要参与者；" +
        "  （2）.贡献度需达到一作贡献度的30%；" +
        "  （3）.参考标准：" +
        "在系统实现中承担主要作用或者对文章的形成中起到主要作用。" +
        "   2.以第一作者身份完成科研论文：" +
        "  （1）.指定的Trans至少达到小修（Minor Revision）；" +
        "  （2）.会议论文投稿多次未被录用，研究价值需得到导师认可；" +
        "  （3）.论文列表：CCF二区+实验室认可的若干三区会议（AsiaCCS等）（需针对讨论）。",
    2: "完成以下两类标准之一：" +
        "   1.完成具有较大技术含量的、完整的、可上线的项目：" +
        "  （1）.完成项目交付的文稿、代码等必要准备。" +
        "   2.发现现有系统重大安全漏洞或者新型的攻击方法，形成工具和较大的影响力。",
    3: "完成以下标准之二：" +
        "   1.负责一项实验室资源管理工作，两年内未发生重大事故；" +
        "   2.策划和负责一次实验室团建活动，活动参与人数占实验室学生总人数60%以上；" +
        "   3.负责某一年度的实验室本科生招生宣传工作，挖掘5名以上具有潜力的本科生，牵头实验室老师与对应学生面谈；负责当年度的夏令营工作；" +
        "   4.负责某一年度的实验室对外宣传工作，包括网站新闻撰写、公众号维护、宣传材料制作等，且实现阅读、传播度等考核指标（每年由负责老师另行公布）；" +
        "   5.负责某一年度的实验室信息系统建设和维护，实现不少于两个模块的更新和改版；" +
        "   6.积极参与实验室党建工作，在学工部/研工部认可的思政期刊上以第一作者发表与实验室工作相关的思政论文1篇。"
}

const ROLE={
    1:"学术科研",
    2:"专业技术",
    3:"思政服务"
}

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

const useStyles=makeStyles((theme)=>({
    root: {
        // flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(2),
    },
    header: {
        alignItems: "center",
        justifyContent: "space-between",
        paddingBottom: theme.spacing(2),
        "& .MuiTextField-root": {
            width: "150px",
        },
        "& .MuiButton-root": {
            width: "150px",
            height: "40px",
            float: "left"
        },
    },
    Pagination: {
        padding:theme.spacing(2),
        '& .MuiPagination-ul':{
            justifyContent: 'center',
        }
    }
}))

export default function ProcessADVForStu(props){
    const {userDetail}= props
    const classes=useStyles()
    const {userInfo}=useContext(UserContext)
    const [teacherStuId,setTeacherStuId]=useState("")
    const [ADVMission,setADVMission]=useState([])
    const [refresh,setRefresh]=useState(false)
    const [index,setIndex]=useState(0)
    const [text,setText]=useState(0)
    const [delayOpen,setDelayOpen]=useState(false)
    const [addOpen,setAddOpen]=useState(false)
    const [detailOpen,setDetailOpen]=useState(false)
    const [missionDetail,setMissionDetail]=useState([])
    const [confirmDetail,setConfirmDetail]=useState({})
    const [confirmForm,setConfirmForm]=useState(false)
    const category=3
    const tinyCategory=null

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

    const getADVMission=()=>{
        fetch(`${MNG_GET_PROCESS_LIST}?stuId=${userDetail.stuId}&category=${3}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setADVMission(res?.data || [])
            })
    }

    const handleSubmit=()=>{//处理提交
        if(text===0){
            alertBox({text:"请选择一个之后再提交",severity:"error"})
            return
        }
        const cor=confirmModal({
            title:"确定要选择这一个板块进行提交吗？提交后不可修改，请与导师商量之后再做决定",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_UPDATE_USER_URL,
                    values:{
                        id:userDetail.id,
                        studyType:text,
                    },
                    successCallback:()=>{
                        alertBox({text:"选择成功",severity:"success"})
                        window.location.reload()
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

    const handleUpload=(mission,index)=>{//处理提交完成申请
        if(mission.missionStatus!==1){
            alertBox({text:"提交错误，这个任务不是可提交状态",severity:"error"})
            return
        }
        if(ADVMission[index-1] && ADVMission[index-1].missionStatus && ADVMission[index-1].missionStatus!==3){//前一个任务没有到完成阶段，不能提交
            if(ADVMission[index-1].year!==mission.year || ADVMission[index-1].month!==mission.month){
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
                                title:"您的学生提交了进阶任务的完成申请",
                                content:"您的学生["+userDetail.name+"]提交了进阶任务的完成申请，请到培养过程模块进行处理"
                            }
                        })
                        setRefresh((p)=>!p)
                    }
                })
            }
        })
    }

    useEffect(()=>{
        queryTeacher()
        fetch(`${MNG_GET_PROCESS_LIST}?stuId=${userDetail.stuId}&category=${3}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setADVMission(res?.data || [])
                let confMission=res?.data.find((m)=>m.missionStatus===2)
                if(confMission){
                    setConfirmDetail(confMission)
                    setConfirmForm(true)
                }
            })

    },[])

    useEffect(()=>{
        getADVMission()
    },[refresh])

    return(
        <div className={classes.root}>
            <div className={classes.header}>
                {userDetail.studyType === 0 ?
                    (mentorPermission || selfPermission) &&(
                    <>
                        <div style={{display: "flex"}}>
                            <FormControl>
                                <FormLabel id="demo-radio-buttons-group-label">请从以下板块中选择一个进行进阶任务</FormLabel>
                                <RadioGroup
                                    row
                                    aria-labelledby="demo-radio-buttons-group-label"
                                    defaultValue="female"
                                    name="radio-buttons-group"
                                    onChange={(event, value) => {
                                        setText(parseInt(value))
                                    }}
                                >
                                    <FormControlLabel value={1} control={<Radio/>} label="学术科研"/>
                                    <FormControlLabel value={2} control={<Radio/>} label="专业技术"/>
                                    <FormControlLabel value={3} control={<Radio/>} label="思政服务"/>
                                </RadioGroup>
                            </FormControl>
                            <Button
                                color="primary"
                                size="small"
                                variant="outlined"
                                onClick={() => {
                                    handleSubmit()
                                }}
                            >点击提交</Button>
                        </div>
                        <Typography color="textPrimary" size="small">
                            {TEXT[text]}
                        </Typography>
                    </>
                    ):(
                        <>
                            <Typography color="textPrimary" size="small">
                                选择的进阶任务板块：{ROLE[userDetail.studyType]}
                            </Typography>
                            <Typography color="textPrimary" size="small">
                                任务细节：{TEXT[userDetail.studyType]}
                            </Typography>
                        </>
                )}
            </div>

            <Divider/>
            {(mentorPermission && userDetail.studyType!==0) && (
                <Tooltip title="点击增加新任务">
                    <Fab color="primary" aria-label="add" onClick={()=>{
                        setAddOpen(true)
                    }} >
                        <AddIcon/>
                    </Fab>
                </Tooltip>
            )}

            {userDetail.studyType!==0 && (
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
                            {ADVMission.map((mission,index)=>(
                                <TableRow>
                                    <TableCell align="center">{mission.missionName}</TableCell>
                                    <TableCell align="center">
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                setDetailOpen(true)
                                                setMissionDetail(mission)
                                            }}
                                        >查看详细任务内容</Button>

                                    </TableCell>
                                    <TableCell>{MISSIONSTATUS[mission.missionStatus]}</TableCell>
                                    <TableCell align="center">{DELAY[mission.isDelay]}</TableCell>
                                    <TableCell>{mission.year}年{mission.month}月</TableCell>
                                    {mentorPermission && (
                                        <TableCell align="center">
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="text"
                                                onClick={()=>{
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
                                            >删除</Button>
                                        </TableCell>
                                    )}
                                    {selfPermission && (
                                        <TableCell align="center">
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="text"
                                                onClick={()=>{
                                                    handleUpload(mission,index)
                                                }}
                                            >提交完成申请</Button>
                                        </TableCell>
                                    )}
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Paper>
            )}
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
                            tinyCategory={tinyCategory}
            />

            <ProcessDelayForm open={delayOpen}
                              onClose={()=>{
                                  setDelayOpen(false)
                                  setRefresh((p)=>!p)
                              }}
                              mission={ADVMission}
                              missionDetail={missionDetail} index={index}/>

            <ConfirmForm open={confirmForm} //教师处理学生提交请求的
                         onClose={()=>{
                             setConfirmForm(false)
                             setRefresh((p)=>!p)
                         }}
                         detail={confirmDetail}/>
        </div>
     )
}
