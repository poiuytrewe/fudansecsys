import {useParams} from "react-router-dom";
import {AppBar, Box, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import Page from "../../../../components/Page";
import PageView from "../../../../components/Style/PageView";
import React, {useContext, useEffect, useState} from "react";
import PropTypes from "prop-types";
import ProcessForStu from "../ProcessForStu";
import {MNG_GET_USER_DETAIL, U_GET_USER_DETAIL} from "../../../../settings";
import {UserContext} from "../../../../layouts/Context";
import StuBaseInformation from "./StuBaseInformation";

const useStyles = makeStyles((theme) => ({
    root: {
        // flexGrow: 1,
        padding: theme.spacing(3)
    },
}));

export default function ProcessDoubleForStuView(){
    const {userStuId}=useParams()
    const {userInfo}=useContext(UserContext)
    const classes2=PageView()
    const classes=useStyles()
    const [value,setValue]=useState()
    const [userDetail,setUserDetail]=useState()

    useEffect(()=>{
        fetch(
            `${userInfo.type==20 ? MNG_GET_USER_DETAIL:U_GET_USER_DETAIL }?stuId=${userStuId}`)
            .then((res)=>res.json())
            .then((res)=>{
                setUserDetail(res?.data || {})
            })
    },[])

    return (
        <Page className={classes.root} title="学生培养过程">
            <Paper className={classes2.root}>
                <AppBar position="static" color="secondary">
                    <Tabs value={value}
                          onChange={(e,newValue)=>{
                              setValue(newValue)
                          }}
                          indicatorColor="primary"
                          textColor="primary">
                        <Tab label="学生基础信息" {...a11yProps(0)}/>
                        <Tab label="学生培养过程" {...a11yProps(1)}/>
                    </Tabs>
                </AppBar>
                <TabPanel value={value} index={0}>
                    {userDetail?.id>0 && <StuBaseInformation userDetail={userDetail}/>}
                </TabPanel>
                <TabPanel value={value} index={1}>
                    {userDetail?.id>0 && <ProcessForStu userDetail={userDetail}/>}
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

