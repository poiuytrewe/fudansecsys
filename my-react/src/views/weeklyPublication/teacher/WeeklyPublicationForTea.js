import {
    Box,
    Button,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow, TextField,
    Typography
} from "@material-ui/core";
import ContentStyle from "../../../components/Style/ContentStyle";
import {useEffect, useState} from "react";
import {MENTOR_GET_PUBLICATION, MENTOR_STATISTICS_PUBLICATION, UPDATE_WEEKLYPUBLICATION} from "../../../settings";
import Pagination from "@material-ui/lab/Pagination";
import PublicationComment from "../dialog/PublicationComment";
import EditPublicationComment from "../dialog/EditPublicationComment";
import {postFetch} from "../../../base";
import confirmModal from "../../../components/ConfirmModal";
import WeeklyPublicationDialog from "../dialog/WeeklyPublicationDialog";
import UndoneStudentDialog from "../dialog/UndoneStudentDialog";
import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";

export default function WeeklyPublicationForTea(props){
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
    const classes=ContentStyle()
    const [week,setWeek]=useState(yearNum+"-W"+(weekNum<10 ? "0"+weekNum : weekNum))
    const [weeklyPublication, setWeeklyPublication]=useState([])
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const [editOpen,setEditOpen]=useState(false)
    const [open,setOpen]=useState(false)
    const [publicationOpen, setPublicationOpen]=useState(false)
    const [publication,setPublication]=useState({})//查看评论或者修改评论的周报文件
    const [undoneCount, setUndoneCount]=useState(0)
    const [undoneList,setUndoneList]=useState([])
    const [undoneListOpen, setUndoneListOpen]=useState(false)

    const mentorGetWeeklyPublication=async ({stuName=userInfo.name, page=1, limit, week})=>{
        try{
            let response=await fetch(
                `${MENTOR_GET_PUBLICATION}?stuName=${stuName}&week=${week}&limit=${limit}&offset=${(page-1)*limit}`
            );
            return response.json()
        }catch (error){
            console.log(error)
        }
    }

    const mentorStatistics=({stuName, week})=>{
        fetch(`${MENTOR_STATISTICS_PUBLICATION}?stuName=${stuName}&week=${week}`,{method:"GET"})
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setUndoneCount(res.data.unDoneCount)
                setUndoneList(res.data.unDoneStudentList)
            })
    }

    const handleScoreChange=(publication, score)=>{
        const cor=confirmModal({
            title:"确认要修改周报评分吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:UPDATE_WEEKLYPUBLICATION,
                    values:{
                        id:publication.id,
                        score:score
                    },
                    successCallback:()=>{
                        setRefresh((p)=>!p)
                    }
                })
            }
        })
    }

    const getNameLine=()=>{
        let nameLine=""
        undoneList.map((l,index)=>{
            let s= index==0 ? l.name : "，"+l.name
            nameLine=nameLine+s
        })
        return nameLine
    }

    useEffect(()=>{
        const year= parseInt(week.slice(0,4))
        const weekNum=parseInt(week.slice(6))
        mentorStatistics({stuName:userInfo.name, week:"第"+year+"年第"+weekNum+"周"})
    },[week])

    useEffect(()=>{
        const year= parseInt(week.slice(0,4))
        const weekNum=parseInt(week.slice(6))
        mentorGetWeeklyPublication({stuName:userInfo.name, page, limit:10, week:"第"+year+"年第"+weekNum+"周"}).then((res)=>{
            setWeeklyPublication(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total/10) || 0)
        })
    },[refresh, page, pageNo, week])

    return (
        <div className={ContentStyleForPage().root}>
                <div className={ContentStyleForPage().header}>
                    <Typography color="textPrimary" size="large" component="h2">
                        学生周报
                    </Typography>

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

                </div>
            <br/>

                    <Typography color="textPrimary" size="small" component="h2">
                        该周未交周报的人数：{undoneCount}
                    </Typography>

                    <Typography color="textPrimary" size="small" component="h2">
                        该周未交周报名单：{getNameLine()}
                    </Typography>
            <br/>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>学生姓名</TableCell>
                                <TableCell align="center">该周周报提交时间</TableCell>
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
                                        )
                                    }</TableCell>
                                    {publication.target ?(
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
                                        {publication.target ? (
                                            <TextField
                                                select
                                                size="small"
                                                value={publication.score? publication.score: 0}
                                                variant="outlined"
                                                onChange={(e)=>{
                                                    handleScoreChange(publication,parseInt(e.target.value))
                                                }}
                                                SelectProps={{native:true}}
                                            >
                                                <option value={0}>0</option>
                                                <option value={1}>1</option>
                                                <option value={2}>2</option>
                                                <option value={3}>3</option>
                                                <option value={4}>4</option>
                                                <option value={5}>5</option>
                                            </TextField>
                                        ): "暂未上传周报"}
                                    </TableCell>
                                    <TableCell align="center">
                                        {publication.target ? (
                                            <>
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        setEditOpen(true)
                                                        setPublication(publication)
                                                    }}
                                                >{publication.comment ? "修改评语" : "添加评语"}</Button>

                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        setOpen(true)
                                                        setPublication(publication)
                                                    }}
                                                >查看周报评语</Button>
                                            </>
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
                </Box>

                {pageNo>1 && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        color="primary"
                        onChange={(e,i)=>setPage(i)}
                    />
                )}

            <EditPublicationComment open={editOpen}
                                    onClose={()=>{
                                        setEditOpen(false)
                                        setPublication({})
                                        setRefresh((p)=>!p)
                                    }}
                                    publication={publication}/>

            <PublicationComment open={open}
                                onClose={()=>{
                                    setOpen(false)
                                    setPublication({})
                                    setRefresh((p)=>!p)
                                }}
                                publication={publication}/>

            <WeeklyPublicationDialog open={publicationOpen}
                                     onClose={()=>{
                                         postFetch({
                                             url:UPDATE_WEEKLYPUBLICATION,
                                             values:{
                                                 id:publication.id,
                                                 isRead:1
                                             },
                                             successCallback:()=>{

                                             }
                                         })
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
