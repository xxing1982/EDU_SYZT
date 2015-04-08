package com.syzton.sunread.common.util;


import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
  
import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPClientConfig;  
import org.apache.commons.net.ftp.FTPReply;  
  
 
public class FtpUtil {  
  
    private FTPClient ftpClient = null;  
    private String    hostname;  
    private int       port;  
    private String    username;  
    private String    password;  
    private String    remoteDir;  
  
    /** 
     * 初始化 
     * @param hostname 
     * @param port 
     * @param username 
     * @param password 
     * @param remoteDir 
     */  
    public FtpUtil(String hostname, int port, String username, String password, String remoteDir) {  
        this.hostname = hostname;  
        this.port = port;  
        this.username = username;  
        this.password = password;  
        this.remoteDir = remoteDir;  
        if (remoteDir == null) {  
            remoteDir = "/";  
        }  
    }  
  
    /** 
     * FTP登陆 
     * @throws IOException 
     */  
    public void login() throws Exception {  
        ftpClient = new FTPClient();  
        ftpClient.configure(getFTPClientConfig());  
        ftpClient.setDefaultPort(port);  
        ftpClient.setControlEncoding("UTF-8");  
        ftpClient.connect(hostname);  
        if (!ftpClient.login(username, password)) {  
            throw new Exception("FTP log error");  
        }  
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
        ftpClient.changeWorkingDirectory(remoteDir);  
    }  
  
    /** 
     * 得到配置 
     * @return 
     */  
    private FTPClientConfig getFTPClientConfig() {  
        // 创建配置对象  
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);  
        conf.setServerLanguageCode("zh");  
        return conf;  
    }  
  
    /** 
     * 关闭FTP服务器 
     */  
    public void closeServer() {  
        try {  
            if (ftpClient != null) {  
                ftpClient.logout();  
                ftpClient.disconnect();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 链接是否已经打开 
     * @return 
     */  
    public boolean serverIsOpen() {  
        if (ftpClient == null) {  
            return false;  
        }  
        return !ftpClient.isConnected();  
    }  
  
    /** 
     * 列表FTP文件 
     * @param regEx 
     * @return 
     */  
    public String[] listFiles(String regEx) {  
        String[] names;  
        try {  
            names = ftpClient.listNames(regEx);  
            if (names == null)  
                return new String[0];  
            return names;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String[0];  
    }  
  
    /** 
     * 取得FTP操作类的句柄 
     * @return 
     */  
    public FTPClient getFtpClient() {  
        return ftpClient;  
    }  
  
    /** 
     * 上传 
     * @throws Exception 
     */  
    public boolean upload(String localFilePath, String remoteFilePath) throws Exception {  
        boolean state = false;  
        File localFile = new File(localFilePath);  
        if (!localFile.isFile() || localFile.length() == 0) {  
            return state;  
        }  
        FileInputStream localIn = new FileInputStream(localFile);  
        state = this.upload(localIn, remoteFilePath);  
        localIn.close();  
        return state;  
    }  
  
    /** 
     * 上传 
     * @throws Exception 
     */  
    public boolean upload(InputStream localIn, String remoteFilePath) throws Exception {  
        this.createDir(new File(remoteFilePath).getParent());  
        boolean result = ftpClient.storeFile(remoteFilePath, localIn);  
        return result;  
    }  
  
    /** 
     * 是否存在FTP目录 
     * @param dir 
     * @param ftpClient 
     * @return 
     */  
    public boolean isDirExist(String dir) {  
        try {  
            int retCode = ftpClient.cwd(dir);  
            return FTPReply.isPositiveCompletion(retCode);  
        } catch (Exception e) {  
            return false;  
        }  
    }  
  
    /** 
     * 创建FTP多级目录 
     * @param remoteFilePath 
     * @throws IOException 
     */  
    public void createDir(String dir) throws IOException {  
        if (!isDirExist(dir)) {  
            File file = new File(dir);  
            this.createDir(file.getParent());  
            ftpClient.makeDirectory(dir);  
        }  
    }  
  
    /** 
     * 删除文件 
     * @param remoteFilePath 
     */  
    public boolean delFile(String remoteFilePath) {  
        try {  
            return ftpClient.deleteFile(remoteFilePath);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
  
    /** 
     * 下载 
     * @throws Exception 
     */  
    public void download(String localFilePath, String remoteFilePath) throws Exception {  
        OutputStream localOut = new FileOutputStream(localFilePath);  
        this.download(localOut, remoteFilePath);  
        localOut.close();  
    }  
  
    /** 
     * 下载 
     * @throws Exception 
     */  
    public void download(OutputStream localOut, String remoteFilePath) throws Exception {  
        boolean result = ftpClient.retrieveFile(remoteFilePath, localOut);  
        if (!result) {  
            throw new Exception("download fail!");  
        }  
    }  
}  