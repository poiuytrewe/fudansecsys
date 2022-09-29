import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import {
    Box,
    Button,
    Divider,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@material-ui/core";
import React, {useContext, useEffect, useState} from "react";
import {
    MNG_GET_ALL_PROCESS_CATEGORY,
    MNG_GET_TINY_PROCESS_CATEGORY,
    MNG_PROCESS_ADD, MNG_PROCESS_RESET,
    MNG_UPDATE_USER_URL
} from "../../../settings";
import { Checkbox } from '@mui/material'
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";
import alertBox from "../../../components/AlertBox";
import {UserContext} from "../../../layouts/Context";
import {useHistory} from "react-router-dom";

const STUDYTYPE={
    1:"学硕",
    2:"专硕",
    3:"学工"
}

const TYPES=[
    {
        value:1,
        name:"学术科研",
    },
    {
        value:2,
        name:"专业技术",
    },
    {
        value:3,
        name:"思政服务"
    }
]

export default function ProcessConfigForStu(props){
    const {userDetail}=props
    const {userInfo}=useContext(UserContext)
    const classes=ContentStyleForPage()
    const history=useHistory()
    const [refresh,setRefresh]=useState(false)
    const [type,setType]=useState(()=>{
        if(userDetail.keshuo!==3){
            return 1
        }
        else{
            return 3
        }
    })
    const [baseTinyCategory,setBaseTinyCategory]=useState([])//基础任务的大类别
    const [baseCategory,setBaseCategory]=useState([])//基础任务的小类别
    const [advTinyCategory,setADVTinyCategory]=useState([])
    const [advCategory,setADVCategory]=useState([])
    let advTinyCategoryList=[]

    const mngPermission= userInfo.isEducateMng===1

    const getBaseTiny=()=>{
        fetch(`${MNG_GET_TINY_PROCESS_CATEGORY}?type=${0}&studyType=${userDetail.keshuo}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setBaseTinyCategory(res?.data || [])
            })
    }

    const getBaseCategory=()=>{
        fetch(`${MNG_GET_ALL_PROCESS_CATEGORY}?type=${0}&studyType=${userDetail.keshuo}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setBaseCategory(res?.data || [])
            })
    }

    const getADVTiny=()=>{
        fetch(`${MNG_GET_TINY_PROCESS_CATEGORY}?type=${type}&studyType=${userDetail.keshuo}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setADVTinyCategory(res?.data || [])
            })
    }

    const getADVCategory=()=>{
        fetch(`${MNG_GET_ALL_PROCESS_CATEGORY}?type=${type}&studyType=${userDetail.keshuo}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setADVCategory(res?.data || [])
            })
    }

    const handleSubmit=()=>{
        if(advTinyCategoryList.length ===0){
            alertBox({text:"请至少选择一项进阶任务",severity:"error"})
            return
        }
        if(!mngPermission){
            const cor=confirmModal({
                title:"只有一次提交机会，请先和导师商量好再做决定",
                handleCorfirm:()=>{
                    cor.close()
                    let advCategoryList=[]
                    advTinyCategoryList.map((advList)=>{//遍历选中的进阶任务的大类
                        advCategory.map((category)=>{
                            if(advList.processCategory===category.processCategory){
                                advCategoryList.push(category)
                            }
                        })
                    })
                    postFetch({
                        url:MNG_PROCESS_ADD,
                        values:{
                            stuName:userDetail.name,
                            stuId:userDetail.stuId,
                            advCategoryList:advCategoryList,
                            baseCategoryList:baseCategory,
                            enrollYear:userDetail.enrollYear
                        },
                        successCallback:()=>{
                            postFetch({
                                url:MNG_UPDATE_USER_URL,
                                values:{
                                    id:userDetail.id,
                                    studyType:type,
                                },
                                successCallback:()=>{
                                    alertBox({text:"提交成功",severity:"success"})
                                    window.location.reload()
                                }
                            })
                        }
                    })
                }
            })
        }
        else{
            const cor=confirmModal({
                title:"确定要重新配置该学生的培养过程吗？",
                handleCorfirm:()=>{
                    cor.close()
                    let advCategoryList=[]
                    advTinyCategoryList.map((advList)=>{//遍历选中的进阶任务的大类
                        advCategory.map((category)=>{
                            if(advList.processCategory===category.processCategory){
                                advCategoryList.push(category)
                            }
                        })
                    })
                    postFetch({
                        url: MNG_PROCESS_RESET,
                        values:{
                            stuName:userDetail.name,
                            stuId:userDetail.stuId,
                            advCategoryList:advCategoryList,
                            enrollYear:userDetail.enrollYear
                        },
                        successCallback:()=>{
                            postFetch({
                                url:MNG_UPDATE_USER_URL,
                                values:{
                                    id:userDetail.id,
                                    studyType:type,
                                },
                                successCallback:()=>{
                                    alertBox({text:"提交成功",severity:"success"})
                                    window.location.reload()
                                }
                            })
                        }
                    })
                }
            })
        }
    }

    useEffect(()=>{
        getBaseTiny()
        getADVTiny()
        getBaseCategory()
        getADVCategory()
    },[refresh])

    return (
        <Paper>
            <div className={classes.root}>
                <div className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        基本培养任务
                    </Typography>

                    <Box>
                        <Button
                            color="primary"
                            size="small"
                            variant="outlined"
                            onClick={()=>{
                                handleSubmit()
                            }}
                        >提交</Button>

                        {mngPermission && (
                            <Button
                                color="primary"
                                size="small"
                                variant="outlined"
                                onClick={()=>{
                                    history.goBack()
                                }}>返回</Button>
                        )}
                    </Box>
                </div>
                <Divider/>
                <Paper>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>任务类别</TableCell>
                                <TableCell>任务</TableCell>
                                <TableCell align="center">预计完成时间</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {baseTinyCategory.map((baseTiny)=>(
                                <>
                                    <TableRow>
                                        <TableCell rowSpan={baseCategory.filter((category)=>{
                                            return category.processCategory===baseTiny.processCategory
                                        }).length+1}>{baseTiny.processCategory}</TableCell>
                                    </TableRow>
                                    {baseCategory.filter((category)=>{return category.processCategory===baseTiny.processCategory}).map((category)=>(
                                        <TableRow>
                                            <TableCell>{category.categoryMission}</TableCell>
                                            <TableCell align="center">
                                                {(category.month>=9 && category.month<=12)? (userDetail.enrollYear+category.year-1): (userDetail.enrollYear+category.year)}年第{category.month}月</TableCell>
                                        </TableRow>
                                    ))}
                                </>
                                )
                            )}
                        </TableBody>
                    </Table>
                </Paper>
                <Divider />
                <Box style={{height:"20px"}}/>
                <div className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        进阶任务
                    </Typography>
                    <TextField
                        select
                        label="板块类型"
                        size="small"
                        value={type}
                        onChange={(e) => {
                            setType(parseInt(e.target.value))
                            setRefresh((p)=>!p)
                        }}
                        SelectProps={{
                            native: true,
                        }}
                        variant="outlined"
                    >{(userDetail.keshuo===1 || userDetail.keshuo===2)? (
                        TYPES.filter((type)=>{
                            return type.value!==3
                        }).map((t)=>(
                            <option value={t.value}>
                                {t.name}
                            </option>
                        ))
                    ):(
                        TYPES.filter((type)=>{
                            return type.value===3
                        }).map((t)=>(
                            <option value={t.value}>
                                {t.name}
                            </option>
                        ))
                    )}
                    </TextField>
                    <Box/>
                </div>
                <Divider/>
                <Paper>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell></TableCell>
                                <TableCell>任务类别</TableCell>
                                <TableCell>任务</TableCell>
                                <TableCell align="center">预计完成时间</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {advTinyCategory.map((advTiny)=>(
                                    <>
                                        <TableRow>
                                            <TableCell rowSpan={advCategory.filter((category)=>{
                                                return category.processCategory===advTiny.processCategory
                                            }).length+1}>
                                                <Checkbox defaultChecked={false} color="primary"
                                                          onChange={(event, checked)=>{
                                                              if(checked===true){
                                                                  advTinyCategoryList.push(advTiny)
                                                              }
                                                              if(checked===false){
                                                                  advTinyCategoryList=advTinyCategoryList.filter((adv)=>{
                                                                      return adv.id!==advTiny.id
                                                                  })
                                                              }
                                                          }}
                                                />
                                            </TableCell>
                                            <TableCell rowSpan={advCategory.filter((category)=>{
                                                return category.processCategory===advTiny.processCategory
                                            }).length+1}>{advTiny.processCategory}</TableCell>
                                        </TableRow>
                                        {advCategory.filter((category)=>{return category.processCategory===advTiny.processCategory}).map((category)=>(
                                            <TableRow>
                                                <TableCell>{category.categoryMission}</TableCell>
                                                <TableCell align="center">{(category.month>=9 && category.month<=12)? (userDetail.enrollYear+category.year-1): (userDetail.enrollYear+category.year)}年第{category.month}月</TableCell>
                                            </TableRow>
                                        ))}
                                    </>
                                )
                            )}
                        </TableBody>
                    </Table>
                </Paper>
            </div>
        </Paper>
    )
}
