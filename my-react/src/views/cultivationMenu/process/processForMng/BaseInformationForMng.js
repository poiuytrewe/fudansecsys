import React, {useContext, useEffect, useState} from "react";
import {getAllStudent} from "../../../../service/userService";
import ContentStyle from "../../../../components/Style/ContentStyle";
import {
    Box, Button,
    Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@material-ui/core";
import Pagination from "@material-ui/lab/Pagination";

import Badge from "@mui/material/Badge";
import {UserContext} from "../../../../layouts/Context";

const ROLE= {
    0: "硕士",
    10: "博士"
}

export default function BaseInformationForMng(){
    const classes=ContentStyle()
    const {userInfo}=useContext(UserContext)
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [students,setStudents]=useState([])
    const [missionStatus,setMissionStatus]=useState([])
    const [name,setName]=useState("")

    const mngPermission= userInfo.isEducateMng===1

    // const getAllStudentMissionStatus=()=>{
    //     fetch(`${MNG_PROCESS_GET_MNGSTUMISSOINSTATUS}?limit=${10}&offset=${(page-1)*10}&name=${name}&groupId=${0}`)
    //         .then((res)=>res.json())
    //         .catch((error)=>console.log(error))
    //         .then((res)=>{
    //             setMissionStatus(res?.data || [])
    //         })
    // }

    useEffect(()=>{
        getAllStudent({page,groupId:0,name}).then((res)=>{ //获取所有的学生
            setStudents(res?.data ||[])
            setPageNo(Math.ceil(res?.paging?.total / 10) || 0)
        })
        // getAllStudentMissionStatus()
    },[refresh, page, pageNo, name])

    return(
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        学生培养过程（管理员模式）
                    </Typography>

                        <TextField
                            label="搜索学生"
                            size="small"
                            onChange={(e)=>{
                                setPage(1)
                                setName(e.target.value)}}
                        />

                        <Button
                            color="primary"
                            size="small"
                            variant="outlined"
                            href="/app/studyPlan/mngProcess/category">
                            管理员配置任务
                        </Button>

                </Box>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>姓名</TableCell>
                                <TableCell>学/工号</TableCell>
                                <TableCell>类型</TableCell>
                                <TableCell>导师</TableCell>
                                <TableCell align="center">任务状态</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {students.map((student,index)=>(
                                <TableRow>
                                    <TableCell>{student.name}</TableCell>
                                    <TableCell>{student.stuId}</TableCell>
                                    <TableCell>{ROLE[student.type]}</TableCell>
                                    <TableCell>{student.mentor}</TableCell>
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
                                            href={`/app/updateStudentInfo/${student.stuId}`}
                                        >
                                            编辑学生信息
                                        </Button>
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            href={`/app/studyPlan/stuProcess/${student.stuId}`}
                                            >查看学生培养过程</Button>
                                    </TableCell>
                                </TableRow>
                            ))}

                        </TableBody>

                    </Table>

                    {pageNo > 1 && (
                        <Pagination
                            className={classes.Pagination}
                            count={pageNo}
                            color="primary"
                            onChange={(e, i) => setPage(i)}
                        />
                    )}
                </Box>
            </Card>
        </div>
    )

}
