package by.epam.cafe.controller;

import by.epam.cafe.constant.EntityAtr;

import by.epam.cafe.receiver.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = {"/uploadcontroller"})
@MultipartConfig(fileSizeThreshold=1024*1024*10,
        maxFileSize=1024*1024*50,
        maxRequestSize=1024*1024*100)
public class UploadController extends HttpServlet {
    private static final String DIRECTORY_PATH = "D:\\STUDY\\JAVA-EPAM\\EpamCafe\\web\\images";
    private static final String PARAM_CONTENT_DISPOSITION = "content-disposition";
    private static final String PARAM_FILE_NAME = "filename";
    private static final String ATR_UPLOAD_MESSAGE = "uploadMessage";
    private static final String DEFAULT_FILE_PATH = "default.jpg";
    private static final String IMAGES_PATH_PART = "/images";
    private static final String SEPARATOR = "/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part part = null;
        try {
            part = request.getPart("file_field");
        } catch (IOException e) {
            throw new ServletException("Problem with pictuure add", e);
        }
        String fileName = extractFileName(part);

        if (Validator.isPictureNameValid(fileName)) {
            part.write(DIRECTORY_PATH + File.separator + fileName);
            String picPathForDB = IMAGES_PATH_PART + SEPARATOR + fileName;
            request.setAttribute(EntityAtr.PICTURE_PARAM, picPathForDB);
        }

        getServletContext().getRequestDispatcher("/controller").forward(request, response);

    }

    private String extractFileName(Part part) {
        String result = DEFAULT_FILE_PATH;
        String contentDisp = part.getHeader(PARAM_CONTENT_DISPOSITION);
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith(PARAM_FILE_NAME)) {
                result = s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return result;
    }

}