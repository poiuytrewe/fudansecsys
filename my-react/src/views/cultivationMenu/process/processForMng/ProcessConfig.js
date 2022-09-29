import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";
import {
    Button,
    Divider, Fab,
    Paper,
    TextField, Tooltip,
} from "@material-ui/core";
import React, {useContext, useEffect, useState} from "react";
import {
    MNG_DELETE_PROCESS_CATEGORY,
    MNG_GET_ALL_PROCESS_CATEGORY,
    MNG_GET_TINY_PROCESS_CATEGORY,
    MNG_PROCESS_ADD,
    MNG_RECONFIG_PROCESS,
    MNG_RESET_PROCESS_CATEGORY,
    MNG_UPDATE_USER_URL
} from "../../../../settings";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Radio from "@mui/material/Radio";
import AddIcon from "@mui/icons-material/Add";
import {UserContext} from "../../../../layouts/Context";
import ProcessTinyCategoryAddForm from "../processForm/ProcessTinyCategoryAddForm";
import {Table, TableBody, TableCell, TableHead, TableRow} from "@mui/material";
import ProcessAddForm from "../processForm/ProcessAddForm";
import confirmModal from "../../../../components/ConfirmModal";

import alertBox from "../../../../components/AlertBox";
import {useHistory} from "react-router-dom";
import {postFetch} from "../../../../base";



const STUDYTYPE={
    1:"学硕",
    2:"专硕",
    3:"学工"
}

