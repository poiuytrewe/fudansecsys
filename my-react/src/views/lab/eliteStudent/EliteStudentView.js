import PageView from "../../../components/Style/PageView";
import {AppBar, Box, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import Page from "../../../components/Page";
import React, {useState} from "react";
import {a11yProps} from "../../cultivationMenu/score/scoreForStudent/StudentScoreView";
import PropTypes from "prop-types";
import LabPublish from "../common/LabPublish";

const useStyles = makeStyles((theme) => ({
    root: {
        // flexGrow: 1,
        padding: theme.spacing(3)
    },
}));

export default function EliteStudentView(){
    const classes = PageView()
    const classes2 = useStyles()
    const [value, setValue]=useState(0)

    return (
        <Page className={classes2.root} title="Lab管理">
            <Paper className={classes.root}>
                <AppBar position="static" color="secondary">
                    <Tabs value={value} onChange={(event, value)=>{
                        setValue(value)
                    }} indicatorColor="primary" textColor="primary">
                        <Tab label="已发布的Lab资料" {...a11yProps(0)} />
                        <Tab label="Lab作业批阅" {...a11yProps(1)} />
                    </Tabs>
                </AppBar>

                <TabPanel value={value} index={0}>
                    <LabPublish elite={true}/>
                </TabPanel>

                <TabPanel value={value} index={1}>

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
