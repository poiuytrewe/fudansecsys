import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import {Button, Divider, Table, TableBody, TableCell, TableRow, Typography} from "@material-ui/core";
import {DOWNLOAD_WEEKLYPUBLICATION, STU_GET_PUBLICATION, UPLOAD_WEEKLYPUBLICATION} from "../../../settings";
import alertBox from "../../../components/AlertBox";
import {message, notification, Upload} from "antd";
import {DownloadOutlined, UploadOutlined} from "@ant-design/icons";
import {TableHead} from "@mui/material";
import Pagination from "@material-ui/lab/Pagination";
import {useEffect, useState} from "react";
import PublicationComment from "../dialog/PublicationComment";
import WeeklyPublicationDialog from "../dialog/WeeklyPublicationDialog";
import PublicationUploadDialog from "../dialog/PublicationUploadDialog";

export default function WeeklyPublicationForGroupLeaderSelf(props){
    const {userInfo}=props
    const classes=ContentStyleForPage()
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [weeklyPublication,setWeeklyPublication]=useState([])//得到的所有的周报文件
    const [publication,setPublication]=useState({})//查看评价的周报文件
    const [open,setOpen]=useState(false)//comment的open
    const [uploadOpen, setUploadOpen]=useState(false)
    const [publicationOpen, setPublicationOpen]=useState(false)

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

    useEffect(()=>{
        getPublication({stuId:userInfo.stuId,page, limit:10}).then((res)=>{
            setWeeklyPublication(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total/10) || 0)
        })
    },[refresh, page, pageNo])

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Typography color="textPrimary" size="small" component="h2">
                    周报模块
                </Typography>

                <Button
                    color="primary"
                    size="large"
                    variant="outlined"
                    onClick={()=>{
                        setUploadOpen(true)
                    }}>上传周报</Button>
            </div>

            <Divider/>

            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>对应周数</TableCell>
                        <TableCell align="center">周报提交时间</TableCell>
                        <TableCell align="center">周报</TableCell>
                        <TableCell align="center">周报评分</TableCell>
                        <TableCell align="center">周报评语</TableCell>
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
                                    }}>查看周报内容</Button>
                            </TableCell>
                            <TableCell align="center">{publication.score ? (publication.score) : ("导师暂未评分")}</TableCell>
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
                                         setPublication({})
                                         setRefresh((p)=>!p)}}
                                     detail={{
                                         stuId:userInfo.stuId,
                                         stuName:userInfo.name
                                     }}/>

        </div>
    )
}
