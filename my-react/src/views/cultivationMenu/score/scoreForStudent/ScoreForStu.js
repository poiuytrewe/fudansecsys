import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {postFetch} from "../../../../base";
import {MNG_GET_ALL_USER, MNG_RETURN_SCORE, MNG_UPDATE_SCORE} from "../../../../settings";
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
import ScoreEditForm from "../scoreForm/ScoreEditForm";
import {useHistory} from "react-router-dom";
import ScoreStudentComment from "../scoreForm/ScoreStudentComment";
import {Rating} from "@material-ui/lab";
import confirmModal from "../../../../components/ConfirmModal";
import alertBox from "../../../../components/AlertBox";
import ScoreAddForm from "../scoreForm/ScoreAddForm";
import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";
import TeaCommentForm from "../scoreForm/TeaCommentForm";
import TeaCommentForTeaForm from "../scoreForm/TeaCommentForTeaForm";

export default function ScoreForStu(props){
    const {userDetail}=props
    const classes=ContentStyleForPage()
    const {userInfo}=useContext(UserContext)
    const [refresh,setRefresh]=useState(false)
    const [scoreDetail,setScoreDetail]=useState({})
    const [scores,setScores]=useState([])
    const [openEdit,setOpenEdit]=useState(false)
    const [openAdd,setOpenAdd]=useState(false)
    const [commentOpen, setCommentOpen]=useState(false)
    const [teacherStuId,setTeacherStuId]=useState("")
    const [teaCommentOpen,setTeaCommentOpen]=useState(false)
    const [teaCommentOpenForTea, setTeaCommentOpenForTea]=useState(false)
    const history=useHistory()

    const mentorPermission= userInfo.name==userDetail.mentor

    const selfPermission= userInfo.stuId==userDetail.stuId //登陆者是否是本人

    const handleScoreChanged=({score,newValue})=>{ //修改评分
        if(newValue===0){
            alertBox({text:"评分至少为1分",severity:"success"})
            return
        }
        const cor=confirmModal({
            title:"确定要修改评分吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_UPDATE_SCORE,
                    values:{
                        id:score.id,
                        score:parseInt(newValue)
                    },
                    successCallback:()=>{
                        alertBox({ text: "评分成功", severity: "success" });
                        setRefresh((p)=>!p)
                    }
                })
            }
        })
    }

    const queryTeacher=()=>{//查询老师id
        fetch(`${MNG_GET_ALL_USER}?name=${userDetail?.mentor}&limit=1`)
            .then((r)=>r.json())
            .catch((error)=>console.error(error))
            .then((r)=>{
                setTeacherStuId(r?.data[0]?.stuId || "")
            })
    }

    useEffect(()=>{
        if(userDetail?.id){ //获取这个学生的分数信息
            fetch(`${MNG_RETURN_SCORE}?stuId=${userDetail?.stuId}`,{})
                .then((res)=>res.json())
                .catch((error)=>console.error("Error:",error))
                .then((response)=>{
                    setScores(response?.data || [])
                })
            queryTeacher()
        }
    },[refresh])

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Typography color="textPrimary" size="small" component="h2">
                    {mentorPermission && `${userDetail.name}的`}季度评分
                </Typography>

                {selfPermission && ( //如果是自己的话，可以新增一个季度自评
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{
                            setOpenAdd(true)
                        }}
                    >添加季度自评</Button>
                )}

                {(mentorPermission || userInfo.stuId==="07175") && (
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

                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>年份</TableCell>
                                <TableCell align="center">季度</TableCell>
                                <TableCell align="center">评分</TableCell>
                                <TableCell align="center">自评</TableCell>
                                {(mentorPermission || selfPermission) && (//登录的用户是选择用户的导师或者是用户自己，开放这个功能
                                    <TableCell align="center">操作</TableCell>
                                )}
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {scores.map((score)=>(
                                <TableRow>
                                    <TableCell>{score.year}</TableCell>
                                    <TableCell align="center">{score.season}</TableCell>
                                    <TableCell align="center">
                                        {mentorPermission? <Rating defaultValue={score.score} value={score.score} onChange={(e,newValue)=>{
                                                e.preventDefault()//阻止默认行为
                                                handleScoreChanged({score,newValue})}} />
                                            : <Rating disabled defaultValue={score.score} value={score.score}/>}
                                    </TableCell>
                                    <TableCell align="center">
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                setScoreDetail(score)
                                                setCommentOpen(true)
                                            }}
                                        >点击查看学生自评</Button>
                                    </TableCell>
                                    {(mentorPermission || selfPermission) && ( //用户的导师和用户自己都可以进行修改，不同的是导师只能修改评分，用户只能修改自评
                                        <TableCell align="center">
                                            {selfPermission && (
                                                <>
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        onClick={()=>{
                                                            setOpenEdit(true)
                                                            setScoreDetail(score)
                                                        }}
                                                    >修改自评
                                                    </Button>

                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        onClick={()=>{
                                                            setTeaCommentOpen(true)
                                                            setScoreDetail(score)
                                                        }}>查看导师评价
                                                    </Button>
                                                </>
                                            )}

                                            {mentorPermission && ( //只有导师才有资格删除评分
                                                <>
                                                    {score.teaComment ? (
                                                        <Button
                                                            color="primary"
                                                            size="small"
                                                            variant="text"
                                                            onClick={()=>{
                                                                setTeaCommentOpenForTea(true)
                                                                setScoreDetail(score)
                                                            }}
                                                            >修改导师评价</Button>
                                                    ): (
                                                        <Button
                                                            color="primary"
                                                            size="small"
                                                            variant="text"
                                                            onClick={()=>{
                                                                setTeaCommentOpenForTea(true)
                                                                setScoreDetail(score)
                                                            }}
                                                        >添加导师评价</Button>
                                                    )}
                                                </>
                                            )}
                                        </TableCell>
                                    )}
                                </TableRow>
                            ))}

                        </TableBody>
                    </Table>

            <ScoreEditForm open={openEdit}
                           onClose={()=>{
                               setOpenEdit(false)
                               setRefresh((p)=>!p)
                               setScoreDetail({})
                           }}
                           scoreDetail={scoreDetail}
            />

            <ScoreAddForm open={openAdd}
                          onClose={()=>{
                              setOpenAdd(false)
                              setRefresh((p)=>!p)
                          }}
                          userDetail={userDetail}
                          teacherStuId={teacherStuId} />

            <ScoreStudentComment open={commentOpen}
                                 onClose={()=>{
                                     setCommentOpen(false)
                                     setRefresh((p)=>!p)
                                     setScoreDetail({})
                                 }}
                                 scoreDetail={scoreDetail}
            />

            <TeaCommentForm open={teaCommentOpen}
                            onClose={()=>{
                                setTeaCommentOpen(false)
                                setRefresh((p)=>!p)
                                setScoreDetail({})
                            }}
                            scoreDetail={scoreDetail}/>

            <TeaCommentForTeaForm open={teaCommentOpenForTea}
                                  onClose={()=>{
                                      setTeaCommentOpenForTea(false)
                                      setRefresh((p)=>!p)
                                      setScoreDetail({})
                                  }}
                                  scoreDetail={scoreDetail}/>
        </div>
    )
}
