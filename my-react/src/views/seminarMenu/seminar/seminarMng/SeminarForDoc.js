import {
    Box,
    Button,
    Divider,
    makeStyles,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@material-ui/core";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {
    DELETE_SECTION_URL,
    DOWNLOAD_SECTION_URL,
    DOWNLOAD_SEMINAR_URL,
    GET_ALL_SECTION_URL,
    UPLOAD_SECTION_URL
} from "../../../../settings";
import alertBox from "../../../../components/AlertBox";
import {message, notification, Upload} from "antd";
import {DownloadOutlined, UploadOutlined} from "@ant-design/icons";
import Pagination from "@material-ui/lab/Pagination";
import SeminarSectionEditForm from "../SeminarSectionEditForm";
import confirmModal from "../../../../components/ConfirmModal";
import {deleteFetch} from "../../../../base";
import SeminarSectionDetailForm from "../SeminarSectionDetailForm";


const useStyles = makeStyles((theme) => ({
    root: {},
    actions: {
        justifyContent: "flex-end",
    },
    td: {
        maxWidth: "200px",
    },
    header: {
        display: "flex",
        justifyContent: "space-between",
        padding: "16px",
        "& p": {
            lineHeight: 2,
        },
    },
    Pagination: {
        padding: theme.spacing(2),
        "& .MuiPagination-ul": {
            justifyContent: "center",
        },
    },
}));

const SeminarForDoc=()=>{
    const classes=useStyles()
    const {userInfo}=useContext(UserContext)
    const [section,setSection]=useState([])
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [paperId,setPaperId]=useState(0)//需要上传的论文Id
    const [open,setOpen]=useState(false) //这个是判断编辑/新增的专题框是否打开
    const [sectionDetail,setSectionDetail]=useState({}) //要进行编辑的信息
    const [detailOpen,setDetailOpen]=useState(false)

    const hasPermission= userInfo.status==1 //判断用户是否是博士生，如果是博士生，则能够进行专题操作

    const getAllSection=()=>{//从后台调取所有的section安排
        fetch(`${GET_ALL_SECTION_URL}?limit=10&offset=${(page-1)*10}`,{})
            .then((res)=>res.json()) //将接受到的Promise对象res转为json格式
            .catch((error)=>console.log("Error:",error))
            .then((response)=>{
                setSection(response?.data || [])
                setPageNo(Math.ceil(response?.paging?.total/10) || 0)
            })
    }

    const getDownloadSection=(section)=>{ //下载文件功能
        fetch(`${DOWNLOAD_SECTION_URL}?id=${section.id}`,{
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
                aLink.download=`${section.sectionFileName}`
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

    const handleDeleteSection=(section)=>{ //确定是否删除
        if(section.stuId != userInfo.stuId){
            alertBox({text:"无权进行删除",severity:"error"})
        }
        else{
            const cor=confirmModal({
                title:"确定要删除吗？",
                handleCorfirm:()=>{
                    cor.close()
                    deleteFetch({
                        url:`${DELETE_SECTION_URL}?id=${section.id}`,
                        successCallback:()=>{
                            setRefresh((prev)=>!prev)
                        }
                    })
                }
            })
        }
    }

    const uploadProps={//Upload的相关属性
        name:"sectionFile",
        action:UPLOAD_SECTION_URL,
        method:"POST",
        data:{
            id:paperId
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

    useEffect(()=>{
        getAllSection()
    },[refresh, page, pageNo])

    return (
        <div>
            <Box className={classes.header}>
                <Typography color="textPrimary" size="small" component="h2">
                    专题选讲
                </Typography>

                {hasPermission && (
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{
                            setOpen(true)
                            setSectionDetail({
                                name:userInfo.name,
                                stuId:userInfo.stuId
                            })
                        }}
                    >
                        添加专题
                    </Button>
                )
                }

            </Box>

            <Divider/>

            <Box minWidth={800}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>演讲时间</TableCell>
                            <TableCell>专题主讲人</TableCell>
                            <TableCell align="center">主题</TableCell>
                            <TableCell align="center">资源</TableCell>
                            <TableCell align="center">推荐论文</TableCell>
                            {hasPermission && ( //博士生有权利将他们发表过的专题修改或删除
                                <TableCell align="center">操作</TableCell>
                            )}
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {section.map((sec)=>(
                            <TableRow>
                                <TableCell>{sec.date}</TableCell>
                                <TableCell>{sec.name}</TableCell>
                                <TableCell align="center">{sec.topic}</TableCell>

                                {sec.stuId==userInfo.stuId?( //这里是判断发布这个专题的人是否是用户本人，如果是，可以上传资源，如果不是，只能下载资源
                                    <TableCell align="center">
                                        <Upload {...uploadProps}>
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="outlined"
                                                onClick={()=>{
                                                    setPaperId(sec.id)
                                                }}
                                            >
                                                <UploadOutlined/>{sec.isFile==1?"重新上传":"上传文件"}
                                            </Button>
                                        </Upload>
                                    </TableCell>
                                ):(
                                    <TableCell align="center">
                                        {sec.isFile==1?( //如果isFIle为1，那么说明数据库已经有文件，就可以进行下载，否则显示暂未上传
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="outlined"
                                                onClick={()=>{
                                                    getDownloadSection(sec)
                                                }}
                                            >
                                            <DownloadOutlined/>下载文件
                                            </Button>
                                        ):"暂未上传"}
                                    </TableCell>
                                )}

                                <TableCell align="center">
                                    <Button
                                        color="primary"
                                        size="small"
                                        variant="text"
                                        onClick={()=>{
                                            setDetailOpen(true)
                                            setSectionDetail(sec)
                                        }}>查看推荐论文</Button>
                                    </TableCell>

                                {hasPermission &&(//首先，得是博士，才有操作的大前提
                                  <TableCell align="center">
                                      {
                                          sec.stuId==userInfo.stuId && (//这里是判断专题的发布人是否是用户本人，如果是用户本人，那么就有删除修改权限，否则没有
                                          <>
                                              <Button
                                                  color="primary"
                                                  size="small"
                                                  variant="text"
                                                  onClick={()=>{
                                                      setSectionDetail({
                                                          id:sec.id,
                                                          name:userInfo.name,
                                                          stuId:userInfo.stuId,
                                                          topic:sec.topic,
                                                          paperRec:sec.paperRec
                                                      })
                                                      setOpen(true)
                                                  }}
                                              >编辑</Button>
                                              <Button
                                                  color="primary"
                                                  size="small"
                                                  variant="text"
                                                  onClick={()=>{
                                                      handleDeleteSection(sec)
                                                  }}
                                              >删除</Button>
                                          </>
                                          )
                                      }
                                  </TableCell>
                                )}
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

            <SeminarSectionEditForm
                open={open}
                sectionDetail={sectionDetail}
                onClose={()=>{
                    setOpen(false)
                    setSectionDetail({})
                    setRefresh((prev)=>!prev)
                }}
            />

            <SeminarSectionDetailForm
                open={detailOpen}
                sectionDetail={sectionDetail}
                onClose={()=>{
                    setDetailOpen(false)
                    setSectionDetail({})
                    setRefresh((p)=>!p)
                }}/>
        </div>
    )
}

export default SeminarForDoc;
