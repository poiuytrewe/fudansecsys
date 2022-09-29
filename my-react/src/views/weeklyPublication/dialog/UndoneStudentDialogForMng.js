import {Box, Button, Dialog, DialogTitle, makeStyles, TextField} from "@material-ui/core";
import confirmModal from "../../../components/ConfirmModal";
import {postFetch} from "../../../base";
import {MNG_ADD_BULLETIN_URL} from "../../../settings";
import alertBox from "../../../components/AlertBox";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(3),
    },
}));

export default function UndoneStudentDialogForMng(props){
    const {open, list, onClose, week}=props
    const classes=useStyles()

    const getNameLine=()=>{
        let nameLine=""
        list.map((l,index)=>{
            let s= index==0 ? l.name : "，"+l.name
            nameLine=nameLine+s
        })
        return nameLine
    }

    const handleClick=()=>{
        const cor=confirmModal({
            title:"确定要给该周未提交周报的同学发送提示信息嘛？",
            handleCorfirm:()=>{
                cor.close()
                list.map((l)=>{
                    postFetch({
                        url:MNG_ADD_BULLETIN_URL,
                        values:{
                            stuId:l.stuId,
                            title:"你的周报未及时提交",
                            content:"你的第"+week.slice(0,4)+"年第"+week.slice(6)+"周的周报未提交，请及时提交你的周报"
                        }
                    })
                })
                alertBox({text:"通知发送成功", severity:"success"})
                onClose()
            }
        })
    }

    return (
        <Dialog
            onClose={onClose} open={open} className={classes.root}>
            <DialogTitle onClose={onClose}>
                未提交周报名单
            </DialogTitle>
            <form className="dialogForm">
                <TextField
                    InputProps={{readOnly:true}}
                    multiline
                    rows={15}
                    fullWidth
                    value={getNameLine()}
                    variant="outlined"
                />
                <Box my={2}>
                    <Button
                        fullWidth
                        color="primary"
                        size="large"
                        onClick={()=>{
                            handleClick()
                        }}
                        variant="contained">发送提示信息</Button>
                    <Button
                        fullWidth
                        color="secondary"
                        size="large"
                        onClick={()=>{
                            onClose()
                        }}
                        variant="contained">关闭</Button>
                </Box>
            </form>
        </Dialog>
    )
}
