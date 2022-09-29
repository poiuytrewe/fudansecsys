import {AppBar, Box, makeStyles, Paper, Tab, Tabs} from "@material-ui/core";
import React, {useState} from "react";
import SeminarForMaster from "./seminarMng/SeminarForMaster";
import SeminarForDoc from "./seminarMng/SeminarForDoc";
import PageView from "../../../components/Style/PageView";
import TabPanel from "../../../components/TabPanel";

const SeminarHead=()=>{
  const classes=PageView()
  const [value,setValue]=useState(0)

  const handleChanged=(e,newValue)=>{
    setValue(newValue)
  }

  return (
      <Paper className={classes.root}>
        <AppBar position="static" color="secondary">
          <Tabs
            value={value}
            onChange={handleChanged}
            indicatorColor="primary"
            textColor="primary"
          >
            <Tab label="论文演讲安排" {...a11yProps(0)}/>
            <Tab label="专题选讲" {...a11yProps(1)}/>
          </Tabs>
        </AppBar>

        <TabPanel index={0} value={value}>
          <SeminarForMaster />
        </TabPanel>

        <TabPanel index={1} value={value}>
          <SeminarForDoc />
        </TabPanel>
      </Paper>
  )
}

export default  SeminarHead;



function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}
