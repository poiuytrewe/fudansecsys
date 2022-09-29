import ContentStyle from "../../../../components/Style/ContentStyle";
import {
    Box,
    Button,
    Card,
    Divider, Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
} from "@material-ui/core";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {getAllStudent} from "../../../../service/userService";
import {MNG_GET_ALL_USER, MNG_RESET_GROUPID, MNG_UPDATE_USER_URL} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import {postFetch} from "../../../../base";
import {Pagination} from "@material-ui/lab";

const GROUP=[
    {
        id: 0,
        name:"所有组"
    },
    {
        id: 1,
        name:"1组"
    },
    {
        id: 2,
        name:"2组"
    },
    {
        id: 3,
        name:"3组"
    },
    {
        id: 4,
        name:"4组"
    }
]

export default function RecorderGroupForMng(){
    const classes=ContentStyle()
    const {userInfo}=useContext(UserContext)
    const [name,setName]=useState("") //设置搜索的名字
    const [students,setStudents]=useState([]) //从后台获取学生信息
    const [groupId,setGroupId]=useState(0) //groupId
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)

    const talkPermission= userInfo.isTalkMng===1 //是否是讨论班管理员
    const teacherPermission=userInfo.type===20//是否是老师

    const handleReset=()=>{//重置分组
        fetch(MNG_RESET_GROUPID,{
            method:"POST"
        }).then(()=>{
            alertBox({text:"重置成功",severity: "success" })
            setGroupId(0)
            setRefresh((prev)=>!prev)
        })
    }

    const handleGroupChanged=(student,groupId)=>{ //分组变化
        postFetch({
            url:MNG_UPDATE_USER_URL,
            values:{
                id: student.id,
                groupId: groupId
            },
            successCallback:()=>{
                setRefresh((prev)=>!prev)
            }
        })
    }

    useEffect(()=>{
        getAllStudent({page,groupId,name}).then((res)=>{
            setStudents(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total /10) || 0)
        })
    },[refresh, page, pageNo, name, groupId])

    return (
        <div >
            <Card>
                <Box className={classes.header}>

                    {(teacherPermission || talkPermission) && (
                        <TextField
                            select
                            label="组号"
                            size="small"
                            value={groupId}
                            onChange={(e)=>{
                                setPage(1)
                                if(pageNo>0){
                                    setPageNo(1)
                                }
                                setGroupId(parseInt(e.target.value))
                            }}
                            SelectProps={{
                                native:true
                            }}
                            variant="outlined"
                        >
                            {GROUP.map((option)=>(
                                <option key={option.id} value={option.id}>
                                    {option.name}
                                </option>
                            ))}
                        </TextField>
                    )}

                    {(talkPermission || teacherPermission) && (
                        <div>
                            <TextField
                                label="搜索学生"
                                size="small"
                                onChange={(e)=>{
                                    setPage(1)
                                    setName(e.target.value)
                                }}/>

                            <Divider/>

                            {talkPermission && (
                                <Button
                                    color="primary"
                                    size="small"
                                    variant="outlined"
                                    onClick={handleReset}
                                >重置分组</Button>
                            )}
                        </div>
                    )}
                </Box>

                <Divider/>

                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>学生姓名</TableCell>
                            <TableCell align="center">学/工号</TableCell>
                            <TableCell align="center">组号</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {students.map(student=>(
                            <TableRow>
                                <TableCell>{student.name}</TableCell>
                                <TableCell align="center">{student.stuId}</TableCell>
                                <TableCell align="center">
                                    {talkPermission?(//是否有讨论班管理员的权限
                                        <TextField
                                            select
                                            label="所在组"
                                            size="small"
                                            value={student.groupId}
                                            variant="outlined"
                                            onChange={(e)=>{
                                                handleGroupChanged(student, parseInt(e.target.value))
                                            }}
                                            SelectProps={{
                                                native:true
                                            }}
                                        >
                                            {
                                                GROUP.map((group)=>(
                                                    <option key={group.id} value={group.id}>
                                                        {group.id}
                                                    </option>
                                                ))
                                            }
                                        </TextField>
                                    ): (student.groupId)}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>

                </Table>

                {pageNo>1 && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        color="primary"
                        onChange={(e,i)=>setPage(i)}
                    />
                )}

            </Card>
        </div>
    )
}
