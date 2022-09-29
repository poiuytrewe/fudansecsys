import {useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import {MNG_GET_USER_DETAIL, U_GET_USER_DETAIL} from "../../../../settings";
import {UserContext} from "../../../../layouts/Context";
import Page from "../../../../components/Page";
import {AppBar, Box, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import ScoreForStu from "./ScoreForStu";
import PageView from "../../../../components/Style/PageView";
import PropTypes from "prop-types";
import ScoreGraphForStu from "./ScoreGraphForStu";

const defaultStyles=makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3)
    },
}));

export default function StudentScoreView(){
    const {stuId}=useParams() //这个stuId一定是学生的
    const {userInfo}=useContext(UserContext)
    const [userDetail,setUserDetail]=useState({})
    const [value,setValue]=useState(0)
    const classes=PageView()
    const twoClasses=defaultStyles()

    useEffect(()=>{
        fetch(
            `${
                userInfo.type==20 ? MNG_GET_USER_DETAIL : U_GET_USER_DETAIL
            }?stuId=${stuId}`,
            {}
        )
            .then((res) => res.json())
            .catch((error) => console.error("Error:", error))
            .then((response) => {
                setUserDetail(response?.data || {});
            });
    },[])

    return (
        <Page className={twoClasses.root} title="季度评分">
            <Paper className={classes.root}>
                <AppBar position="static" color="secondary">
                    <Tabs value={value} onChange={(e,newValue)=>{
                        setValue(newValue)
                    }} indicatorColor="primary" textColor="primary">
                        <Tab label="季度评分" {...a11yProps(0)}/>
                        <Tab label="评分统计图" {...a11yProps(1)}/>
                    </Tabs>
                </AppBar>
                <TabPanel index={0} value={value}>
                    {userDetail?.id>0 && <ScoreForStu userDetail={userDetail}/>}
                </TabPanel>
                <TabPanel index={1} value={value}>
                    {userDetail?.id>0 && <ScoreGraphForStu userDetail={userDetail}/>}
                </TabPanel>
            </Paper>
        </Page>
    )
}

export function TabPanel(props) {
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

export function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        "aria-controls": `simple-tabpanel-${index}`,
    };
}
