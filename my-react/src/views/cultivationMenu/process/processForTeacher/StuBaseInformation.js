import ContentStyleForPage from "../../../../components/Style/ContentStyleForPage";
import {Divider, Typography} from "@material-ui/core";

export default function StuBaseInformation(props){
    const {userDetail}= props
    const classes=ContentStyleForPage()

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Typography color="textPrimary" size="small">
                    学生基本信息
                </Typography>
            </div>
            <Divider/>
            <Typography color="textPrimary" size="middle">
                姓名：{userDetail.name}/n
                学号：{userDetail.stuId}/n
                导师：{userDetail.mentor}/n
                论文：{userDetail.papers}/n
                项目：{userDetail.projects}/n
                服务：{userDetail.services}/n
                专利：{userDetail.patents}/n
            </Typography>
        </div>
    )
}
