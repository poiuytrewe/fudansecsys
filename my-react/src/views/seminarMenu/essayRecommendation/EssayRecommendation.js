import React, { useState, useEffect, useContext } from "react";
import {
  Box,
  Button, Card,
  Divider,
  Table, TableBody, TableCell,
  TableHead, TableRow,
  TextField, Typography
} from "@material-ui/core";
import {DELETE_ARTICLE_URL, GET_ALL_ARTICLE_URL, GET_ALL_CLASS_URL} from "src/settings";
import { UserContext } from "src/layouts/Context";
import Pagination from "@material-ui/lab/Pagination";
import EssayEditForm from "./EssayEditForm";
import corfirmModal from "../../../components/ConfirmModal";
import {deleteFetch} from "../../../base";
import ContentStyle from "../../../components/Style/ContentStyle";

const EssayRecommendation=()=> {
  const classes = ContentStyle()
  const { userInfo } = useContext(UserContext);
  const [refresh, setRefresh]=useState(false);
  const [page, setPage]=useState(1);
  const [pageNo, setPageNo]=useState(0);
  const [article, setArticle]=useState([]);
  const [open, setOpen]=useState(false); //用来判断是否打开编辑框
  const [articleDetail, setArticleDetail]=useState({});

  const getAllArticle=async ({page=1, limit=10})=>{ //获得已发布的所有文章信息
    try{
      let response =await fetch(
          `${GET_ALL_ARTICLE_URL}?limit=${limit}&offset=${(page - 1) * 10}`
      );
      return await response.json();
    }catch (error){
      console.log(error)
    }
  }

  const handleDeleteEssays = (id, name) => {//删除文章
    const cor = corfirmModal({
      title: `确定要删除[${name}]吗？`,
      handleCorfirm: () => {
        cor.close();
        deleteFetch({
          url: `${DELETE_ARTICLE_URL}?id=${id}`,
          successCallback: () => {
            setRefresh((prev) => !prev);
          },
        });
      },
    });
  };

  useEffect(()=>{
    getAllArticle({page}).then((res)=>{
      setArticle(res?.data || []);
      setPageNo(Math.ceil(res?.paging?.total /10) || 0);
    })
  },[refresh, page, pageNo])

  return (
      <div>
        <Card>
        <Box className={classes.header}>
          <Typography color="textPrimary" size="small" component="h2">
            论文演讲安排
          </Typography>

          {userInfo.isPaperMng ==1 &&( //推荐论文管理员可以添加论文
                <Button
                  color="primary"
                  size="small"
                  variant="outlined"
                  onClick={()=>{
                    setOpen(true)
                  }}
                  >
                  添加论文
                </Button>
          )}
        </Box>

        <Divider />

        <Box minWidth={800}>
        <Table>

          <TableHead>
            <TableRow>
              <TableCell>论文演讲时间</TableCell>
              <TableCell align="center">论文名称</TableCell>
              <TableCell align="center">论文链接</TableCell>
              {userInfo.isPaperMng ==1 &&(
                  <TableCell align="center">操作</TableCell>
              )}
            </TableRow>
          </TableHead>

          <TableBody>
            {article.map((art)=>(
                <TableRow >
                  <TableCell>{art.date}</TableCell>
                  <TableCell align="center">{art.title}</TableCell>
                  <TableCell align="center">
                    <a href={art.content}>{art.content}</a>
                  </TableCell>
                  {userInfo.isPaperMng ==1 &&(
                      <TableCell align="center">
                        <Button
                            color="primary"
                            size="small"
                            variant="text"
                            onClick={()=>{
                              setOpen(true)
                              setArticleDetail(art)
                            }}
                        >
                          编辑
                        </Button>
                        <Button
                            color="primary"
                            size="small"
                            variant="text"
                            onClick={()=>{
                              handleDeleteEssays(art.id, art.title)
                            }}
                        >
                          删除
                        </Button>
                      </TableCell>
                  )}
                </TableRow>
            ))}
          </TableBody>
        </Table>
          {pageNo > 1 && (
              <Pagination
                  className={classes.Pagination}
                  count={pageNo}
                  color="primary"
                  onChange={(e, i) => setPage(i)}
              />
          )}
        </Box>
        </Card>
        <EssayEditForm
          open={open}
          onClose={()=>{
            setOpen(false)
            setArticleDetail({})
            setRefresh((prev)=>!prev)
          }}
          articleDetail={articleDetail}
          />

      </div>
  );
}

export default EssayRecommendation;

