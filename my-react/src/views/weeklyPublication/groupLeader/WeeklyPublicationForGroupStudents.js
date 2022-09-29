import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import {
    GROUPLEADER_GET_PUBLICATION,
    GROUPLEADER_STATISTICS_PUBLICATION,
    MENTOR_STATISTICS_PUBLICATION
} from "../../../settings";
import {useEffect, useState} from "react";
import {
    Button,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@material-ui/core";
import Pagination from "@material-ui/lab/Pagination";
import PublicationComment from "../dialog/PublicationComment";
import WeeklyPublicationDialog from "../dialog/WeeklyPublicationDialog";
import UndoneStudentDialog from "../dialog/UndoneStudentDialog";

export default function WeeklyPublicationForGroupStudents(props){
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
    const {userInfo}=props
    const yearNum=new Date().getFullYear()
    const weekNum=getWeek()
    const [week,setWeek]=useState(yearNum+"-W"+(weekNum<10 ? "0"+weekNum : weekNum))
    const classes=ContentStyleForPage()
    const [weeklyPublication, setWeeklyPublication]=useState([])
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [open,setOpen]=useState(false)
    const [publication,setPublication]=useState({})//查看评论或者修改评论的周报文件
    const [publicationOpen, setPublicationOpen]=useState(false)//dialog是否打开
    const [undoneCount, setUndoneCount]=useState(0)
    const [undoneList,setUndoneList]=useState([])
    const [undoneListOpen, setUndoneListOpen]=useState(false)


    const groupLeaderGetWeeklyPublication= async ({stuName, page=1, limit, week})=>{
        try{
            let response=await fetch(
                `${GROUPLEADER_GET_PUBLICATION}?stuName=${stuName}&week=${week}&limit=${limit}&offset=${(page-1)*limit}`
            )
            return response.json()
        }catch (error){
            console.log(error)
        }
    }

    const groupLeaderStatistics=({stuName, week})=>{
        fetch(`${GROUPLEADER_STATISTICS_PUBLICATION}?stuName=${stuName}&week=${week}`,{method:"GET"})
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
        groupLeaderStatistics({stuName:userInfo.name, week:"第"+year+"年第"+weekNum+"周"})
    },[week])

    useEffect(()=>{
        const year= parseInt(week.slice(0,4))
        const weekNum=parseInt(week.slice(6))
        groupLeaderGetWeeklyPublication({stuName:userInfo.name, page, limit:10, week:"第"+year+"年第"+weekNum+"周"}).then((res)=>{
            setWeeklyPublication(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total /10) || 0)
        })
    },[refresh, page, pageNo, week])

    return (
        <div className={classes.root}>
            <div>
                <Typography color="textPrimary" size="small" component="h2">
                    组内学生周报
                </Typography>

                <div className={classes.header}>
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

            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>学生姓名</TableCell>
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
                            <TableCell>{publication.stuName}</TableCell>
                            <TableCell align="center">{
                                publication.date ? (publication.date) : (
                                    "暂未上传周报"
                                )}</TableCell>
                            {publication.target ? (
                                <TableCell align="center">
                                    <Button
                                        color="primary"
                                        size="small"
                                        variant="outlined"
                                        onClick={()=>{
                                            setPublicationOpen(true)
                                            setPublication(publication)
                                        }}
                                    >查看周报内容</Button>
                                </TableCell>
                            ):(
                                <TableCell align="center">暂未上传</TableCell>
                            )}
                            <TableCell align="center">
                                {publication.score? (
                                    publication.score
                                ): "导师暂未评分"}
                            </TableCell>
                            <TableCell align="center">
                                {publication.target ? (
                                    <Button
                                        color="primary"
                                        size="small"
                                        variant="text"
                                        onClick={()=>{
                                            setOpen(true)
                                            setPublication(publication)
                                        }}
                                    >查看周报评语</Button>
                                ): "暂未上传周报"}

                            </TableCell>
                            <TableCell align="center">
                                <Button
                                    color="primary"
                                    size="small"
                                    variant="text"
                                    href={`/app/weeklyPublication/${publication.stuId}`}
                                >查看学生所有周报</Button>
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

            <UndoneStudentDialog open={undoneListOpen}
                                 list={undoneList}
                                 onClose={()=>{
                                     setUndoneListOpen(false)
                                 }}/>

        </div>
    )

}
