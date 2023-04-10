package com.dplot.admin.controller.common;

import com.dplot.common.Response;
import com.dplot.common.SOMap;
import com.dplot.common.service.util.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RequestMapping("image")
@RestController
public class AdminCommonEditorController {
    private static final Logger logger = LoggerFactory.getLogger(AdminCommonEditorController.class);

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response Upload(MultipartHttpServletRequest request) throws Exception {
        MultipartFile imageFile = request.getFileMap().get("image");
        String imageUrl = fileService.uploadEditFile(imageFile);
        SOMap data = new SOMap();
        data.put("url", imageUrl);

        return new Response(data);
    }
}
