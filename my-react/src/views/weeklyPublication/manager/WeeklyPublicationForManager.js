import ContentStyle from "../../../components/Style/ContentStyle";
import {DOWNLOAD_WEEKLYPUBLICATION, MANAGER_GET_PUBLICATION, MANAGER_STATISTICS_PUBLICATION} from "../../../settings";
import {notification} from "antd";
import {useEffect, useState} from "react";
import {
    Box,
    Button,
    Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow, TextField,
    Typography
} from "@material-ui/core";
import {DownloadOutlined} from "@ant-design/icons";
import Pagination from "@material-ui/lab/Pagination";
import WeeklyPublicationDialog from "../dialog/WeeklyPublicationDialog";
import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import UndoneStudentDialogForMng from "../dialog/UndoneStudentDialogForMng";

const TYPE={
    0:"硕士",
    10:"博士"
}

export default function WeeklyPublicationForManager(){
    const getWeek=()=>{//获取现在是今年的第几周
        const today = new Date();
        let firstDayOfYear = new Date(today.getFullYear(), 0, 1);
        const dayOfWeek = firstDayOfYear.getDay();
        let spendDay = 1;
        if (dayOfWeek != 0) {
            spendDay = 7 - dayOfWeek + 1;
        }
        firstDayOfYear = new Date(today.getFullYear(), 0, spendDay);
        const d = Math.ceil((today.valueOf() - firstDayOfYear.valueOf()) / 86400000);
        return Math.ceil(d / 7)
    }
    const yearNum=new Date().getFullYear()
    const weekNum=getWeek()
    const classes=ContentStyle()
    const [week,setWeek]=useState(yearNum+"-W"+(weekNum<10 ? "0"+weekNum : weekNum))
    const [refresh,setRefresh]=useState(false)
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [weeklyPublication,setWeeklyPublication]=useState([])
    const [publication, setPublication]=useState({})
    const [publicationOpen, setPublicationOpen]=useState(false)
    const [undoneCount, setUndoneCount]=useState(0)
    const [undoneList,setUndoneList]=useState([])
    const [undoneListOpen, setUndoneListOpen]=useState(false)

    const managerGetWeeklyPublication = async ({page=1,limit, week})=>{
        try{
            let response=await fetch(
                `${MANAGER_GET_PUBLICATION}?week=${week}&limit=${limit}&offset=${(page-1)*limit}`
            )
            return response.json()
        }catch (error){
            console.log(error)
        }
    }

    const managerStatics=({week})=>{
        fetch(`${MANAGER_STATISTICS_PUBLICATION}?week=${week}`,{method:"GET"})
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setUndoneCount(res.data.unDoneCount)
                setUndoneList(res.data.unDoneStudentList)
            })
    }

    useEffect(()=>{
        const year= parseInt(week.slice(0,4))
        const weekNum=parseInt(week.slice(6))
        managerStatics({week:"第"+year+"年第"+weekNum+"周"})
    },[week])

    useEffect(()=>{
        const year= parseInt(week.slice(0,4))
        const weekNum=parseInt(week.slice(6))
        managerGetWeeklyPublication({page,limit:10,week:"第"+year+"年第"+weekNum+"周"}).then((res)=>{
            setWeeklyPublication(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total/10) || 0)
        })
    },[refresh, page, pageNo, week])

    return (
        <div className={ContentStyleForPage().root}>
                <div>
                    <Typography color="textPrimary" size="small" component="h2">
                        周报模块
                    </Typography>

                    <div className={ContentStyleForPage().header}>
                        <TextField
                            label="请选择周数"
                            InputLabelProps={{shrink:true}}
                            margin="normal"
                            color="primary"
                            size="small"
                            value={week}
                            onChange={(event)=>{
                                setWeek(event.target.value)
                            }}
                            type="week"
                            variant="outlined"/>

                        <Typography color="textPrimary" size="small" component="h2">该周未提交周报人数：{undoneCount}</Typography>

                        <Button
                            size="small"
                            color="primary"
                            variant="outlined"
                            onClick={()=>{
                                setUndoneListOpen(true)
                            }}>查看该周未提交周报名单</Button>
                    </div>
                </div>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>学生姓名</TableCell>
                                <TableCell align="center">学生类型</TableCell>
                                <TableCell align="center">周报提交时间</TableCell>
                                <TableCell align="center">周报</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {weeklyPublication.map((publication)=>(
                                <TableRow>
                                    <TableCell>{publication.stuName}</TableCell>
                                    <TableCell align="center">{TYPE[publication.type]}</TableCell>
                                    <TableCell align="center">{
                                        publication.date ? (
                                            publication.date
                                        ): (
                                            "暂未上传周报"
                                        )
                                    }</TableCell>
                                    <TableCell align="center">
                                        {publication.target ? (
                                            <Button
                                                color="primary"
                                                size="small"
                                                variant="outlined"
                                                onClick={()=>{
                                                    setPublicationOpen(true)
                                                    setPublication(publication)
                                                }}
                                            >查看周报内容</Button>
                                        ):("暂未上传")}
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

            <WeeklyPublicationDialog open={publicationOpen}
                                     onClose={()=>{
                                         setPublicationOpen(false)
                                         setPublication({})
                                         setRefresh((p)=>!p)
                                     }}
                                     weeklyPublication={publication}/>

            <UndoneStudentDialogForMng open={undoneListOpen}
                                       week={week}
                                       list={undoneList}
                                       onClose={()=>{
                                           setUndoneListOpen(false)
                                       }}/>

        </div>
    )
}
