package com.shfh.pdmp.demo.upload;

import com.shfh.pdmp.base.Response;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by wpcao on 2018/3/13 14:06
 */
@Controller
@RequestMapping("/file")
public class UpDownLoadController {

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, Model model) {
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/");
        File uploadHome = new File(path);
        if (!uploadHome.exists()) {
            uploadHome.mkdirs();
        }
        File[] files = uploadHome.listFiles();
        model.addAttribute("files", files);
        return "demo/uploadFile";
    }


    /**
     * 文件上传功能。
     * 方式一：通过二进制保存文件到服务器
     * 上传文件会自动绑定到MultipartFile中，将上传的文件通过二进制保存到web服务器上去
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Response upload(HttpServletRequest request, @RequestParam("description") String description, @RequestParam("files") MultipartFile[] files) {
        //上传文件路径
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/");
        if (files != null && files.length > 0) {
            for (MultipartFile multipartFile : files) {
                //获取上传文件的原名
                String originalFilename = multipartFile.getOriginalFilename();
                //目标存储文件
                File destFile = new File(path, originalFilename);
                //判断路径是否存在，如果不存在就创建一个
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                if (!destFile.exists()) {
                    //将上传文件保存到一个目标文件当中
                    try {
                        multipartFile.transferTo(new File(path + File.separator + originalFilename));
                    } catch (IOException e) {
                        //文件存储失败
                        e.printStackTrace();
                    }
                } else {
                    //已存在同名文件
                }
            }
        } else {
            //上传的文件为空
        }
        return Response.success();
    }

    /**
     * 文件上传功能。
     * 方式二：通过对象的方式上传文件,可以携带任意数据
     */
    @RequestMapping(value = "/uploadWithExtra")
    @ResponseBody
    public Response uploadWithExtra(HttpServletRequest request, @ModelAttribute UserInfoBean user) throws Exception {
        System.out.println(user.getUsername());
        //如果文件不为空，写入上传路径
        if (!user.getImage().isEmpty()) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/WEB-INF/upload/");
            //上传文件名
            String filename = user.getImage().getOriginalFilename();
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            user.getImage().transferTo(new File(path + File.separator + filename));
            //将用户添加到model
            return Response.success();
        } else {
            return Response.error();
        }
    }


    /**
     * 文件下载
     * 方式一：通过输出流
     *
     * @param request http请求
     * @param response http响应
     * @param filename 文件名
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response, String filename) {
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/");
        File file = new File(path + File.separator + filename);
        if (file.exists()) {
            InputStream fileStream = null;
            try {
                fileStream = new FileInputStream(file);
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try {
                    response.reset();
                    response.setContentType("application/octet-stream; charset=utf-8");
                    response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));
                    bis = new BufferedInputStream(fileStream);
                    bos = new BufferedOutputStream(response.getOutputStream());
                    byte[] buff = new byte[4096];
                    int bytesRead = -1;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bos.write(buff, 0, bytesRead);
                    }
                    bos.flush();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileStream != null) {
                    try {
                        fileStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 文件下载
     * 方法二：通过springMVC的ResponseEntity
     *
     * @param request http请求
     * @param filename 文件名
     * @return 文件输出流
     */
    @RequestMapping(value = "/downloadByEntity")
    public ResponseEntity<byte[]> downloadByEntity(HttpServletRequest request, @RequestParam("filename") String filename) {
        try {
            //下载文件路径
            String path = request.getServletContext().getRealPath("/WEB-INF/upload/");
            File file = new File(path + File.separator + filename);
            if (file.exists()) {
                HttpHeaders headers = new HttpHeaders();
                //下载显示的文件名，解决中文名称乱码问题
                String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");

                //通知浏览器以attachment（下载方式）打开图片
                headers.setContentDispositionFormData("attachment", downloadFielName);
                //application/octet-stream ： 二进制流数据（最常见的文件下载）。
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
            } else {
                //文件不存在
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
