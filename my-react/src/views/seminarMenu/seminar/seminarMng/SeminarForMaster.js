import React, { useState, useEffect, useContext } from "react";
import {
    Box,
    Button,
    Typography,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow, TextField,
} from "@material-ui/core";
import Pagination from "@material-ui/lab/Pagination";
import { UserContext } from "src/layouts/Context";
import {
    GET_ALL_SEMINAR_URL,
    DELETE_SEMINAR_URL,
    ADD_SEMINAR_URL,
    UPDATE_SEMINAR_URL,
    DOWNLOAD_SEMINAR_URL, UPLOAD_SEMINAR_URL, MNG_ADD_BULLETIN_URL, MNG_GET_ALL_USER
} from "src/settings";
import {deleteFetch, postFetch} from "src/base";
import corfirmModal from "src/components/ConfirmModal";

import alertBox from "../../../../components/AlertBox";
import {message, notification, Upload} from "antd";
import {DownloadOutlined, UploadOutlined} from "@ant-design/icons";
import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";

const SeminarForMaster= () => {
    const classes = ContentStyleForPage()
    const [refresh, setRefresh] = useState(false);
    const [seminars, setSeminars] = useState([]);
    const [groupMembers,setGroupMembers]=useState([])
    const { userInfo } = useContext(UserContext);
    const [page, setPage] = useState(1);
    const [pageNo, setPageNo] = useState(0);
    const [id,setId]=useState(0)

    //向后台调取用户列表并更新界面
    const getAllSeminar = () => {//获取所有的演讲安排
        fetch(`${GET_ALL_SEMINAR_URL}?limit=10&offset=${(page - 1) * 10}`, {})
            .then((res) => res.json())
            .catch((error) => console.error("Error:", error))
            .then((response) => {
                setSeminars(response?.data || []);
                setPageNo(Math.ceil(response?.paging?.total / 10) || 0);
            })
    };

    const getAllGroupMembers=()=>{
        fetch(`${MNG_GET_ALL_USER}?groupId=${userInfo.groupId}`)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((response)=>{
                setGroupMembers(response?.data || [])
            })
    }

    const handleDeleteSeminar = (seminar) => {
        if(userInfo.groupId==seminar.groupId){
            const cor = corfirmModal({
                title: "确定要删除吗？",
                handleCorfirm: () => {
                    cor.close();
                    deleteFetch({
                        url: `${DELETE_SEMINAR_URL}?id=${seminar.id}`,
                        values: { id },
                        successCallback: () => {
                            setRefresh((prev) => !prev);
                        },
                    });
                },
            });
        }
        else{
            alertBox({text:"您无权删除这条演讲记录！",severity:"error"})
            return
        }
    };

    const uploadProps={
        name:"file",
        action:UPLOAD_SEMINAR_URL,
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

    const getDownloadSeminar=(seminar)=>{
        fetch(`${DOWNLOAD_SEMINAR_URL}?id=${seminar.id}`,{
            method:"GET",
            credentials:"include",
            headers: new Headers({
                'Content-Type': 'application/json',
            })
        }).then((res)=>{
            res.blob().then(blob=>{
                const aLink=document.createElement("a")
                document.body.appendChild(aLink)
                aLink.style.display="none"
                const objectUrl=window.URL.createObjectURL(blob)
                aLink.href=objectUrl
                aLink.download=`${seminar.fileName}`
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

    useEffect(()=>{
        getAllSeminar()
        getAllGroupMembers()
    }, [refresh,page,pageNo]);

    return (
        <div>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        论文演讲安排
                    </Typography>

                    {userInfo.groupId!==0 && (
                        <Button
                            color="primary"
                            size="small"
                            variant="outlined"
                            onClick={()=>{ //点击添加一次演讲安排
                                postFetch({//为本组添加一次演讲安排
                                    url:ADD_SEMINAR_URL,
                                    values:{
                                        groupId:userInfo.groupId
                                    },
                                    successCallback:()=>{
                                        alertBox({text:"添加成功",severity:"success"})
                                        setRefresh((prev)=>!prev)
                                    }
                                })
                            }}
                        >
                            添加演讲安排
                        </Button>
                    )}

                </Box>
                <Divider />
                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>发布时间</TableCell>
                                <TableCell>组号</TableCell>
                                <TableCell>主讲人</TableCell>
                                <TableCell>演讲资源</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {seminars.map((seminar) => (
                                <TableRow hover key={seminar.id}>
                                    <TableCell>{seminar.date}</TableCell>
                                    <TableCell>{seminar.groupId}</TableCell>
                                    {(userInfo.groupId==seminar.groupId)?(//设定的演讲组和登录用户的演讲组是否相同
                                        <TableCell>
                                            <TextField
                                                select
                                                size="small"
                                                variant="outlined"
                                                value={seminar.speakerName?seminar.speakerName:"无"}
                                                onChange={(e)=>{
                                                    postFetch({
                                                        url:UPDATE_SEMINAR_URL,
                                                        values:{
                                                            id:seminar.id,
                                                            speakerName:e.target.value,
                                                            speakerStuId:groupMembers.find((member)=>member.name==e.target.value).stuId
                                                        },
                                                        successCallback:()=>{
                                                            setRefresh((prev)=>!prev)
                                                        }
                                                    })
                                                    postFetch({
                                                        url:MNG_ADD_BULLETIN_URL,
                                                        values:{
                                                            stuId:groupMembers.find((member)=>member.name==e.target.value).stuId,
                                                            title:"请及时准备下周论文演讲",
                                                            content:"经组内成员商议决定，下周的论文演讲由你完成，请及时准备并上传演讲资料"
                                                        }
                                                    })
                                                }}
                                                SelectProps={{
                                                    native:true
                                                }}
                                            >
                                                <option>无</option>
                                                {groupMembers.map((user)=>(
                                                    <option value={user.name}>
                                                        {user.name}
                                                    </option>
                                                ))}
                                            </TextField>
                                        </TableCell>
                                    ):(<TableCell>{seminar.speakerName}</TableCell>)}

                                    {seminar.speakerName==userInfo.name?(
                                        <TableCell>
                                            <Box>
                                                <Upload {...uploadProps}>
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="outlined"
                                                        onClick={()=>{
                                                            setId(seminar.id)
                                                        }}
                                                    >
                                                        <UploadOutlined/>{seminar.isFile==1?"重新上传":"上传文件"}
                                                    </Button>
                                                </Upload >
                                            </Box>
                                        </TableCell>
                                    ): (<TableCell>
                                        {seminar.isFile==1?(
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="outlined"
                                                onClick={()=>{
                                                    getDownloadSeminar(seminar)
                                                }}
                                            ><DownloadOutlined />下载文件</Button>
                                        ):"暂未上传"}
                                    </TableCell>)}
                                        <TableCell align="center">
                                            {userInfo.groupId==seminar.groupId &&(
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={(e) => handleDeleteSeminar(seminar)}
                                                >
                                                    删除
                                                </Button>
                                                )
                                            }
                                        </TableCell>
                                </TableRow>
                            ))}
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
        </div>
    );
};

export default SeminarForMaster;
