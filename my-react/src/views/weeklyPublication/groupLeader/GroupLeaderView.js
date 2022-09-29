import {AppBar, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import PageView from "../../../components/Style/PageView";
import Page from "../../../components/Page";
import PropTypes from "prop-types";
import {a11yProps, TabPanel} from "../../cultivationMenu/score/scoreForStudent/StudentScoreView";
import {useContext, useState} from "react";
import {UserContext} from "../../../layouts/Context";
import WeeklyPublicationForGroupLeaderSelf from "./WeeklyPublicationForGroupLeaderSelf";
import WeeklyPublicationForGroupStudents from "./WeeklyPublicationForGroupStudents";

const defaultStyles=makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3)
    },
}));

export default function GroupLeaderView(){
    const classes=PageView()
    const {userInfo}=useContext(UserContext)
    const twoClasses=defaultStyles()
    const [value,setValue]=useState(0)

    return (
        <Page className={twoClasses.root} title="周报模块">
            <Paper className={classes.root}>
                <AppBar position="static" color="secondary">
                    <Tabs value={value} onChange={(e,newValue)=>{
                        setValue(newValue)
                    }} indicatorColor="primary" textColor="primary">
                        <Tab label="组内学生周报" {...a11yProps(0)}/>
                        <Tab label="个人周报模块" {...a11yProps(1)}/>
                    </Tabs>
                </AppBar>
                <TabPanel index={0} value={value}>
                    <WeeklyPublicationForGroupStudents userInfo={userInfo}/>
                </TabPanel>
                <TabPanel index={1} value={value}>
                    <WeeklyPublicationForGroupLeaderSelf userInfo={userInfo}/>
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
