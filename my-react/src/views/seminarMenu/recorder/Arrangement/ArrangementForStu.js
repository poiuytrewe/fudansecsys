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
import {Upload, message, notification} from "antd";
import {
    DOWNLOAD_RECORDER_URL, DOWNLOAD_SUMMARIZER_URL, MNG_ADD_BULLETIN_URL, MNG_GET_ALL_USER,
    MNG_UPDATE_RECORDER,
    UPLOAD_RECORDER_URL,
    UPLOAD_SUMMARY_URL
} from "../../../../settings";
import { postFetch} from "../../../../base";
import { getAllRecorder} from "../../../../service/userService";
import {UserContext} from "../../../../layouts/Context";
import {DownloadOutlined, UploadOutlined} from '@ant-design/icons';
import alertBox from "../../../../components/AlertBox";

import Pagination from "@material-ui/lab/Pagination";
import ContentStyle from "../../../../components/Style/ContentStyle";

export default function ArrangementForStu(){
    const classes=ContentStyle()
    const [recorder,setRecorder]=useState([])
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [groupStudents,setGroupStudents]=useState([])
    const {userInfo}=useContext(UserContext)
    const [id,setId]=useState(0)//此处的id是方便上传文件而使用

    const uploadProps={//上传领读文件
        name:"recorderFile",
        action:UPLOAD_RECORDER_URL,
        method:"POST",
        data:{
            id:id
        },
        onChange (info){
            if (info.file.status !== 'uploading') {
                console.log(info.file, info.fileList);
            }
            if (info.file.status === 'done') {
                alertBox({text:"上传成功",severity:"success"})
                message.success(`${info.file.name} file uploaded successfully`);
                setRefresh((prev)=>!prev)
            } else if (info.file.status === 'error') {
                alertBox({text:"上传失败",severity:"error"})
                message.error(`${info.file.name} file upload failed.`);
            }
        },
    }

    const uploadPropsS={//上传记录文件
        name:"summarizerFile",
        action:UPLOAD_SUMMARY_URL,
        method:"POST",
        data:{
            id:id
        },
        onChange(info) {
            if (info.file.status !== 'uploading') {
                console.log(info.file, info.fileList);
            }
            if (info.file.status === 'done') {
                alertBox({text:"上传成功",severity:"success"})
                message.success(`${info.file.name} file uploaded successfully`);
                setRefresh((prev)=>!prev)
            } else if (info.file.status === 'error') {
                alertBox({text:"上传失败",severity:"error"})
                message.error(`${info.file.name} file upload failed.`);
            }
        },
    }

    const handleSummarizer=(recorder,summarizerName)=> {//选择记录人员，并保存到数据库中
        postFetch({
            url: MNG_UPDATE_RECORDER,
            values: {
                id: recorder.id,
                summarizerName: summarizerName
            },
            successCallback: () => {
                setRefresh((prev) => !prev)
            }
        })
    }

    const handleRecorder=(recorder,recorderName)=>{//选择领读人员，并保存到数据库中
        postFetch({
            url:MNG_UPDATE_RECORDER,
            values:{
                id:recorder.id,
                recorder:recorderName
            },
            successCallback:()=>{
                setRefresh((prev)=>!prev)
            }
        })
    }


    const getDownloadRecorder=(recorder)=>{//下载领读文件
            fetch(`${DOWNLOAD_RECORDER_URL}?id=${recorder.id}`,{
                method:"GET",
                credentials:"include",
                headers: new Headers({
                    'Content-Type': 'application/json',
                })
            }).then((res)=>{
                res.blob().then(blob=>{
                    const aLink=document.createElement("a") //创建一个a标签
                    document.body.appendChild(aLink)
                    aLink.style.display="none" //将代码隐蔽
                    const objectUrl=window.URL.createObjectURL(blob) //可以在浏览器上进行预览，这句话的意思应该是将blob文件的可见体保存到objectUrl中
                    aLink.href=objectUrl
                    aLink.download=`${recorder.recorderFileName}`
                    aLink.click()
                    document.body.removeChild(aLink)
                })
            }).catch((error)=>{
                console.log("下载失败",error)
                notification.success({
                    message:"文件下载失败",
                    description:error
                })
            })
    }

    const getDownloadSummarizer=(recorder)=>{//下载记录文件
        fetch(`${DOWNLOAD_SUMMARIZER_URL}?id=${recorder.id}`,{
            method:"GET",
            credentials:"include",
            headers:new Headers({
                'Content-Type': 'application/json',
            })
        }).then((res)=>{
            res.blob().then(blob=>{
                const aLink=document.createElement("a")
                document.body.appendChild(aLink)
                aLink.style.display="none"
                const objectUrl=window.URL.createObjectURL(blob)
                aLink.href=objectUrl
                aLink.download=`${recorder.summarizerFileName}`
                aLink.click()
                document.body.removeChild(aLink)
            })
        }).catch((error)=>{
            console.log("下载失败",error)
            notification.success({
                message:"文件下载失败",
                description:error
            })
        })
    }

    const getGroupStudents=async ()=>{ //获取同组的人员
        try{
            let response=await fetch(`${MNG_GET_ALL_USER}?groupId=${userInfo.groupId}`)
            return await response.json()
        }
        catch (error){
            console.log(error)
        }
    }

    useEffect(()=>{
        getAllRecorder({page,limit:40}).then((res)=>{//获取所有的recorder记录
            setRecorder(res.data || [])
            setPageNo(Math.ceil(res?.paging?.total / 40) || 0);
        })
        getGroupStudents().then((res)=>{
            setGroupStudents(res.data || [])
        })
    },[refresh,page,pageNo])

    return (
        <div >
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        导读安排
                    </Typography>
                </Box>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>发布时间</TableCell>
                                <TableCell>基础分数</TableCell>
                                <TableCell>辅读人员1</TableCell>
                                <TableCell>辅读人员2</TableCell>
                                <TableCell>领读人员</TableCell>
                                <TableCell>记录人员</TableCell>
                                <TableCell align="center">领读文件</TableCell>
                                <TableCell align="center">记录文件</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {recorder.map((singleRecorder)=>{
                                if(singleRecorder.groupId==userInfo.groupId){
                                    return (
                                        <TableRow>
                                            <TableCell>{singleRecorder.date}</TableCell> {/*上传日期*/}
                                            <TableCell>{singleRecorder.baseScore}</TableCell>
                                            <TableCell>{singleRecorder.recorder1Name}</TableCell> {/*上传辅读人1的名字*/}
                                            <TableCell>{singleRecorder.recorder2Name}</TableCell> {/*上传辅读人2的名字*/}
                                            <TableCell>
                                                <TextField
                                                    select
                                                    size="small"
                                                    value={singleRecorder.recorder?singleRecorder.recorder:"无"}
                                                    variant="outlined"
                                                    onChange={(e)=>{
                                                        handleRecorder(singleRecorder,e.target.value)
                                                    }}
                                                    SelectProps={{
                                                        native:true
                                                    }}
                                                >
                                                    <option>无</option>
                                                    {groupStudents.map((user)=>(
                                                        <option value={user.name}>
                                                            {user.name}
                                                        </option>
                                                    ))}
                                                </TextField>
                                            </TableCell>

                                            <TableCell>
                                                <TextField
                                                    select
                                                    size="small"
                                                    value={singleRecorder.summarizerName?singleRecorder.summarizerName:"无"}
                                                    variant="outlined"
                                                    onChange={(e)=>{
                                                        handleSummarizer(singleRecorder,e.target.value)
                                                    }}
                                                    SelectProps={{
                                                        native:true
                                                    }}
                                                >
                                                    <option>无</option>
                                                    {groupStudents.map((user)=>(
                                                        <option value={user.name}>
                                                            {user.name}
                                                        </option>
                                                    ))}
                                                </TextField>
                                            </TableCell>

                                            {
                                                singleRecorder.recorder==userInfo.name?( //这里需要上传领读文件，只有领读人员才能上传，所以这里是判断用户是否是领读人员

                                                    //如果是领读人员
                                                    <TableCell align="center">
                                                        <Box>
                                                            <Upload {...uploadProps}>
                                                                <Button
                                                                    color="primary"
                                                                    size="small"
                                                                    variant="outlined"
                                                                    onClick={()=>{
                                                                        setId(singleRecorder.id)
                                                                    }}
                                                                >
                                                                    <UploadOutlined/>{singleRecorder.isRecorderFile==1?"重新上传":"上传文件"}
                                                                </Button>
                                                            </Upload >
                                                        </Box>
                                                    </TableCell>
                                                ):(//如果不是领读人员，那么能做的只能是下载，或者文件还没有上传，那么就只能看着
                                                    <TableCell align="center">
                                                        {singleRecorder.isRecorderFile==1?(
                                                            <Button
                                                                color="primary"
                                                                size="small"
                                                                variant="outlined"
                                                                onClick={()=>{
                                                                    getDownloadRecorder(singleRecorder)
                                                                }}
                                                            ><DownloadOutlined />下载文件</Button>
                                                        ):"暂未上传"}
                                                    </TableCell>
                                                )
                                            }

                                            {
                                                singleRecorder.summarizerName==userInfo.name?( //这里需要上传记录文件，只有记录人员才能上传，所以这里是判断用户是否是记录人员

                                                    //如果是记录人员
                                                    <TableCell align="center">
                                                        <Box>
                                                            <Upload {...uploadPropsS}>
                                                                <Button
                                                                    color="primary"
                                                                    size="small"
                                                                    variant="outlined"
                                                                    onClick={()=>{
                                                                        setId(singleRecorder.id)
                                                                    }}
                                                                >
                                                                    <UploadOutlined/>{singleRecorder.isSummarizerFile==1?"重新上传":"上传文件"}
                                                                </Button>
                                                            </Upload >
                                                        </Box>
                                                    </TableCell>
                                                ):(//如果不是领读人员，那么能做的只能是下载，或者文件还没有上传，那么就只能看着
                                                    <TableCell align="center">
                                                        {singleRecorder.isSummarizerFile==1?(
                                                            <Button
                                                                color="primary"
                                                                size="small"
                                                                variant="outlined"
                                                                onClick={()=>{
                                                                    getDownloadSummarizer(singleRecorder)
                                                                }}
                                                            ><DownloadOutlined />下载文件</Button>
                                                        ):"暂未上传"}
                                                    </TableCell>
                                                )
                                            }
                                        </TableRow>
                                    )
                                }
                            })}
                        </TableBody>
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
