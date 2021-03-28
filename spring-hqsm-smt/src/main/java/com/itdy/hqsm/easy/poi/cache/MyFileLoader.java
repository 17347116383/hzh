
package com.itdy.hqsm.easy.poi.cache;

import cn.afterturn.easypoi.cache.manager.FileLoaderImpl;
import cn.afterturn.easypoi.cache.manager.IFileLoader;
/**
 * 
 * 文件件模板加载类
 * @author Administrator
 *
 */
public class MyFileLoader extends FileLoaderImpl implements IFileLoader {
    
    
    @Override
    public byte[] getFile(String url) {
        url = "doc/" + url;
        return super.getFile(url);
    }
}
