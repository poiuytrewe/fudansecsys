import ContentStyle from "../../../components/Style/ContentStyle";
import {
    Box,
    Button,
    Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableRow,
    Typography
} from "@material-ui/core";
import {TableHead} from "@mui/material";
import {useEffect, useState} from "react";
import {DELETE_WEEKLYPUBLICATION, STU_GET_PUBLICATION} from "../../../settings";
import {UploadOutlined} from "@ant-design/icons";
import Pagination from "@material-ui/lab/Pagination";
import PublicationComment from "../dialog/PublicationComment";
import WeeklyPublicationDialog from "../dialog/WeeklyPublicationDialog";
import PublicationUploadDialog from "../dialog/PublicationUploadDialog";
import confirmModal from "../../../components/ConfirmModal";

export default function WeeklyPublicationForStu(props){
    const {userInfo}=props
    const classes=ContentStyle()
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [weeklyPublication,setWeeklyPublication]=useState([])//得到的所有的周报文件
    const [publication,setPublication]=useState({})//查看评价的周报文件
    const [open,setOpen]=useState(false)
    const [publicationOpen,setPublicationOpen]=useState(false)//控制查看周报信息的dialog是否打开
    const [uploadOpen, setUploadOpen]=useState(false)
    const [publicationValues, setPublicationValues]=useState({})

    const getPublication=async ({stuId, page=1, limit})=>{
        try{
            let response= await fetch(
                `${STU_GET_PUBLICATION}?stuId=${stuId}&limit=${limit}&offset=${(page-1)*limit}`
            );
            return response.json()
        }catch (error){
            console.log(error)
        }
    }

    const handleDelete=(id)=>{
        fetch(`${DELETE_WEEKLYPUBLICATION}?id=${id}`,{method: "POST"})
            .then(()=>{
                setRefresh((p)=>!p)
            })
    }

    useEffect(()=>{
        getPublication({stuId:userInfo.stuId,page, limit:10}).then((res)=>{
            setWeeklyPublication(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total/10) || 0)
        })
    },[refresh, page, pageNo])

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        周报模块
                    </Typography>

                    <Button
                        color="primary"
                        size="large"
                        variant="outlined"
                        onClick={()=>{
                            setUploadOpen(true)
                            setPublicationValues({
                                stuName:userInfo.name,
                                stuId:userInfo.stuId
                            })
                        }}>
                        <UploadOutlined/>上传周报</Button>
                </Box>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>对应周数</TableCell>
                                <TableCell align="center">周报提交时间</TableCell>
                                <TableCell align="center">周报</TableCell>
                                <TableCell align="center">周报评分</TableCell>
                                <TableCell align="center">周报评语</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {weeklyPublication.map((publication)=>(
                                <TableRow>
                                    <TableCell>{publication.week}</TableCell>
                                    <TableCell align="center">{publication.date}</TableCell>
                                    <TableCell align="center">
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="outlined"
                                            onClick={()=>{
                                                setPublicationOpen(true)
                                                setPublication(publication)
                                                console.log(publication)
                                            }}>查看周报内容</Button>
                                    </TableCell>
                                    <TableCell align="center">{
                                        publication.score ? (publication.score):(
                                            "导师暂未评分"
                                        )
                                    }</TableCell>
                                    <TableCell align="center">
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                setPublication(publication)
                                                setOpen(true)
                                            }}
                                        >查看周报评语</Button>
                                    </TableCell>

                                    <TableCell align="center">
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                setUploadOpen(true)
                                                setPublicationValues(publication)
                                            }}>修改周报</Button>

                                        {publication.isRead==0 ? (
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="text"
                                                onClick={()=>{
                                                    const cor = confirmModal({
                                                        title:"该周报导师还没有阅读，确认要删除这个周报吗？",
                                                        handleCorfirm:()=>{
                                                            cor.close()
                                                            handleDelete(publication.id)
                                                        }
                                                    })
                                                }}>删除周报</Button>
                                        ):(
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="text"
                                                disabled
                                            >删除周报</Button>
                                        )}
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Box>

                {pageNo>1 && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        color="primary"
                        onChange={(e,i)=>setPage(i)}
                    />
                )}
            </Card>

            <PublicationComment open={open}
                                onClose={()=>{
                                    setOpen(false)
                                    setPublication({})
                                    setRefresh((p)=>!p)
                                }}
                                publication={publication}/>

            <WeeklyPublicationDialog open={publicationOpen}
                                     onClose={()=>{
                                         setPublicationOpen(false)
                                         setPublication({})
                                         setRefresh((p)=>!p)
                                     }}
                                     weeklyPublication={publication}/>

            <PublicationUploadDialog open={uploadOpen}
                                     onClose={()=>{
                                         setUploadOpen(false)
                                         setPublicationValues({})
                                         setRefresh((p)=>!p)}}
                                     detail={publicationValues} />

        </div>
    )
}
