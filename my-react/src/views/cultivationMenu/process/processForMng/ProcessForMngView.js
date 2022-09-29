import ViewStyle from "../../../../components/Style/ViewStyle";
import {Container} from "@mui/material";
import Page from "../../../../components/Page";
import ProcessConfig from "./ProcessConfig";
import {AppBar, Box, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import PropTypes from "prop-types";
import React, {useState} from "react";
import PageView from "../../../../components/Style/PageView";
import ProcessADVConfig from "./ProcessADVConfig";
const useStyles = makeStyles((theme) => ({
    root: {
        // flexGrow: 1,
        padding: theme.spacing(3)
    },
}));

export default function ProcessForMngView(){
    const classes=PageView()
    const classesPage=useStyles()
    const [value,setValue]=useState(0)

    return(
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
                    <ProcessConfig />
                </TabPanel>
                <TabPanel index={1} value={value}>
                    <ProcessADVConfig/>
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
