import React, {useContext, useEffect, useState} from "react";
import {
    Box,
    Button, Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField, Typography
} from "@material-ui/core";
import {UserContext} from "../../../../layouts/Context";
import {
    ADD_RECORDER_URL,
    DELETE_RECORDER_URL,
    MNG_ADD_BULLETIN_URL,
    MNG_GET_ALL_USER,
    MNG_UPDATE_RECORDER
} from "../../../../settings";
import {deleteFetch, postFetch} from "../../../../base";
import {getAllLeader, getAllRecorder} from "../../../../service/userService";
import Pagination from "@material-ui/lab/Pagination";
import {MinusOutlined} from "@ant-design/icons";
import alertBox from "../../../../components/AlertBox";
import ContentStyle from "../../../../components/Style/ContentStyle";
import confirmModal from "../../../../components/ConfirmModal";

export default function Arrangement(){
    const classes=ContentStyle()
    const {userInfo}=useContext(UserContext)
    const [refresh,setRefresh]=useState(false)
    const [page,setPage]=useState(1)
    const [recorder,setRecorder]=useState([])
    const [pageNo,setPageNo]=useState(0)
    const [docs,setDocs]=useState([])

    const hasPermission=userInfo.isTalkMng===1

    const getAllDocs=async ()=>{//获得所有博士骨干学生的信息
        try{
            let response=await fetch(
                `${MNG_GET_ALL_USER}?type=${10}&status=${1}`
            )
            return await response.json()
        }catch (error){
            console.log(error)
        }
    }

    const handleOpen=()=> { //添加一次辅读安排
        postFetch({
            url:ADD_RECORDER_URL,
            successCallback:()=>{ //添加成功之后要发布通知，向各个组的组长发送通知，还应该向所有导读人员发布通知
                getAllRecorder({limit:4}).then((res)=>{//这里是刚刚加上的四条导读记录
                    res?.data.map((re)=>{
                        postFetch({
                            url:MNG_ADD_BULLETIN_URL,
                            values:{
                                stuId:docs.find((doc)=>doc.name==re.recorder1Name)?.stuId,
                                title:"新的导读安排已发布",
                                content:"本周你被安排为第["+re.groupId+"]的导读人员，请按时到位并完成评分"
                            },
                        })
                        postFetch({
                            url:MNG_ADD_BULLETIN_URL,
                            values:{
                                stuId: docs.find((doc)=>doc.name==re.recorder2Name)?.stuId,
                                title:"新的导读安排已发布",
                                content:"本周你被安排为第["+re.groupId+"]的导读人员，请按时到位并完成评分"
                            }
                        })
                    })
                })
                alertBox({text:"添加成功",severity:"success"})
                setRefresh((prev)=>!prev)
            }
        })

    }

    const handleDelete=(recorder)=>{//删除导读信息
        if(!hasPermission){
            alertBox({text:"无权进行删除",severity:"error"})
            return
        }
        const cor=confirmModal({
            title:"将同时删除四个辅读安排，确定要删除吗？",
            handleCorfirm:()=>{
                cor.close()
                deleteFetch({
                    url:`${DELETE_RECORDER_URL}?id=${recorder.id}&groupId=${recorder.groupId}`,
                    successCallback:()=>{
                        setRefresh((prev)=>!prev)
                    }
                })
            }
        })
    }

    const handleBaseScoreChanged=(recorder,score)=>{//修改基础分数
        postFetch({
            url:MNG_UPDATE_RECORDER,
            values:{
                id:recorder.id,
                baseScore:score
            },
            successCallback:()=>{
                setRefresh((prev)=>!prev)
            }
        })
    }

    useEffect(()=>{
        getAllDocs().then((res)=>{
            setDocs(res?.data || [])
        })
        getAllRecorder({page,limit:12}).then((res)=>{
            setRecorder(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total / 12) || 0);
        })

    },[refresh,page,pageNo])

    return (
        <div>
            <Card>

                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        导读安排
                    </Typography>

                    {hasPermission&& (
                        <Button
                            color="primary"
                            size="small"
                            variant="outlined"
                            onClick={handleOpen}
                        >
                            添加导读安排
                        </Button>
                    )}
                </Box>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>发布时间</TableCell>
                                <TableCell>组号</TableCell>
                                <TableCell>导读人员1</TableCell>
                                <TableCell>导读人员2</TableCell>
                                <TableCell align="center">基础分数</TableCell>
                                {hasPermission && (
                                    <TableCell align="center">操作</TableCell>
                                )}
                            </TableRow>
                        </TableHead>

                        {recorder.map((singleRecorder)=>(
                            <TableBody>
                                <TableRow>
                                    <TableCell>{singleRecorder.date}</TableCell>
                                    <TableCell>{singleRecorder.groupId}</TableCell>
                                    <TableCell>{singleRecorder.recorder1Name}</TableCell>
                                    <TableCell>{singleRecorder.recorder2Name}</TableCell>
                                    {(userInfo.name==singleRecorder.recorder1Name || userInfo.name==singleRecorder.recorder2Name)? (
                                            <TableCell align="center">
                                                <TextField
                                                    select
                                                    size="small"
                                                    value={singleRecorder.baseScore}
                                                    variant="outlined"
                                                    onChange={(e)=>{
                                                        handleBaseScoreChanged(singleRecorder,parseInt(e.target.value))
                                                    }}
                                                    SelectProps={{
                                                        native:true
                                                    }}
                                                >
                                                    <option value={0}>0</option>
                                                    <option value={1}>1</option>
                                                    <option value={2}>2</option>
                                                    <option value={3}>3</option>
                                                    <option value={4}>4</option>
                                                    <option value={5}>5</option>
                                                </TextField>
                                            </TableCell>
                                        ):(
                                            <TableCell align="center">{singleRecorder.baseScore}</TableCell>
                                        )
                                    }
                                    {hasPermission &&(
                                        <TableCell align="center">
                                            <Button
                                                size="small"
                                                color="primary"
                                                variant="text"
                                                onClick={()=>{
                                                    handleDelete(singleRecorder)
                                                }}>删除</Button>
                                        </TableCell>
                                    )}
                                </TableRow>
                                {
                                    singleRecorder.groupId==4 &&(
                                        <MinusOutlined />
                                    )
                                }
                            </TableBody>
                        ))}
                    </Table>
                </Box>
                {pageNo > 1 && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        color="primary"
                        onChange={(e, i) => setPage(i)}
                    />
                )}
            </Card>
        </div>
    )
}
