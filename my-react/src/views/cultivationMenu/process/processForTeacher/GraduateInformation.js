import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";
import {PROCESS_GET_GRADUATE_INFORMATION,
    PROCESS_GET_MASTERFROMMENTOR_TOTAL
} from "../../../../settings";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {Button, Divider, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@material-ui/core";
import {Pagination} from "@material-ui/lab";

export default function GraduateInformation(){
    const classes=ContentStyleForPage()
    const {userInfo}=useContext(UserContext)
    const [pageNo, setPageNo]=useState(0)
    const [page, setPage]=useState(1)
    const [graduate, setGraduate]=useState([])

    const getTotal=()=>{
        fetch(`${PROCESS_GET_MASTERFROMMENTOR_TOTAL}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setPageNo(Math.ceil(res.data/10) || 0)
            })
    }

    const getGraduateInformation=()=>{
        fetch(`${PROCESS_GET_GRADUATE_INFORMATION}?name=${userInfo.name}&limit=10&offset=${(page-1)*10}`)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setGraduate(res?.data || [])
            })
    }

    useEffect(()=>{
        getTotal()
    },[])

    useEffect(()=>{
        getGraduateInformation()
    },[page])

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Typography color="textPrimary" size="small">
                    毕业相关信息
                </Typography>
            </div>

            <Divider/>

            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>姓名</TableCell>
                        <TableCell align="center">学/工号</TableCell>
                        <TableCell align="center">硕士类型</TableCell>
                        <TableCell align="center">预计毕业时间</TableCell>
                        <TableCell align="center">是否完成所有前置工作</TableCell>
                        <TableCell align="center">目前工作</TableCell>
                        <TableCell align="center">未完成工作列表</TableCell>
                    </TableRow>
                </TableHead>

                <TableBody>
                    {graduate.map((data)=>(
                        <TableRow>
                            <TableCell>{data.stuName}</TableCell>
                            <TableCell align="center">{data.stuId}</TableCell>
                            <TableCell align="center">{data.stuType}</TableCell>
                            <TableCell align="center">{data.graduateTime}</TableCell>
                            <TableCell align="center">{data.isDone}</TableCell>
                            <TableCell align="center">{data.currentJob}</TableCell>
                            <TableCell align="center">
                                <Button
                                    color="primary"
                                    size="small"
                                    variant="text"
                                    onClick={()=>{

                                    }}>查看未完成工作列表</Button>
                            </TableCell>

                        </TableRow>
                    ))}
                </TableBody>
            </Table>

            {pageNo>1 &&(
                <Pagination
                    className={classes.Pagination}
                    count={pageNo}
                    color="primary"
                    onChange={(e,i)=>{
                        setPage(i)
                    }}/>
            )}
        </div>
    )
}
