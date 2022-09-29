import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import {Formik} from "formik";
import {UploadOutlined} from "@ant-design/icons";
import {useEffect, useState} from "react";
import {message, Upload} from "antd";
import alertBox from "../../../components/AlertBox";
import {UPLOAD_LAB_DATA} from "../../../settings";
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function UploadLabData(props){
    const {open, publisher, onClose} = props
    const classes = useStyles()
    const [labName, setLabName]=useState("")
    const [disable, setDisable]=useState(true)
    const [refresh, setRefresh]=useState(false)
    const [labData, setLabData]=useState({})

    useEffect(()=>{
        labName == "" ? (
            setDisable(true)
        ):(
            setDisable(false)
        )
    },[refresh])

    // const uploadProps={
    //     name:"labFile",
    //     action: UPLOAD_LAB_DATA,
    //     method:"POST",
    //     data:{
    //         labName:labName,
    //         labPublisher:publisher.stuName,
    //         labPublisherStuId:publisher.stuId
    //     },
    //     onChange (info){
    //         if (info.file.status !== 'uploading') {
    //             console.log(info.file, info.fileList);
    //         }
    //         if (info.file.status === 'done') {
    //             alertBox({text:"上传成功",severity:"success"})
    //             message.success(`${info.file.name} file uploaded successfully`);
    //             setRefresh((prev)=>!prev)
    //         } else if (info.file.status === 'error') {
    //             alertBox({text:"上传失败",severity:"error"})
    //             message.error(`${info.file.name} file upload failed.`);
    //         }
    //     }
    // }

    return (
        <Dialog open={open} className={classes.root}>
            <DialogTitle>
                上传Lab资料
            </DialogTitle>

            <Formik onSubmit={()=>{
                console.log(labData)
                const cor = confirmModal({
                    title:"请确定好Lab名称和要上传的Lab资料",
                    handleCorfirm:()=>{
                        cor.close()
                        postFetch({
                            url:UPLOAD_LAB_DATA,
                            values:{
                                labName: labName,
                                labFile: labData,
                                labPublisher: publisher.stuName,
                                labPublisherStuId: publisher.stuId
                            },
                            successCallback:()=>{
                                alertBox({text:"上传成功",severity:"success"})
                                onClose()
                            }
                        })
                    }
                })

            }}
                    >{({handleBlur, handleSubmit})=>(
                <form className="dialogForm"  onSubmit={handleSubmit} encType="multipart/form-data">
                    <TextField
                        label="请输入Lab名称"
                        fullWidth
                        placeholder="必填"
                        margin="normal"
                        name="labName"
                        value={labName}
                        variant="outlined"
                        onBlur={handleBlur}
                        onChange={(event)=>{
                            setLabName(event.target.value)
                            setRefresh((p)=>!p)
                        }}
                    />

                    <TextField
                        label="请选择Lab资料"
                        fullWidth
                        InputLabelProps={{shrink:true}}
                        margin="normal"
                        name="labData"
                        onBlur={handleBlur}
                        type="file"
                        onChange={(event)=>{
                            setLabData(event.target.files[0])
                        }}/>

                    <Box my={2}>
                            <Button
                                disabled={disable}
                                fullWidth
                                color="primary"
                                size="large"
                                variant="outlined"
                                type="submit"
                            ><UploadOutlined/>上传</Button>

                        <Button
                            fullWidth
                            color="secondary"
                            size="large"
                            variant="contained"
                            onClick={()=>{onClose()}}
                            >关闭</Button>
                    </Box>

                </form>
            )}</Formik>
        </Dialog>
    )
}
