import React, { useState, useEffect} from "react";
import {
    Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography, TextField, Button,
} from "@material-ui/core";
import Pagination from "@material-ui/lab/Pagination";

import ContentStyle from "../../../../components/Style/ContentStyle";
import {
    MNG_GET_ALL_USER,
    MNG_RETURN_SINGLESCORE,
    MNG_GET_SCORE_DATE,
    MNG_UPDATE_SCORE
} from "../../../../settings";
import {Box} from "@mui/material";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import alertBox from "../../../../components/AlertBox";
import ScoreStudentComment from "../scoreForm/ScoreStudentComment";
import TeaCommentForTeaForm from "../scoreForm/TeaCommentForTeaForm";
import TeaCommentForm from "../scoreForm/TeaCommentForm";

const Role={
    0: "硕士",
    10: "博士"
}

const Sort=[
    {
        index: 0,
        value: "默认"
    },
    {
        index: 1,
        value: "评分从低到高"
    },
    {
        index: 2,
        value: "评分从高到低"
    }
]

export default function BossScore(){ //给大老板看的
    const [allUsers, setAllUsers] =useState([]);
    const classes = ContentStyle();
    const [page, setPage] = useState(1);
    const [pageNo, setPageNo] = useState(0);
    const [date,setDate]=useState([]); //所有的已经评分的年份和季度
    const [nowDate,setNowDate]=useState(0);//当前查看的评分年份和季度的数组下标，所以默认是0
    const [teacherName,setTeacherName]=useState("")//查找导师的名称
    const [sortNum,setSortNum]=useState(0); //排序方式
    const [scoreData, setScoreData]=useState([])
    const [refresh, setRefresh]=useState(false)
    const [scoreDetail, setScoreDetail]=useState({})
    const [commentOpen, setCommentOpen]=useState(false)
    const [teaCommentOpenForTea,setTeaCommentOpenForTea]=useState(false)
    const [teaCommentOpen,setTeaCommentOpen]=useState(false)

    const getUserLastScore= async ({name, page,limit=10, year, season, sort=sortNum})=>{
        //返回某个老师名下的所有学生的近期一次的成绩
        try{
            let response=await fetch(
                `${MNG_RETURN_SINGLESCORE}?limit=${limit}&offset=${(page-1)*limit}&name=${name}&year=${year}&season=${season}&sort=${sort}`)
            return await response.json();
        }catch (error){
            console.log(error)
        }
    }

    const getAllTeacher=async ()=>{//获取所有的教师
        try{
            let response=await fetch(`${MNG_GET_ALL_USER}?type=${20}`,{})
            return await response.json()
        }catch (error){
            console.error(error)
        }
    }

    const getDate=async ()=>{
        try {
            let response=await fetch(MNG_GET_SCORE_DATE,{})
            return await response.json()
        }catch (error){
            console.log(error)
        }
    }

    const handleDateChange=(value)=>{
        setNowDate(value)//重新设置数组下表
        //接下来应该将请求的信息发送到后台
    }

    const handleTeacherChanged=(tName)=>{ //所属教师改变之后
        setTeacherName(tName)
    }

    const handleSortChanged=(sort)=>{ //排序方式改变之后
        setSortNum(sort)
    }

    const handleScoreChange=(data, newScore)=>{
        const cor = confirmModal({
            title:"确定要修改评分吗？",
            handleCorfirm:()=>{
                cor.close()
                postFetch({
                    url:MNG_UPDATE_SCORE,
                    values:{
                        id: data.id,
                        score: newScore
                    },
                    successCallback:()=>{
                        alertBox({text:"修改成功",severity:"success"})
                        setRefresh((p)=>!p)
                    }
                })
            }
        })
    }

    useEffect(()=>{
        getAllTeacher().then((res)=>{ //获取所有的教师数据
            setAllUsers(res.data || [])//写入教师数据
        })
        getDate().then((res)=>{ //获取已经打过分的年份季度
            setDate(res.data || [])
            getUserLastScore({name:teacherName, page, year:res.data[nowDate].year, season:res.data[nowDate].season, sort:sortNum}).then((res)=>{
                setScoreData(res?.data || [])
                setPageNo(Math.ceil(res?.paging?.total / 10) || 0)
            })
        })
    },[])

    useEffect(()=>{
        getUserLastScore({name:teacherName, page:1, year: date[nowDate]?.year, season:date[nowDate]?.season, sort:sortNum}).then((res)=>{
            setScoreData(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total / 10) || 0)
            setPage(1)
        })
    },[nowDate, teacherName])

    useEffect(()=>{
        getUserLastScore({name:teacherName, page, year: date[nowDate]?.year, season:date[nowDate]?.season, sort:sortNum}).then((res)=>{
            setScoreData(res?.data || [])
        })
    },[refresh, sortNum, page])

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    {date.length===0? (
                        <Typography color="textPrimary" size="small">
                            请先让学生完成自评
                        </Typography>
                    ):(<>
                            <Typography color="textPrimary" size="small">
                                学生季度评分
                            </Typography>

                            <Box>
                                <Typography color="textPrimary" size="small" align="center">
                                    所属导师
                                </Typography>
                                <TextField
                                    size="small"
                                    select
                                    variant="outlined"
                                    value={teacherName}
                                    onChange={(event)=>{
                                        handleTeacherChanged(event.target.value)
                                    }}
                                    SelectProps={{
                                        native:true
                                    }}
                                >
                                    <option value="">所有导师</option>
                                    {allUsers.map((user)=>(
                                        <option value={user.name}>
                                            {user.name}
                                        </option>
                                    ))}
                                </TextField>
                            </Box>

                            <Box>
                                <Typography color="textPrimary" size="small" align="center">
                                    季度
                                </Typography>

                                <TextField
                                    size="small"
                                    select
                                    variant="outlined"
                                    value={nowDate}
                                    onChange={(e)=>{
                                        handleDateChange(e.target.value)
                                    }}
                                    SelectProps={{
                                        native:true
                                    }}
                                >
                                    {date.map((d,index)=>(
                                        //因为d是一个具体的对象，不能直接把它赋给value，所以我们换个思路，将数组下标赋值给value
                                        <option value={index}>
                                            {d.year}年第{d.season}季
                                        </option>
                                    ))}
                                </TextField>
                            </Box>

                            <Box>
                                <Typography color="textPrimary" size="small" align="center">
                                    排序方式
                                </Typography>
                                <TextField
                                    select
                                    size="small"
                                    color="primary"
                                    variant="outlined"
                                    value={sortNum}
                                    SelectProps={{
                                        native:true
                                    }}
                                    onChange={(event)=>{
                                        handleSortChanged(parseInt(event.target.value))
                                    }}
                                >{Sort.map((s)=>(
                                    <option value={s.index}>
                                        {s.value}
                                    </option>
                                ))}
                                </TextField>
                            </Box>
                        </>
                    )}
                </Box>

                <Divider />

                <Box minWidth={800}>
                    {date.length!==0 && (
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>姓名</TableCell>
                                    <TableCell align="center">学/工号</TableCell>
                                    <TableCell align="center">类型</TableCell>
                                    <TableCell align="center">季度自评</TableCell>
                                    <TableCell align="center">季度评分</TableCell>
                                    <TableCell align="center">导师评语</TableCell>
                                    <TableCell align="center">操作</TableCell>
                                </TableRow>
                            </TableHead>

                            {scoreData.length!==0 && (
                                <TableBody>
                                    { scoreData.map((data,index) => {
                                        return (
                                            <TableRow hover key={data.id}>
                                                <TableCell>{data.stuName}</TableCell>
                                                <TableCell align="center">{data.stuId}</TableCell>
                                                <TableCell align="center">{Role[data.stuType]}</TableCell>
                                                <TableCell align="center">
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        onClick={()=>{
                                                            setScoreDetail(data)
                                                            setCommentOpen(true)
                                                        }}>查看学生自评</Button>
                                                </TableCell>
                                                <TableCell align="center">
                                                    {data.mentor == "杨珉"? (
                                                        <TextField
                                                            select
                                                            size="small"
                                                            value={data.score}
                                                            variant="outlined"
                                                            onChange={(e)=>{
                                                                handleScoreChange(data, parseInt(e.target.value))
                                                            }}
                                                            SelectProps={{native:true}}
                                                            >
                                                            <option key={0} value={0}>0</option>
                                                            <option key={1} value={1}>1</option>
                                                            <option key={2} value={2}>2</option>
                                                            <option key={3} value={3}>3</option>
                                                            <option key={4} value={4}>4</option>
                                                            <option key={5} value={5}>5</option>
                                                        </TextField>
                                                    ):(
                                                        data.score
                                                    )}
                                                </TableCell>

                                                <TableCell align="center">
                                                    {data.mentor == "杨珉" ? (
                                                        data.teaComment ? (
                                                            <Button
                                                                color="primary"
                                                                size="small"
                                                                variant="text"
                                                                onClick={()=>{
                                                                    setTeaCommentOpenForTea(true)
                                                                    setScoreDetail(data)
                                                                }}
                                                            >修改导师评价</Button>
                                                        ):(
                                                            <Button
                                                                color="primary"
                                                                size="small"
                                                                variant="text"
                                                                onClick={()=>{
                                                                    setTeaCommentOpenForTea(true)
                                                                    setScoreDetail(data)
                                                                }}
                                                            >添加导师评价</Button>
                                                        )
                                                    ):(
                                                        <Button
                                                            color="primary"
                                                            size="small"
                                                            variant="text"
                                                            onClick={()=>{
                                                                setTeaCommentOpen(true)
                                                                setScoreDetail(data)
                                                            }}>查看导师评价</Button>
                                                    )}
                                                </TableCell>
                                                <TableCell align="center">
                                                    <Button
                                                        color="primary"
                                                        size="small"
                                                        variant="text"
                                                        href={`/app/studyPlan/stuScore/${data.stuId}`}
                                                    >查看季度评分表
                                                    </Button>
                                                </TableCell>
                                            </TableRow>
                                        )
                                    })}
                                </TableBody>
                            )}
                        </Table>
                    )}
                    {(date.length!==0 && pageNo > 1) && (
                        <Pagination
                            className={classes.Pagination}
                            count={pageNo}
                            color="primary"
                            page={page}
                            onChange={(e, i) => {
                                setPage(i)}}
                        />
                    )}
                </Box>

                <ScoreStudentComment open={commentOpen}
                                     onClose={()=>{
                                         setCommentOpen(false)
                                         setRefresh((p)=>!p)
                                         setScoreDetail({})
                                     }}
                                     scoreDetail={scoreDetail}
                />

                <TeaCommentForTeaForm open={teaCommentOpenForTea}
                                      onClose={()=>{
                                          setTeaCommentOpenForTea(false)
                                          setRefresh((p)=>!p)
                                          setScoreDetail({})
                                      }}
                                      scoreDetail={scoreDetail}/>

                <TeaCommentForm open={teaCommentOpen}
                                onClose={()=>{
                                    setTeaCommentOpen(false)
                                    setRefresh((p)=>!p)
                                    setScoreDetail({})
                                }}
                                scoreDetail={scoreDetail}/>
            </Card>
        </div>
    );
};
