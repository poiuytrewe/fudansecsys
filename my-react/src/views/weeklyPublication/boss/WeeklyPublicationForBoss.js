import {BOSS_GET_PUBLICATION, UPDATE_WEEKLYPUBLICATION} from "../../../settings";
import {postFetch} from "../../../base";
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
import ContentStyle from "../../../components/Style/ContentStyle";
import Pagination from "@material-ui/lab/Pagination";
import EditPublicationComment from "../dialog/EditPublicationComment";
import PublicationComment from "../dialog/PublicationComment";
import confirmModal from "../../../components/ConfirmModal";
import WeeklyPublicationDialog from "../dialog/WeeklyPublicationDialog";

const TYPE={
    0:"硕士",
    10:"博士"
}
export default function WeeklyPublicationForBoss(){
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
    const [week, setWeek]=useState(yearNum+"-W"+(weekNum<10 ? "0"+weekNum : weekNum))
    const [refresh,setRefresh]=useState(false)
    const [page,setPage]=useState(1)
    const [pageNo,setPageNo]=useState(0)
    const [weeklyPublication,setWeeklyPublication]=useState([])
    const [open,setOpen]=useState(false)
    const [editOpen,setEditOpen]=useState(false)
    const [publication,setPublication]=useState({})
    const [publicationOpen, setPublicationOpen]=useState(false)

    const bossGetWeeklyPublication=async ({page=1, limit, week})=>{
        try {
            let response=await fetch(
                `${BOSS_GET_PUBLICATION}?week=${week}&limit=${limit}&offset=${(page-1)*limit}`);
            return response.json()
        }catch (error){
            console.log(error)
        }
    }

    const handleScoreChange=(publication,score)=>{
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

    useEffect(()=>{
        const year=parseInt(week.slice(0,4))
        const weekNum=parseInt(week.slice(6))
        bossGetWeeklyPublication({week:"第"+year+"年第"+weekNum+"周", page,limit:10}).then((res)=>{
            setWeeklyPublication(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total/10) || 0)
        })
    },[refresh, page, pageNo, week])

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        实验室学生周报
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
                </Box>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>学生姓名</TableCell>
                                <TableCell align="center">学生类型</TableCell>
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
                                    <TableCell align="center">{TYPE[publication.type]}</TableCell>
                                    <TableCell align="center">{
                                        publication.date ? (publication.date) : (
                                            "暂未上传周报"
                                        )}</TableCell>
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
                                            publication.isMentor == 1 ? (//=1代表是导师，可以评价
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
                                                ):(publication.score ? (publication.score) :(
                                                    "导师暂未评分"
                                            ))
                                        ): "暂未上传周报"}
                                    </TableCell>

                                    <TableCell align="center">
                                        {publication.target ? (
                                            publication.isMentor == 1 ? (
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
                                                </>):(
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        setOpen(true)
                                                        setPublication(publication)
                                                    }}
                                                >查看周报评语</Button>
                                            )
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
            </Card>

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
                                         setPublicationOpen(false)
                                         setPublication({})
                                         setRefresh((p)=>!p)
                                     }}
                                     weeklyPublication={publication}/>
        </div>
    )
}
