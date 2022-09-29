import ContentStyle from "../../../../components/Style/ContentStyle";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {Box, Card, Divider, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@material-ui/core";
import {getAllStudent} from "../../../../service/userService";
import {Pagination} from "@material-ui/lab";

export default function RecorderGroupForStu(){
    const classes=ContentStyle()
    const {userInfo}=useContext(UserContext)
    const groupId=userInfo.groupId
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [groupStudent,setGroupStudent]=useState([])

    useEffect(()=>{
        getAllStudent({page,groupId}).then((res)=>{
            setGroupStudent(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total /10) || 0)
        })
    },[page, pageNo])

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        我所在的组：{userInfo.groupId}
                    </Typography>

                </Box>

                <Divider/>

                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>组内成员</TableCell>
                            <TableCell align="center">学/工号</TableCell>
                        </TableRow>
                    </TableHead>

                    {userInfo.groupId>0 && (
                        <TableBody>
                            {groupStudent.map((student)=>(
                                <TableRow>
                                    <TableCell>{student.name}</TableCell>
                                    <TableCell align="center">{student.stuId}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    )}
                </Table>

                {(pageNo>1&&userInfo.groupId>0) && (
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
