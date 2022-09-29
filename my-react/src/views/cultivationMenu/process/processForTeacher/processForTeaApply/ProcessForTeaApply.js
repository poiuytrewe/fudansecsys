import PageView from "../../../../../components/Style/PageView";
import {AppBar, Badge, Paper} from "@material-ui/core";
import {a11yProps, TabPanel} from "../../../score/scoreForStudent/StudentScoreView";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../../../layouts/Context";
import PropTypes from "prop-types";
import {MNG_GET_ALL_PROCESS_APPROVE, MNG_GET_ALL_PROCESS_DELAY} from "../../../../../settings";
import ProcessForTeaApplyAgree from "./ProcessForTeaApplyAgree";
import ProcessForTeaApplyDelay from "./ProcessForTeaApplyDelay";
import {Tab, Tabs} from "@mui/material";

export default function ProcessForTeaApply(props){
    const {onRefresh}=props
    const classes=PageView()
    const [value,setValue]=useState(0)
    const [count1,setCount1]=useState(0)
    const [count2,setCount2]=useState(0)
    const [refresh,setRefresh]=useState(false)
    const {userInfo}=useContext(UserContext)

    const getAllApprove=()=>{//获取该导师手下的所有已提交任务
        fetch(`${MNG_GET_ALL_PROCESS_APPROVE}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setCount1(res?.data.length || 0)
            })
    }

    const getAllDelay=()=>{
        fetch(`${MNG_GET_ALL_PROCESS_DELAY}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setCount2(res?.data.length || 0)
            })
    }

    useEffect(()=>{//两个都是获取数量即可
        getAllApprove()
        getAllDelay()
    },[refresh])

    return (
        <Paper className={classes.root}>
            <AppBar position="static" color="secondary">
                <Tabs value={value} onChange={(e,newValue)=>{
                    setValue(newValue)
                }} indicatorColor="primary" textColor="primary">
                    <Tab label="学生任务完成申请" iconPosition="end" {...a11yProps(0)} icon={
                        <Badge color="error" badgeContent={count1} max={99} />
                    }/>
                    <Tab label="学生任务延期申请" iconPosition="end" {...a11yProps(1)} icon={
                        <Badge color="error" badgeContent={count2} max={99} />
                    }/>
                </Tabs>
            </AppBar>
            <TabPanel index={0} value={value}>
                <ProcessForTeaApplyAgree length={count1} onRefresh={()=>{
                    onRefresh()
                    setRefresh((p)=>!p)
                }}/>
            </TabPanel>
            <TabPanel index={1} value={value}>
                <ProcessForTeaApplyDelay length={count2} onRefresh={()=>{
                    onRefresh()
                    setRefresh((p)=>!p)
                }}/>
            </TabPanel>
        </Paper>
    )
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};
