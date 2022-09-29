import ContentStyleForPage from "../../../components/Style/ContentStyleForPage";
import {Button, Divider, Table, TableBody, TableCell, TableHead, TableRow} from "@material-ui/core";
import {UploadOutlined} from "@ant-design/icons";
import {useEffect, useState} from "react";
import UploadLabWork from "../dialog/UploadLabWork";
import {GET_LAB_NAME} from "../../../settings";

export default function PersonalLab(props){
    const {userInfo} = props
    const classes = ContentStyleForPage()
    const [open, setOpen]=useState(false)
    const [refresh, setRefresh]=useState(false)
    const [labName, setLabName]=useState([])

    const getLabName=()=>{
        fetch(GET_LAB_NAME)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
                setLabName(res.data)
            })
    }

    useEffect(()=>{
     getLabName()
    },[refresh])

    return (
        <div className={classes.root}>
            <div className={classes.header}>
                <Button
                    color="primary"
                    size="medium"
                    variant="outlined"
                    onClick={()=>{
                        setOpen(true)
                    }}><UploadOutlined/>上传Lab答案</Button>
            </div>

            <Divider/>

            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>

                <TableBody>

                </TableBody>
            </Table>

            <UploadLabWork open={open} onClose={()=>{
                setOpen(false)
                setRefresh((p)=>!p)
            }} labName={labName}/>

        </div>
    )
}
