import Page from "../../../../components/Page";
import {AppBar, Box, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import {useParams} from "react-router-dom";
import ProcessConfig from "./ProcessConfig";
import React, {useEffect, useState} from "react";
import {MNG_GET_USER_DETAIL} from "../../../../settings";
import PageView from "../../../../components/Style/PageView";
import PropTypes from "prop-types";
import ProcessADVConfig from "./ProcessADVConfig";
const useStyles = makeStyles((theme) => ({
    root: {
        // flexGrow: 1,
        padding: theme.spacing(3)
    },
}));

export default function ProcessConfigView(){
    const classes=PageView()
    const classesPage=useStyles()
    const {stuId}=useParams()
    const [userDetail,setUserDetail]=useState({})
    const [value,setValue]=useState(0)

    useEffect(()=>{
        fetch(
            `${MNG_GET_USER_DETAIL}?stuId=${stuId}`)
            .then((res)=>res.json())
            .then((res)=>{
                setUserDetail(res?.data || {})
            })
    },[value])

    return (
        <Page className={classesPage.root} title="配置培养过程">
            <Paper className={classes.root}>
                <AppBar position="static" color="secondary">
                    <Tabs value={value} onChange={(e,newValue)=>{
                        setValue(newValue)
                    }} indicatorColor="primary" textColor="primary">
                        <Tab label="基本任务目标" {...a11yProps(0)}/>
                        <Tab label="进阶任务目标" {...a11yProps(1)}/>
                    </Tabs>
                </AppBar>
                <TabPanel index={0} value={value}>
                    {userDetail.id>0 &&  <ProcessConfig userDetail={userDetail} userStuId={stuId}/>}
                </TabPanel>
                <TabPanel index={1} value={value}>
                    {userDetail.id>0 && <ProcessADVConfig userDetail={userDetail} userStuId={stuId}/>}
                </TabPanel>
            </Paper>
        </Page>
    )
}

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box p={3}>{children}</Box>}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        "aria-controls": `simple-tabpanel-${index}`,
    };
}

