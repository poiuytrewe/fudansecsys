import ContentStyle from "../../../components/Style/ContentStyle";
import {
    Box,
    Button,
    Card,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@material-ui/core";
import {useHistory} from "react-router-dom";
import {useEffect, useState} from "react";
import {STU_GET_PUBLICATION} from "../../../settings";
import Pagination from "@material-ui/lab/Pagination";
import PublicationComment from "../dialog/PublicationComment";
import {TextField} from "@mui/material";

export default function WeeklyPublicationAllTime(props){
    const classes=ContentStyle()
    const {userInfo}=props
    const history = useHistory()
    const [page, setPage]=useState(1)
    const [pageNo, setPageNo]=useState(0)
    const [refresh, setRefresh]=useState(false)
    const [weeklyPublication, setWeeklyPublication]=useState([])
    const [open, setOpen]=useState(false)
    const [publication,setPublication]=useState({})

    const getPublication = async ({stuId, page=1,limit})=>{
        try{
            let response = await fetch(
                `${STU_GET_PUBLICATION}?stuId=${stuId}&limit=${limit}&offset=${(page-1)*limit}`
            )
            return response.json()
        }catch (error){
            console.error(error)
        }
    }

    useEffect(()=>{
        getPublication({stuId:userInfo.stuId, page, limit:4})
            .then((res)=>{
                setWeeklyPublication(res?.data || [])
                setPageNo(Math.ceil(res?.paging?.total/4) || 0)
            })
    },[refresh, page, pageNo])

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h2">
                        {userInfo.name}的周报模块
                    </Typography>

                    <Button
                        color="primary"
                        size="large"
                        variant="outlined"
                        onClick={()=>{
                            history.goBack()
                        }}>返回</Button>
                </Box>

                <Divider/>
                <Box minWidth={800}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>对应周数</TableCell>
                                <TableCell align="center">Target</TableCell>
                                <TableCell align="center">OutlinedAchievements</TableCell>
                                <TableCell align="center">Achievements</TableCell>
                                <TableCell align="center">Plan</TableCell>
                                <TableCell align="center">评分</TableCell>
                                <TableCell align="center">评语</TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {weeklyPublication.map((publication)=>(
                                <TableRow>
                                    <TableCell>{publication.week}</TableCell>
                                    <TableCell align="center">
                                        <TextField
                                            style={{
                                                fontSize:"smaller"
                                            }}
                                            color="primary"
                                            size="small"
                                            variant="outlined"
                                            multiline
                                            maxRows={4}
                                            value={publication.target}/>
                                    </TableCell>
                                    <TableCell align="center">
                                        <TextField
                                            style={{
                                                fontSize:"smaller"
                                            }}
                                            color="primary"
                                            size="small"
                                            variant="outlined"
                                            multiline
                                            maxRows={4}
                                            value={publication.outlinedAchievements}/>
                                    </TableCell>
                                    <TableCell align="center">
                                        <TextField
                                            style={{
                                                fontSize:"smaller"
                                            }}
                                            color="primary"
                                            size="small"
                                            variant="outlined"
                                            multiline
                                            maxRows={4}
                                            value={publication.achievements}/>
                                    </TableCell>
                                    <TableCell align="center">
                                        <TextField
                                            style={{
                                                fontSize:"smaller"
                                            }}
                                            color="primary"
                                            size="small"
                                            variant="outlined"
                                            multiline
                                            maxRows={4}
                                            value={publication.plan}/>
                                    </TableCell>
                                    <TableCell>{publication.score}</TableCell>
                                    <TableCell>
                                        <Button
                                            color="primary"
                                            size="small"
                                            variant="text"
                                            onClick={()=>{
                                                setOpen(true)
                                                setPublication(publication)
                                            }}>查看评语</Button>
                                    </TableCell>
                                </TableRow>
                                )
                            )}
                        </TableBody>
                    </Table>
                </Box>

                {pageNo>1 && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        color="primary"
                        onChange={(e,i)=>setPage(i)}/>
                )}

            </Card>

            <PublicationComment open={open}
                                onClose={()=>{
                                    setOpen(false)
                                    setPublication({})
                                }}
                                publication={publication}/>
        </div>
    )
}
