import Page from "../../../../components/Page";
import {AppBar, Badge, IconButton, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import PageView from "../../../../components/Style/PageView";
import React, {useContext, useEffect, useState} from "react";
import {a11yProps, TabPanel} from "../../score/scoreForStudent/StudentScoreView";
import PropTypes from "prop-types";
import {UserContext} from "../../../../layouts/Context";
import ProcessForTea from "./ProcessForTea";
import {MNG_GET_PROCESS_APPLY_COUNT} from "../../../../settings";
import ProcessForTeaApply from "./processForTeaApply/ProcessForTeaApply";
import GraduateInformation from "./GraduateInformation";

;
const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3)
    },
}));

export default function ProcessForTeaView(){
    const classes=PageView()
    const twoClasses=useStyles()
    const {userInfo}=useContext(UserContext)
    const [value,setValue]=useState(0)
    const [count,setCount]=useState(0)
    const [refresh,setRefresh]=useState(false)

    useEffect(()=>{
        fetch(`${MNG_GET_PROCESS_APPLY_COUNT}?name=${userInfo.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setCount(res?.data || 0)
            })
    },[refresh])

    return (
        <Page className={twoClasses.root} title="学生培养过程">
            <Paper className={classes.root}>
                <AppBar position="static" color="secondary">
                    <Tabs value={value} onChange={(e,newValue)=>{
                        setValue(newValue)
                    }} indicatorColor="primary" textColor="primary">
                        <Tab label="学生培养过程管理" {...a11yProps(0)}/>
                        <Tab label="毕业相关信息" {...a11yProps(1)}/>
                        <Tab label="学生任务申请管理" {...a11yProps(2)}/>
                        <IconButton>
                            <Badge color="error" badgeContent={count} max={99}>
                            </Badge>
                        </IconButton>
                    </Tabs>
                </AppBar>
                <TabPanel index={0} value={value}>
                    <ProcessForTea/>
                </TabPanel>
                <TabPanel index={1} value={value}>
                    <GraduateInformation/>
                </TabPanel>
                <TabPanel index={2} value={value}>
                    <ProcessForTeaApply onRefresh={()=>{
                        setRefresh((p)=>!p)
                    }}/>
                </TabPanel>
            </Paper>
        </Page>
    )
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

