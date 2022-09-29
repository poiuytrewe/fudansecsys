import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../layouts/Context";
import {
    GET_SCORE_UNDONELIST,
    MNG_GET_SCORE_DATE,
    MNG_RETURN_SINGLESCORE,
    MNG_UPDATE_SCORE
} from "../../../../settings";
import {
    Box, Button,
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
import ContentStyle from "../../../../components/Style/ContentStyle";
import confirmModal from "../../../../components/ConfirmModal";
import {postFetch} from "../../../../base";
import alertBox from "../../../../components/AlertBox";
import ScoreStudentComment from "../scoreForm/ScoreStudentComment";
import TeaCommentForTeaForm from "../scoreForm/TeaCommentForTeaForm";
import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";

const Role={
    0: "硕士",
    10: "博士"
}

export default function ScoreForTea(){
    const classes = ContentStyle()
    const [page, setPage] = useState(1);
    const [pageNo, setPageNo] = useState(0);
    const {userInfo}=useContext(UserContext);
    const [date,setDate]=useState([]); //所有的已经评分的年份和季度
    const [nowDate,setNowDate]=useState(0);//当前查看的评分年份和季度的数组下标，所以默认是0
    const [scoreData,setScoreData]=useState([])
    const [refresh,setRefresh]=useState(false)
    const [scoreDetail, setScoreDetail]=useState({})
    const [commentOpen, setCommentOpen]=useState(false)
    const [teaCommentOpenForTea,setTeaCommentOpenForTea]=useState(false)
    const [undoneList, setUndoneList] = useState([])

    const getUserLastScore = async ({name=userInfo.name, page, limit=10, year, season, sort=0})=>{
        try{
            let response = await  fetch(
                `${MNG_RETURN_SINGLESCORE}?limit=${limit}&offset=${(page-1)*limit}&name=${name}&year=${year}&season=${season}&sort=${sort}`)
            return await response.json()
        }catch (error){
            console.error(error)
        }
    }

    const getDate=async ()=>{//获取已经评分过的年份和季度
        try {
            let response=await fetch(MNG_GET_SCORE_DATE,{})
            return await response.json()
        }catch (error){
            console.log(error)
        }
    }

    const getUndoneList=({year, season, mentor})=>{
        fetch(`${GET_SCORE_UNDONELIST}?year=${year}&season=${season}&mentor=${mentor}`,{method:"GET"})
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setUndoneList(res?.data || [])
            })
    }

    const getNameLine=()=>{
        let nameLine=""
        undoneList.map((l,index)=>{
            let s= index==0 ? l : "，"+l
            nameLine=nameLine+s
        })
        return nameLine
    }

    useEffect(()=>{
        getDate().then((res)=>{
            setDate(res?.data || [])
            getUserLastScore({page, year:res.data[nowDate].year, season:res.data[nowDate].season}).then((res)=>{
                setScoreData(res?.data || [])
                setPageNo(Math.ceil(res?.paging?.total / 10) || 0)
            })
            getUndoneList({year:res.data[nowDate].year, season:res.data[nowDate].season, mentor:userInfo.name})
        })


    },[])

    useEffect(()=>{
        getUserLastScore({page, year:date[nowDate]?.year, season:date[nowDate]?.season}).then((res)=>{
            setScoreData(res?.data || [])
        })
    },[page, refresh])

    useEffect(()=>{
        getUserLastScore({page:1, year:date[nowDate]?.year, season:date[nowDate]?.season}).then((res)=>{
            setScoreData(res?.data || [])
            setPageNo(Math.ceil(res?.paging?.total / 10) || 0)
            setPage(1)
        })
    },[nowDate])

    useEffect(()=>{
        getUndoneList({year:date[nowDate]?.year, season:date[nowDate]?.season, mentor:userInfo.name})
    },[nowDate])

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

    return (
        <div className={ContentStyleForPage().root}>
            <div className={ContentStyleForPage().header}>
                {date.length===0?(
                    <Typography color="textPrimary" size="small">
                        请先让学生完成自评
                    </Typography>
                ):(
                    <>
                        <Typography color="textPrimary" size="small">
                            季度评分
                        </Typography>

                        <TextField
                            size="medium"
                            select
                            variant="outlined"
                            value={nowDate}
                            onChange={(e)=>{
                                setNowDate(parseInt(e.target.value))
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
                    </>
                )}
            </div>

            <br/>

            <Typography color="textPrimary" size="small" component="h2">
                未完成本季度自评的人员名单：  {getNameLine()}
            </Typography>

            <br/>

            <Divider/>

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
                                        </TableCell>

                                        <TableCell align="center">
                                            {data.teaComment ? (
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        setTeaCommentOpenForTea(true)
                                                        setScoreDetail(data)
                                                    }}
                                                >修改导师评价</Button>
                                            ): (
                                                <Button
                                                    color="primary"
                                                    size="small"
                                                    variant="text"
                                                    onClick={()=>{
                                                        setTeaCommentOpenForTea(true)
                                                        setScoreDetail(data)
                                                    }}
                                                >添加导师评价</Button>
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
                    </Table>
                )}
                {(pageNo > 1 && date.length!==0) && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        page={page}
                        color="primary"
                        onChange={(e, i) => {
                            setPage(i)
                        }}
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
        </div>);
}
