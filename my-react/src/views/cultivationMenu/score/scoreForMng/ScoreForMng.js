import {MNG_GET_SCORE_DATE, MNG_GET_UNDONE_SCORE} from "../../../../settings";
import {useEffect, useState} from "react";
import {
    Box,
    Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@material-ui/core";
import ContentStyle from "../../../../components/Style/ContentStyle";

const TYPE={
    10:"博士",
    0:"硕士"
}

export default function ScoreForMng(){
    const classes=ContentStyle()
    const [date,setDate]=useState([])
    const [refresh,setRefresh]=useState(false)
    const [nowDate,setNowDate]=useState(0)
    const [undoneUser,setUndoneUser]=useState([])

    const getDate=async ()=>{//获取已经评分过的年份和季度
        try {
            let response=await fetch(MNG_GET_SCORE_DATE,{})
            return await response.json()
        }catch (error){
            console.log(error)
        }
    }

    const getUndoneUser=({year, season})=>{
        fetch(`${MNG_GET_UNDONE_SCORE}?year=${year}&season=${season}`)
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setUndoneUser(res?.data || [])
            })
    }

    useEffect(()=>{
        getDate().then((res)=>{
            setDate(res?.data || [])
            getUndoneUser({year:res.data[nowDate].year, season:res.data[nowDate].season})
        })
    },[nowDate])

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        查找季度评分未完成学生
                    </Typography>

                    <TextField
                        size="small"
                        select
                        variant="outlined"
                        value={nowDate}
                        onChange={(e)=>{
                            setNowDate(e.target.value)
                            setRefresh((p)=>!p)
                        }}
                        SelectProps={{
                            native:true
                        }}>
                        {date.map((d,index)=>(
                            <option value={index}>
                                {d.year}年第{d.season}季
                            </option>
                        ))}
                    </TextField>
                </Box>
                <Divider/>

                <Box minWidth={800}>
                    <Table>
                     <TableHead>
                         <TableRow>
                             <TableCell>姓名</TableCell>
                             <TableCell>学/工号</TableCell>
                             <TableCell>类型</TableCell>
                         </TableRow>
                     </TableHead>

                     <TableBody>
                         {
                             undoneUser.map((user)=>(
                                 <TableRow>
                                     <TableCell>{user.name}</TableCell>
                                     <TableCell>{user.stuId}</TableCell>
                                     <TableCell>{TYPE[user.type]}</TableCell>
                                 </TableRow>
                             ))
                         }
                     </TableBody>
                    </Table>
                </Box>
            </Card>
        </div>
    )
}