export default function ProcessConfig(props){//这个组件有两个作用，一是管理员配置所有的任务，二是管理员为某个学生重新选择任务
    //学生选择任务也是在这个地方
    //所以，如果userDetail和userStuId都为null，说明是管理员来配置任务
    //如果userDetail不为null，userStuId为null，说明是学生来选择
    //如果userDetail和userStuId都不为null，说明是管理员为某个学生重新选择任务
    {/**由于所有的普通任务不分模块，所以**/}
    const {userDetail, userStuId}=props
    const classes=ContentStyleForPage()
    const history=useHistory()
    const {userInfo}=useContext(UserContext)
    const type=0 //type对应的是板块，因为基础任务没有板块，所以是0
    const [studyType,setStudyType]=useState(1)//studyType是对应的硕士类型，1代表学硕，2代表专硕，3代表学工
    const [allCategory,setAllCategory]=useState([])
    const [refresh,setRefresh]=useState(false)
    const [tinyCategoryAdd,setTinyCategoryAdd]=useState(false)
    const [categoryAdd,setCategoryAdd]=useState(false)
    const [tinyCategory,setTinyCategory]=useState("")
    const [allTinyCategory,setAllTinyCategory]=useState([])
    const [categoryDetail,setCategoryDetail]=useState({})

    const mngPermission= userInfo.isEducateMng===1//是否拥有管理员权限
    const configPermission= typeof(userDetail)==="undefined" && typeof(userStuId)==="undefined" //管理员配置任务
    const stuPermission= typeof(userDetail)!=="undefined" && typeof(userStuId)==="undefined" //学生选择任务
    const configForStuPermission =typeof(userDetail)!=="undefined" && typeof(userStuId)!=="undefined"//管理员为学生重新配置任务

    const getAllProcessCategory=()=>{//这里是某一类型学生对应基础任务的所有小类别
        fetch(`${MNG_GET_ALL_PROCESS_CATEGORY}?type=${type}&studyType=${studyType}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setAllCategory(res?.data || [])
            })
    }

    const getAllTintProcessCategory=()=>{//这里是某一硕士类型学生对应基础任务的所有大类
        fetch(`${MNG_GET_TINY_PROCESS_CATEGORY}?type=${type}&studyType=${studyType}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setAllTinyCategory(res?.data || [])
            })
    }

    const handleDelete=(id)=>{
        const cor=confirmModal({
            title:"确定要删除这一条任务吗",
            handleCorfirm:()=>{
                cor.close()
                fetch(`${MNG_DELETE_PROCESS_CATEGORY}?id=${id}`,{method:"POST"})
                    .then(()=>{
                        alertBox({text:"删除成功",severity:"success"})
                        setRefresh((p)=>!p)
                    })
            }
        })
    }

    const handleSubmit=()=>{//提交初始化请求
        if(!mngPermission){//没有管理员权限，说明是自己提交的
            const cor=confirmModal({
                title:"确认要提交吗？只能提交一次并且没有修改机会，请与导师商量后再做选择",
                handleCorfirm:()=>{
                    cor.close()
                    if(allCategory.length===0){
                        alertBox({text:"还未添加任务，请等待",severity:"error"})
                        return
                    }
                    postFetch({
                        url:MNG_UPDATE_USER_URL,
                        values:{
                            id:userDetail.id,
                            // studyType:type,
                            keshuo:studyType //keshuo代表硕士类型，1代表学硕，2代表专硕，3代表学工
                        },
                        successCallback:()=>{
                            postFetch({
                                url:MNG_PROCESS_ADD,
                                values:{
                                    stuName:userDetail.name,
                                    stuId:userDetail.stuId,
                                    categoryList:allCategory,
                                    enrollYear:userDetail.enrollYear
                                },
                                successCallback:()=>{
                                    alertBox({text:"选择成功",severity:"success"})
                                    window.location.reload()
                                }
                            })
                        }
                    })
                }
            })
        }
        else{//管理员
            if (userDetail.studyType===type && userDetail.keshuo===studyType){
                alertBox({text:"重复选择同一个类型",severity:"error"})
                return
            }
            const cor=confirmModal({
                title:"确定要重新配置这个学生的培养过程吗？",
                handleCorfirm:()=>{
                    cor.close()
                    postFetch({//第一步，修改他本人的信息
                        url:MNG_UPDATE_USER_URL,
                        values:{
                            id:userDetail.id,
                            studyType:type, //studyType代表对应的硕士小类型，如学术型，专业型
                            keshuo:studyType
                        },
                        successCallback:()=>{
                            postFetch({//第二步，将原先配置的任务，除去已提交的和未完成的，都删除掉
                                url:MNG_RECONFIG_PROCESS,
                                values:{
                                    stuId:userDetail.stuId
                                },
                                successCallback:()=>{
                                    postFetch({//第三步，把新配置的任务给他装好
                                        url:MNG_PROCESS_ADD,
                                        values:{
                                            stuName:userDetail.name,
                                            stuId:userDetail.stuId,
                                            categoryList:allCategory,
                                            enrollYear:userDetail.enrollYear
                                        },
                                        successCallback:()=>{
                                            alertBox({text:"选择成功",severity:"success"})
                                            setRefresh((p)=>!p)
                                        }
                                    })
                                }
                            })
                        }
                    })
                }
            })
        }
    }

    const handleReset=()=>{
        const cor=confirmModal({
            title:"确定要重置"+STUDYTYPE[studyType]
            +"类型的所有基础任务吗？",
            handleCorfirm:()=>{
                cor.close()
                fetch(`${MNG_RESET_PROCESS_CATEGORY}?type=${type}&studyType=${studyType}`,{method:"POST"})
                    .then(()=>{
                        alertBox({text:"重置成功",severity:"success"})
                        setRefresh((p)=>!p)
                    })
            }
        })
    }

    useEffect(()=>{
        getAllTintProcessCategory()
        getAllProcessCategory()
    },[refresh])

    return (
        <Paper>
        <div className={classes.root}>
            <div className={classes.header}>
                <FormControl>
                    <FormLabel id="demo-radio-buttons-group-label">请选择配置任务对应的学生身份</FormLabel>
                    <RadioGroup
                        row
                        aria-labelledby="demo-radio-buttons-group-label"
                        defaultValue={studyType}
                        name="radio-buttons-group"
                        onChange={(event, value) => {
                            setStudyType(parseInt(value))
                            setRefresh((p)=>!p)
                        }}
                    >
                        <FormControlLabel value={1} control={<Radio/>} label="学硕"/>
                        <FormControlLabel value={2} control={<Radio/>} label="专硕"/>
                        <FormControlLabel value={3} control={<Radio/>} label="学工"/>
                    </RadioGroup>
                </FormControl>

                {(mngPermission && configForStuPermission) && (//拥有管理员权限，并且stuId不为空
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{
                            handleSubmit()
                        }}>确认提交</Button>
                )}

                {stuPermission && (
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{
                            handleSubmit()
                        }}>确认提交</Button>
                )}

                {mngPermission && (//是管理员，要离开
                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={()=>{
                            history.goBack()
                        }}>返回</Button>
                )}


            </div>

            <Divider/>
            {(mngPermission && configPermission) && (
                <Tooltip title="点击增加新任务类别">
                <Fab color="primary" aria-label="add" onClick={()=>{
                    setTinyCategoryAdd(true)
            }} >
                <AddIcon/>
                </Fab>
                </Tooltip>
                )}


            {(mngPermission && configPermission) && (
                <Button
                    style={{
                        float:"right"
                    }}
                    color="primary"
                    size="medium"
                    variant="outlined"
                    onClick={()=>{
                        handleReset()
                    }}>重置任务</Button>
            )}
            <Paper>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>任务类别</TableCell>
                            <TableCell>任务</TableCell>
                            <TableCell align="center">预计完成时间</TableCell>
                            {(mngPermission && configPermission) && (
                                <TableCell align="center">操作</TableCell>
                            )}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {allTinyCategory.map((tinyCategory)=>(
                            <>
                                <TableRow>
                                    <TableCell rowSpan={(mngPermission && configPermission)? (allCategory.filter((category)=>{
                                        return category.processCategory===tinyCategory.processCategory
                                    }).length+2):(allCategory.filter((category)=>{
                                            return category.processCategory===tinyCategory.processCategory
                                        }).length+1)}>
                                        {tinyCategory.processCategory}
                                    </TableCell>
                                </TableRow>
                                {/**以上是大的类别**/}
                                {allCategory.filter((category)=>{return category.processCategory===tinyCategory.processCategory}).map((category)=>(
                                    <TableRow>
                                        <TableCell>{category.categoryMission}</TableCell>
                                        <TableCell align="center">第{category.year}学年{category.month}月份</TableCell>
                                        {(mngPermission && configPermission)&& (
                                            <TableCell align="center">
                                                <Button
                                                    color="primary"
                                                    variant="text"
                                                    size="small"
                                                    onClick={()=>{
                                                        setCategoryAdd(true)
                                                        setTinyCategory(tinyCategory.processCategory)
                                                        setCategoryDetail(category)
                                                    }}>修改</Button>
                                                <Button
                                                    color="primary"
                                                    variant="text"
                                                    size="small"
                                                    onClick={()=>{handleDelete(category.id)}}
                                                >删除</Button>
                                            </TableCell>
                                        )}
                                    </TableRow>
                                ))}
                                {(mngPermission && configPermission) && (
                                    <TableRow>
                                        <TableCell>
                                            <Tooltip title="点击添加任务内容">
                                                <Fab size="small" color="primary" aria-label="add" onClick={()=>{
                                                    setCategoryAdd(true)
                                                    setTinyCategory(tinyCategory.processCategory)
                                                }}><AddIcon/>
                                                </Fab></Tooltip>
                                        </TableCell>
                                        <TableCell align="center"></TableCell>
                                        <TableCell align="center"></TableCell>
                                    </TableRow>
                                )}
                            </>))}
                    </TableBody>
                </Table>
            </Paper>

            <ProcessTinyCategoryAddForm open={tinyCategoryAdd}
                                        type={type}
                                        studyType={studyType}
                                        onClose={()=>{
                                            setTinyCategoryAdd(false)
                                            setRefresh((p)=>!p)
                                        }}
            />
            <ProcessAddForm open={categoryAdd}
                            type={type}
                            studyType={studyType}
                            tinyCategory={tinyCategory}
                            categoryDetail={categoryDetail}
                            onClose={()=>{
                                setCategoryAdd(false)
                                setCategoryDetail({})
                                setRefresh((p)=>!p)
                            }} />
        </div>
        </Paper>
    )
}
