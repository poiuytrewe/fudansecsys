import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {
    MNG_PROCESS_GET_COUNT,
    MNG_PROCESS_GET_STUDENT,
    MNG_PROCESS_GET_STUMISSIONSTATUS, PROCESS_GET_MASTERFROMMENTOR_TOTAL
} from "../../../../settings";
import {
    Box,
    Button,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@material-ui/core";
import {Pagination} from "@material-ui/lab";
import Badge from '@mui/material/Badge';
import ProcessForTeachApprove from "../processForm/ProcessForTeachApprove";
import ProcessForTeachDelay from "../processForm/ProcessForTeachDelay";
import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";

const Role={
    0: "硕士",
    10: "博士"
}

export default function ProcessForTea(){
    const classes=ContentStyleForPage()
    const [page, setPage]=useState(1)
    const [pageNo, setPageNo]=useState(0)
    const {userInfo}=useContext(UserContext)
    const [users, setUsers]=useState([])
    const [missionStatus,setMissionStatus]=useState([])
    const [allApprove,setAllApprove]=useState([])
    const [open,setOpen]=useState(false)
    const [allDelay,setAllDelay]=useState([])
    const [delayOpen,setDelayOpen]=useState(false)
    const [count1,setCount1]=useState(0)
    const [count2,setCount2]=useState(0)

    const getAllStudentMissionStatus=()=>{
        fetch(`${MNG_PROCESS_GET_STUMISSIONSTATUS}?limit=${10}&offset=${(page-1)*10}&name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setMissionStatus(res?.data || [])
            })
    }
    const getCount=()=>{//获取状态异常的学生人数
        fetch(`${MNG_PROCESS_GET_COUNT}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setCount1(res?.data[0])
                setCount2(res?.data[1])
            })
    }

    const getAllStudent=()=>{
        fetch(`${MNG_PROCESS_GET_STUDENT}?limit=${10}&offset=${(page-1)*10}&name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setUsers(res?.data || [])
            })

    }

    const getTotal=()=>{
        fetch(`${PROCESS_GET_MASTERFROMMENTOR_TOTAL}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setPageNo(Math.ceil(res.data/10) || 0)
            })
    }
    useEffect(()=>{
        getTotal()
        getCount()//获取状态异常的学生人数
    },[])

    useEffect(()=>{
        getAllStudentMissionStatus()
        getAllStudent()
    },[page, pageNo])

    return (
        <div className={classes.root}>
                <div className={classes.header}>

                    <Typography color="textPrimary" size="small">
                        学生培养过程管理
                    </Typography>

                    <Box>
                        <Typography color="textPrimary" size="small">
                            严重逾期人数：{count1}<br/>
                            逾期人数：{count2+count1}
                        </Typography>
                    </Box>

                </div>

                <Divider/>


                    {missionStatus.length!==0 && (
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>姓名</TableCell>
                                    <TableCell align="center">学/工号</TableCell>
                                    <TableCell align="center">类型</TableCell>
                                    <TableCell align="center">任务逾期数量</TableCell>
                                    <TableCell align="center">操作</TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {users.map((user,index)=>{
                                    return (
                                        <TableRow hover key={user.id}>
                                            <TableCell>{user.name}</TableCell>
                                            <TableCell align="center">{user.stuId}</TableCell>
                                            <TableCell align="center">{Role[user.type]}</TableCell>
                                            <TableCell align="center">
                                                {missionStatus[index]<1 ? (
                                                    <Badge color="success" badgeContent={0} showZero/>
                                                ):(
                                                    missionStatus[index]>1? (
                                                        <Badge color="error" badgeContent={missionStatus[index]}/>
                                                    ):(
                                                        <Badge color="warning" badgeContent={missionStatus[index]}/>
                                                    )
                                                )}
                                            </TableCell>
                                            <TableCell align="center">
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    href={`/app/studyPlan/stuProcess/${user.stuId}`}
                                                >查看学生培养过程</Button>
                                            </TableCell>
                                        </TableRow>
                                    )
                                })}
                            </TableBody>
                        </Table>
                    )}
                    {pageNo>1 &&(
                        <Pagination
                            className={classes.Pagination}
                            count={pageNo}
                            color="primary"
                            onChange={(e,i)=>{
                                setPage(i)
                            }}/>
                    )}

            <ProcessForTeachApprove open={open}
                                    onClose={()=>{
                                        setOpen(false)
                                        window.location.reload()
                                    }}
                                    approveList={allApprove}/>

            <ProcessForTeachDelay open={delayOpen}
                                  onClose={()=>{
                                      setDelayOpen(false)
                                      window.location.reload()
                                  }}
                                  delayList={allDelay}/>
        </div>
    )
}
