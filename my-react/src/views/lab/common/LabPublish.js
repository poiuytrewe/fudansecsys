import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import {Button, Divider, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@material-ui/core";
import {DELETE_LAB_DATA, GET_LAB_DATA} from "../../../settings";
import {useContext, useState} from "react";
import {DownloadOutlined, UploadOutlined} from "@ant-design/icons";
import {UserContext} from "../../../layouts/Context";
import confirmModal from "../../../components/ConfirmModal";
import alertBox from "../../../components/AlertBox";
import UploadLabData from "../dialog/UploadLabData";

export default function LabPublish(props){
    const {elite} = props
    const classes = ContentStyleForPage()
    const {userInfo} = useContext(UserContext)
    const [refresh, setRefresh]=useState(false)
    const [labData, setLabData] = useState([])
    const [uploadOpen, setUploadOpen]=useState(false)

    const getLab=()=>{
        fetch(GET_LAB_DATA)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setLabData(res.data)
            })
    }

    const handleDelete=(id)=>{
        const cor = confirmModal({
            title:"确定要删除这个已经发布的Lab资料嘛？",
            handleCorfirm:()=>{
                cor.close()
                fetch(`${DELETE_LAB_DATA}?id=${id}`, {method:"POST"})
                    .then((res)=>res.json())
                    .catch((error)=>console.error(error))
                    .then(()=>{
                        alertBox({text:"删除成功",severity:"success"})
                    })
            }
        })
    }

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Typography color="textPrimary" size="small" component="h2">
                    Lab资料
                </Typography>

                {elite == true && (
                    <Button
                        color="primary"
                        size="medium"
                        variant="outlined"
                        onClick={()=>{
                            setUploadOpen(true)
                        }}
                        ><UploadOutlined/>上传Lab资料</Button>
                )}
            </div>

            <Divider/>

            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Lab发布时间</TableCell>
                        <TableCell align="center">Lab名称</TableCell>
                        <TableCell align="center">Lab资料</TableCell>
                        <TableCell align="center">Lab发布人</TableCell>
                        {elite == true && (
                            <TableCell align="center">操作</TableCell>
                        )}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {labData.map((singleLabData)=>(
                        <TableRow>
                            <TableCell>{singleLabData.date}</TableCell>
                            <TableCell align="center">{singleLabData.labName}</TableCell>
                            <TableCell align="center">
                                <Button
                                    size="small"
                                    color="primary"
                                    variant="outlined"
                                    onClick={()=>{}}
                                    ><DownloadOutlined/>下载</Button>
                            </TableCell>
                            <TableCell>{singleLabData.labPublisher}</TableCell>
                            {elite == true && (
                                <TableCell align="center">{
                                    singleLabData.labPublisherStuId == userInfo.stuId && (
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                handleDelete(singleLabData.id)
                                            }}>删除</Button>
                                    )
                                }</TableCell>
                            )}
                        </TableRow>
                    ))}
                </TableBody>
            </Table>

            <UploadLabData open={uploadOpen}
                           publisher={{
                               stuName:userInfo.name,
                               stuId:userInfo.stuId
                           }}
                           onClose={()=>{
                               setUploadOpen(false)
                               setRefresh((p)=>!p)
                           }}/>
        </div>
    )
}
