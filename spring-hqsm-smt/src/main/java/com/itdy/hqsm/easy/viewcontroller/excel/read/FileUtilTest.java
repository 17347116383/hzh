package com.itdy.hqsm.easy.viewcontroller.excel.read;



import cn.afterturn.easypoi.util.PoiPublicUtil;

import java.net.URISyntaxException;

/**
 * @author
 */
public class FileUtilTest {

    public static String getWebRootPath(String filePath) {
        try {
            String path = PoiPublicUtil.class.getClassLoader().getResource("").toURI().getPath();
            path = path.replace("WEB-INF/classes/", "");
            path = path.replace("file:/", "");
            return path + filePath;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
