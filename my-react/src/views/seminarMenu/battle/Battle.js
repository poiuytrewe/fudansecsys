import ContentStyle from "../../../components/Style/ContentStyle";
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
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../layouts/Context";
import {MinusSquareOutlined, PlusSquareOutlined} from "@ant-design/icons";
import {getAllLeader, getAllRecorder} from "../../../service/userService";
import alertBox from "../../../components/AlertBox";
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";
import {ADD_SEMINAR_URL, GET_ALL_SEMINAR_URL, MNG_ADD_BULLETIN_URL, UPDATE_BATTLE__URL} from "../../../settings";



function ascCompare(property){//从小到大排序
    return function (a,b){
        let value1=a[property]
        let value2=b[property]
        return value1-value2
    }
}

export default function Battle(){
    const classes=ContentStyle()
    const {userInfo}=useContext(UserContext)
    const [recorder,setRecorder]=useState([]) //recorder中包含了所有的东西，包括组名和基础分数，所以直接用recorder就行
    const [battlePermission,setBattlePermission]=useState(true) //默认一开始都是可以评分的
    const [refresh,setRefresh]=useState(false)
    const [defend,setDefend]=useState("") //守擂的组号

    const getSeminar=()=>{ //上次讲过论文的组这周守擂，这个方法的目的是获取上次讲论文的那个组
        fetch(`${GET_ALL_SEMINAR_URL}?limit=2`)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((response)=>{
                setDefend(response?.data[1]?.groupId)
            })
    }

    const handleAdd=(rec)=>{ //处理加分情况
        let newRecorder=[]
        recorder.map((re)=>{
            if(re.id==rec.id){ //如果两个的id值相等
                re.baseScore++
                newRecorder.push(re)
            }
            else {//两个id不等，直接push进去
                newRecorder.push(re)
            }
        })
        setRecorder(newRecorder)
    }

    const handleSub=(rec)=>{ //处理减分情况
        let newRecorder=[]
        recorder.map((re)=>{
            if(re.id==rec.id){ //如果两个的id值相等
                re.baseScore--
                newRecorder.push(re)
            }
            else {//两个id不等，直接push进去
                newRecorder.push(re)
            }
        })
        setRecorder(newRecorder)
    }

    const handleSubmit=()=>{ //将battle的分数提交
        if(userInfo.status!=1){ //如果用户不是博士，无法进行评分，这个在后面会改
            alertBox({text:"无权进行battle评分",severity:"error"})
            return
        }
        if(!battlePermission){ //如果这一轮已经评分过了，就不会重复提交
            alertBox({text:"这一轮已经评分已经结束了",severity:"error"})
            return
        }
        const cor=confirmModal({
            title:"确定要提交battle评分吗？确定之后不可修改",
            handleCorfirm:()=>{
                cor.close()
                const newRecorder=recorder.filter((rec)=>{ //过滤掉守擂组的所有组
                    return rec.groupId!=parseInt(defend)
                })
                newRecorder.sort(ascCompare("baseScore"))//将recorder内容按照baeScore从小到大排序
                postFetch({//增加演讲安排
                    url:ADD_SEMINAR_URL,
                    values:{
                        groupId:newRecorder[0].groupId
                    }
                })
                postFetch({ //设置这一组battle为已经battle过
                    url:UPDATE_BATTLE__URL,
                    values:{
                        id:recorder[0].id
                    },
                    successCallback:()=>{
                        alertBox({text:"评分成功",severity:"success"})
                        setRefresh((prev)=>!prev)
                    }
                })
                getAllLeader().then((res)=>{ //向输了的组的组长发送通知
                    postFetch({
                        url:MNG_ADD_BULLETIN_URL,
                        values:{
                            stuId:res?.data.find((leader)=>leader.groupId==newRecorder[0].groupId).stuId,
                            title:"下周论文论文演讲组为["+newRecorder[0].groupId+"]组",
                            content:"很遗憾，下周的论文需要由你们组准备，请及时选出论文演讲人员"
                        }
                    })
                })
            }
        })
    }

    useEffect(()=>{
        getAllRecorder({limit:4}).then((res)=>{
            if(res?.data[0].isBattle==1){//如果isBattle的值为0，就标明还没有进行过battle，那么就可以上传battle分数，反之则不能
                //可行
                setBattlePermission(false)
            }
            setRecorder(res?.data || [])
        })
        getSeminar()
    },[refresh])

    return　(
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        battle计分
                    </Typography>

                    {userInfo.status==1 && <Typography color="textPrimary" size="small" component="h2">
                        守擂组：
                        <TextField
                            value={defend}
                            onChange={(e)=>{
                                setDefend(e.target.value)
                            }}
                        >
                            {defend}
                        </TextField>
                    </Typography>
                    }


                    {(userInfo.status==1 &&recorder!=[]) &&(
                        <Button
                            color="primary"
                            size="large"
                            variant="outlined"
                            onClick={()=>{
                                handleSubmit()
                            }}
                        >提交</Button>
                    )}
                </Box>

                <Divider/>

                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>组号</TableCell>
                                <TableCell align="center">分数</TableCell>
                                <TableCell align="center">操作</TableCell>
                            </TableRow>
                        </TableHead>

                        <Divider/>

                        {userInfo.status==1 && (
                            <TableBody>
                                {recorder.map((rec)=>(
                                    <TableRow>
                                        <TableCell>{rec.groupId}</TableCell>
                                        <TableCell align="center">{rec.baseScore}</TableCell>
                                        <TableCell align="center">
                                            <Button
                                                color="primary"
                                                size="large"
                                                variant="text"
                                                onClick={()=>{
                                                    handleAdd(rec)
                                                }}
                                            ><PlusSquareOutlined /></Button>

                                            <Button
                                                color="primary"
                                                size="large"
                                                variant="text"
                                                onClick={()=>{
                                                    handleSub(rec)
                                                }}
                                            ><MinusSquareOutlined /></Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        )}
                    </Table>
                </Box>

            </Card>

        </div>
    )
}
