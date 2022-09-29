import React, {useContext, useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import {Card, CardContent, Button, Grid, Typography, Badge} from "@material-ui/core";
import {MARK_AS_READ_URL, MNG_DELETE_BULLETIN_URL, MNG_UPDATE_BULLETIN_URL} from "src/settings";
import { formFetch } from "src/base";
import {CloseSquareOutlined, ExclamationOutlined} from "@ant-design/icons";
import alertBox from "../../components/AlertBox";
import {RefreshContext} from "../../layouts/Context";
const useStyles = makeStyles({
  root: {
    margin: 8,
  },
  title: {
    fontSize: 14,
    fontWeight: 600,
  },
  date: {
    fontSize: 11,
  },
  unRead: {
    color: "#0a0d0e",
  },
  readButton: {
    padding: '0px 4px'
  },
});

export default function BulletinCard(props) {
  const {refreshValue} = useContext(RefreshContext)
  const classes = useStyles();
  const { bulletin, refresh } = props //refresh是刷新操作
  const notReadYet= (<ExclamationOutlined />)

  const markAsRead = (id) => {//标记已读
    fetch(`${MNG_UPDATE_BULLETIN_URL}?id=${bulletin.id}`,{
      method:"POST"
    }).then(()=>{
      // refresh() //刷新
      // refreshValue()
      window.location.reload()
    })
        .catch((error)=>console.log(error))
  };

  const handleDelete=()=>{ //删除这个通知
    fetch(`${MNG_DELETE_BULLETIN_URL}?id=${bulletin.id}`,{
      method:"POST"
    }).then(()=>{
      alertBox({text:"删除成功",severity:"success"})
      // refresh()
      // refreshValue()
      window.location.reload()
    })
        .catch((error)=>console.error(error))
  }

  return (
    <Card className={classes.root} >
      <CardContent>
        <Grid container spacing={1}>
          <Grid item md={11}>
            {
                bulletin.isReadYey==0 && (
                    <Badge badgeContent={notReadYet} color="error"/>
                )
            }
            <Typography
                className={classes.date}
                color="textSecondary"
                gutterBottom
            >
              {bulletin.createTime}
            </Typography>
            <Typography
                className={classes.title}
                color="textPrimary"
                gutterBottom
            >
              {bulletin.title}
            </Typography>
          </Grid>
          <Grid item md={1}>
            <Button
              size="small"
              onClick={()=>{
                handleDelete()
              }}
            ><CloseSquareOutlined /></Button>
          </Grid>
          <Grid item md={11}>
            <Typography
                color="textPrimary"
                gutterBottom
            >
              {bulletin.content}
            </Typography>
          </Grid>
          <Grid item md={1}>
            {bulletin.isReadYey == 0 && (
              <Button
                color="primary"
                size="medium"
                variant="outlined"
                className={classes.readButton}
                onClick={() => markAsRead()}
              >
                标记已读
              </Button>
            )}
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
}
